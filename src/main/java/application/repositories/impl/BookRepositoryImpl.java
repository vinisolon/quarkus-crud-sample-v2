package application.repositories.impl;

import application.entities.Book;
import application.repositories.BookRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class BookRepositoryImpl implements BookRepository {

    private static final String ISBN = "isbn";

    public boolean existsBookByIsbn(String isbn) {
        return count(ISBN, isbn) > 0;
    }

    public Optional<Book> findBookByIsbn(String isbn) {
        return find(ISBN, isbn).firstResultOptional();
    }
}
