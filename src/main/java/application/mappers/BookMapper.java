package application.mappers;

import application.entities.Book;
import application.responses.BookResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface BookMapper {

    BookResponse toResponse(Book book);
}
