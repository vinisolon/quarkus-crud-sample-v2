package application.controllers;

import application.entities.Book;
import application.exceptions.DataIntegrityViolationException;
import application.exceptions.EntityNotFoundException;
import application.mappers.BookMapper;
import application.requests.BookRequest;
import application.responses.BookResponse;
import application.services.impl.BookServiceImpl;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@QuarkusTest
class BookControllerTest {

    @InjectMock
    private BookServiceImpl bookServiceImpl;

    @InjectMock
    private BookMapper bookMapper;

    private static final String BASE_END_POINT = "/books";
    private static final String ID_PARAM = "/{id}";

    private static final Long LONG_1 = 1L;
    private static final String ISBN = "1284";
    private static final String AUTHOR_NAME = "AUTHOR_NAME";
    private static final String TITLE = "TITLE";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final LocalDate LOCAL_DATE_NOW = LocalDate.now();

    private Book book;
    private BookRequest bookRequest;
    private BookResponse bookResponse;
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

        bookResponse = BookResponse.builder()
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
        when(bookServiceImpl.getAllBooks())
                .thenReturn(bookList);

        when(bookMapper.toBookResponse(any()))
                .thenReturn(bookResponse);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_END_POINT)
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void getBookById_SUCCESS() {
        when(bookServiceImpl.getBookById(any()))
                .thenReturn(book);

        when(bookMapper.toBookResponse(any()))
                .thenReturn(bookResponse);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_END_POINT + ID_PARAM, LONG_1)
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void getBookById_EXCEPTION() {
        when(bookServiceImpl.getBookById(any()))
                .thenThrow(EntityNotFoundException.class);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_END_POINT + ID_PARAM, LONG_1)
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    void createBook_SUCCESS() {
        when(bookServiceImpl.createBook(any()))
                .thenReturn(book);

        when(bookMapper.toBookResponse(any()))
                .thenReturn(bookResponse);

        given()
                .contentType(ContentType.JSON)
                .body(bookRequest)
                .when()
                .post(BASE_END_POINT)
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    void createBook_EXCEPTION() {
        when(bookServiceImpl.createBook(any()))
                .thenThrow(DataIntegrityViolationException.class);

        given()
                .contentType(ContentType.JSON)
                .body(bookRequest)
                .when()
                .post(BASE_END_POINT)
                .then()
                .statusCode(Response.Status.CONFLICT.getStatusCode());
    }

    @Test
    void updateBook_SUCCESS() {
        when(bookServiceImpl.updateBook(any()))
                .thenReturn(book);

        when(bookMapper.toBookResponse(any()))
                .thenReturn(bookResponse);

        given()
                .contentType(ContentType.JSON)
                .body(bookRequest)
                .when()
                .put(BASE_END_POINT)
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void updateBook_EXCEPTION() {
        when(bookServiceImpl.updateBook(any()))
                .thenThrow(EntityNotFoundException.class);

        given()
                .contentType(ContentType.JSON)
                .body(bookRequest)
                .when()
                .put(BASE_END_POINT)
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    void deleteBookById_SUCCESS() {
        doNothing().when(bookServiceImpl).deleteBookById(any());

        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(BASE_END_POINT + ID_PARAM, LONG_1)
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }
}