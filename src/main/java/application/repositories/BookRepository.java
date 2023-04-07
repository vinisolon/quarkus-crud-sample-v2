package application.repositories;

import application.entities.Book;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BookRepository implements PanacheRepository<Book> {

    public boolean existsBookByIsbn(String isbn) {
        return this.find("isbn = ?1", isbn).count() > 0;
    }
}
