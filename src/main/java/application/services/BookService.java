package application.services;

import application.constants.ExceptionMessages;
import application.entities.Book;
import application.exceptions.EntityNotFoundException;
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

    public Book getBookById(Long id) {
        return bookRepository.findByIdOptional(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessages.BOOK_NOT_FOUND));
    }
}
