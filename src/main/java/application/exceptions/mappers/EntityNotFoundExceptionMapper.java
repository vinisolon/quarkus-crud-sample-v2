package application.exceptions.mappers;

import application.exceptions.EntityNotFoundException;
import application.responses.ErrorResponse;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.time.LocalDateTime;

@Provider
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(EntityNotFoundException e) {
        ErrorResponse response = ErrorResponse.builder()
                .status(Response.Status.NOT_FOUND.getStatusCode())
                .path(uriInfo.getRequestUri().getPath())
                .message(e.getMessage())
                .date(LocalDateTime.now())
                .build();
        return Response.status(Response.Status.NOT_FOUND).entity(response).build();
    }
}
