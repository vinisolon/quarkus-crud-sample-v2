package application.exceptions.mappers;

import application.exceptions.DataIntegrityViolationException;
import application.responses.ErrorMessageResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.time.LocalDateTime;

@Provider
public class DataIntegrityViolationExceptionMapper implements ExceptionMapper<DataIntegrityViolationException> {

    @Override
    public Response toResponse(DataIntegrityViolationException e) {
        ErrorMessageResponse response = ErrorMessageResponse.builder()
                .status(Response.Status.CONFLICT.getStatusCode())
                .message(e.getMessage())
                .date(LocalDateTime.now())
                .build();
        return Response.status(Response.Status.CONFLICT).entity(response).build();
    }
}
