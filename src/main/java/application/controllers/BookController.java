package application.controllers;

import application.mappers.BookMapper;
import application.requests.BookRequest;
import application.responses.BookResponse;
import application.services.BookService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
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
        return Response.status(Response.Status.OK).entity(bookResponseList).build();
    }

    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") Long id) {
        BookResponse bookResponse = bookMapper.toResponse(bookService.getBookById(id));
        return Response.status(Response.Status.OK).entity(bookResponse).build();
    }

    @POST
    @Transactional
    public Response createBook(@Valid BookRequest request) {
        BookResponse bookResponse = bookMapper.toResponse(bookService.saveBook(request));
        return Response.status(Response.Status.CREATED).entity(bookResponse).build();
    }
}
