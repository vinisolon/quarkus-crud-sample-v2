package application.services;

import application.constants.ExceptionMessages;
import application.entities.Book;
import application.exceptions.DataIntegrityViolationException;
import application.exceptions.EntityNotFoundException;
import application.mappers.BookMapper;
import application.repositories.BookRepository;
import application.requests.BookRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class BookService {

    @Inject
    BookRepository bookRepository;

    @Inject
    BookMapper bookMapper;

    public List<Book> getAllBooks() {
        return bookRepository.listAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findByIdOptional(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessages.BOOK_NOT_FOUND));
    }

    public Book saveBook(BookRequest request) {
        if (bookRepository.existsBookByIsbn(request.getIsbn())) {
            throw new DataIntegrityViolationException(ExceptionMessages.BOOK_ALREADY_REGISTERED);
        }

        Book book = bookMapper.toEntity(request);
        bookRepository.persist(book);
        return book;
    }
}
