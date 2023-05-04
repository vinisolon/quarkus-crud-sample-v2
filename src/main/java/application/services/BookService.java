package application.services;

import application.entities.Book;
import application.requests.BookRequest;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book createBook(BookRequest request);

    Book updateBook(BookRequest request);

    void deleteBookById(Long id);

}
