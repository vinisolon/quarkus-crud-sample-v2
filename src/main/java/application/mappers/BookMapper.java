package application.mappers;

import application.entities.Book;
import application.requests.BookRequest;
import application.responses.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface BookMapper {

    BookResponse toBookResponse(Book book);

    Book toEntity(BookRequest request);

    void updateEntity(@MappingTarget Book book, BookRequest request);

}
