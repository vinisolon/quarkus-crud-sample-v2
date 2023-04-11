package application.repositories;

import application.entities.Book;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class BookRepository implements PanacheRepository<Book> {

    public boolean existsBookByIsbn(String isbn) {
        return this.find("isbn", isbn).count() > 0;
    }

    public Optional<Book> findBookByIsbn(String isbn) {
        return this.find("isbn", isbn).stream().findFirst();
    }
}
