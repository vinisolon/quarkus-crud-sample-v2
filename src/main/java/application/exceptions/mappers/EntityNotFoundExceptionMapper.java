package application.exceptions.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EnumConstantNotPresentException> {

    @Override
    public Response toResponse(EnumConstantNotPresentException e) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
