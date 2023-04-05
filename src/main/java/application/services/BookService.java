package application.services;

import application.entities.Book;
import application.repositories.BookRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class BookService {

    @Inject
    BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.listAll();
    }
}
