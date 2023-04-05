package application.controllers;

import application.mappers.BookMapper;
import application.responses.BookResponse;
import application.services.BookService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookController {

    @Inject
    BookService bookService;

    @Inject
    BookMapper bookMapper;

    @GET
    public Response getAllBooks() {
        List<BookResponse> bookResponseList = bookService.getAllBooks()
                .stream()
                .map(book -> bookMapper.toResponse(book))
                .collect(Collectors.toList());
        return Response.ok().entity(bookResponseList).build();
    }
}
