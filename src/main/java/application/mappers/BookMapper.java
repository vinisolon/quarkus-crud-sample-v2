package application.mappers;

import application.entities.Book;
import application.requests.BookRequest;
import application.responses.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface BookMapper {

    BookResponse toResponse(Book book);

    Book toEntity(BookRequest request);

    void update(@MappingTarget Book book, BookRequest request);
}
