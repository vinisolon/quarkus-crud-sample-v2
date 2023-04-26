package application.repositories;

import application.entities.Book;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class BookRepository implements PanacheRepository<Book> {

    private static final String ISBN = "isbn";

    public boolean existsBookByIsbn(String isbn) {
        return count(ISBN, isbn) > 0;
    }

    public Optional<Book> findBookByIsbn(String isbn) {
        return find(ISBN, isbn).stream().findFirst();
    }
}
