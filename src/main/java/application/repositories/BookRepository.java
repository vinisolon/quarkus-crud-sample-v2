package application.repositories;

import application.entities.Book;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.Optional;

public interface BookRepository extends PanacheRepository<Book> {

    boolean existsBookByIsbn(String isbn);

    Optional<Book> findBookByIsbn(String isbn);

}
