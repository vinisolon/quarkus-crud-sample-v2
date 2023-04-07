package application.exceptions.mappers;

import application.exceptions.EntityNotFoundException;
import application.responses.ErrorMessageResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.time.LocalDateTime;

@Provider
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException> {

    @Override
    public Response toResponse(EntityNotFoundException e) {
        ErrorMessageResponse response = ErrorMessageResponse.builder()
                .status(Response.Status.NOT_FOUND.getStatusCode())
                .message(e.getMessage())
                .date(LocalDateTime.now())
                .build();
        return Response.status(Response.Status.NOT_FOUND).entity(response).build();
    }
}
