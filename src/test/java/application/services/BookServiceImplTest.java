package application.services;

import application.constants.ExceptionMessages;
import application.entities.Book;
import application.exceptions.DataIntegrityViolationException;
import application.exceptions.EntityNotFoundException;
import application.mappers.BookMapper;
import application.repositories.impl.BookRepositoryImpl;
import application.requests.BookRequest;
import application.services.impl.BookServiceImpl;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
class BookServiceImplTest {

    @InjectMock
    private BookRepositoryImpl bookRepositoryImpl;

    @InjectMock
    private BookMapper bookMapper;

    @Inject
    BookServiceImpl bookServiceImpl;

    private static final Long LONG_1 = 1L;
    private static final String ISBN = "1284";
    private static final String AUTHOR_NAME = "AUTHOR_NAME";
    private static final String TITLE = "TITLE";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final LocalDate LOCAL_DATE_NOW = LocalDate.now();

    private Book book;
    private BookRequest bookRequest;
    private List<Book> bookList;

    @BeforeEach
    void setUp() {
        book = Book.builder()
                .id(LONG_1)
                .isbn(ISBN)
                .authorName(AUTHOR_NAME)
                .title(TITLE)
                .description(DESCRIPTION)
                .releaseDate(LOCAL_DATE_NOW)
                .build();

        bookRequest = BookRequest.builder()
                .isbn(ISBN)
                .authorName(AUTHOR_NAME)
                .title(TITLE)
                .description(DESCRIPTION)
                .releaseDate(LOCAL_DATE_NOW)
                .build();

        bookList = Collections.singletonList(book);
    }

    @Test
    void getAllBooks_SUCCESS() {
        when(bookRepositoryImpl.listAll())
                .thenReturn(bookList);

        List<Book> result = bookServiceImpl.getAllBooks();

        assertFalse(result.isEmpty());
        assertNotNull(result.get(0));
    }

    @Test
    void getBookById_SUCCESS() {
        when(bookRepositoryImpl.findByIdOptional(anyLong()))
                .thenReturn(Optional.of(book));

        Book result = bookServiceImpl.getBookById(LONG_1);

        assertNotNull(result);
        assertEquals(LONG_1, result.getId());
        assertEquals(ISBN, result.getIsbn());
        assertEquals(AUTHOR_NAME, result.getAuthorName());
        assertEquals(TITLE, result.getTitle());
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(LOCAL_DATE_NOW, result.getReleaseDate());
    }

    @Test
    void getBookById_EXCEPTION() {
        when(bookRepositoryImpl.findByIdOptional(anyLong()))
                .thenReturn(Optional.empty())
                .thenThrow(EntityNotFoundException.class);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> bookServiceImpl.getBookById(LONG_1));

        assertEquals(EntityNotFoundException.class, exception.getClass());
        assertEquals(ExceptionMessages.BOOK_NOT_FOUND, exception.getMessage());
    }

    @Test
    void createBook_SUCCESS() {
        when(bookRepositoryImpl.existsBookByIsbn(anyString()))
                .thenReturn(Boolean.FALSE);

        when(bookMapper.toEntity(any()))
                .thenReturn(book);

        doNothing().when(bookRepositoryImpl).persist(book);

        Book result = bookServiceImpl.createBook(bookRequest);

        assertNotNull(result);
        assertEquals(LONG_1, result.getId());
        assertEquals(ISBN, result.getIsbn());
        assertEquals(AUTHOR_NAME, result.getAuthorName());
        assertEquals(TITLE, result.getTitle());
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(LOCAL_DATE_NOW, result.getReleaseDate());
    }

    @Test
    void createBook_EXCEPTION() {
        when(bookRepositoryImpl.existsBookByIsbn(anyString()))
                .thenReturn(Boolean.TRUE)
                .thenThrow(DataIntegrityViolationException.class);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class,
                () -> bookServiceImpl.createBook(bookRequest));

        assertEquals(DataIntegrityViolationException.class, exception.getClass());
        assertEquals(ExceptionMessages.BOOK_ALREADY_REGISTERED, exception.getMessage());
    }

    @Test
    void updateBook_SUCCESS() {
        when(bookRepositoryImpl.findBookByIsbn(anyString()))
                .thenReturn(Optional.of(book));

        doNothing().when(bookMapper).updateEntity(book, bookRequest);

        doNothing().when(bookRepositoryImpl).persist(book);

        Book result = bookServiceImpl.updateBook(bookRequest);

        assertNotNull(result);
        assertEquals(LONG_1, result.getId());
        assertEquals(ISBN, result.getIsbn());
        assertEquals(AUTHOR_NAME, result.getAuthorName());
        assertEquals(TITLE, result.getTitle());
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(LOCAL_DATE_NOW, result.getReleaseDate());
    }

    @Test
    void updateBook_EXCEPTION() {
        when(bookRepositoryImpl.findBookByIsbn(anyString()))
                .thenReturn(Optional.empty())
                .thenThrow(EntityNotFoundException.class);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> bookServiceImpl.updateBook(bookRequest));

        assertEquals(EntityNotFoundException.class, exception.getClass());
        assertEquals(ExceptionMessages.BOOK_NOT_FOUND, exception.getMessage());
    }

    @Test
    void deleteBookById_SUCCESS_TRUE() {
        when(bookRepositoryImpl.deleteById(anyLong()))
                .thenReturn(Boolean.TRUE);

        bookServiceImpl.deleteBookById(LONG_1);

        verify(bookRepositoryImpl, times(1)).deleteById(LONG_1);
    }

    @Test
    void deleteBookById_SUCCESS_FALSE() {
        when(bookRepositoryImpl.deleteById(anyLong()))
                .thenReturn(Boolean.FALSE);

        bookServiceImpl.deleteBookById(LONG_1);

        verify(bookRepositoryImpl, times(1)).deleteById(LONG_1);
    }
}
