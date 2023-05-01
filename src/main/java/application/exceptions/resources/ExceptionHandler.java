package application.exceptions.resources;

import application.exceptions.DataIntegrityViolationException;
import application.exceptions.EntityNotFoundException;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ExceptionHandler {

    @Context
    UriInfo uriInfo;

    @ServerExceptionMapper(Exception.class)
    public Response exceptionMapper(Exception e) {
        ExceptionResponse response = ExceptionResponse.builder()
                .status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                .path(uriInfo.getRequestUri().getPath())
                .message(e.getMessage())
                .date(LocalDateTime.now())
                .build();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
    }

    @ServerExceptionMapper(ConstraintViolationException.class)
    public Response constraintViolationExceptionMapper(ConstraintViolationException e) {
        List<ConstraintViolation<?>> violations = new ArrayList<>(e.getConstraintViolations());
        ExceptionResponse response = ExceptionResponse.builder()
                .status(Response.Status.BAD_REQUEST.getStatusCode())
                .path(uriInfo.getRequestUri().getPath())
                .message(getViolationsMessages(violations))
                .date(LocalDateTime.now())
                .build();
        return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
    }

    @ServerExceptionMapper(EntityNotFoundException.class)
    public Response entityNotFoundExceptionMapper(EntityNotFoundException e) {
        ExceptionResponse response = ExceptionResponse.builder()
                .status(Response.Status.NOT_FOUND.getStatusCode())
                .path(uriInfo.getRequestUri().getPath())
                .message(e.getMessage())
                .date(LocalDateTime.now())
                .build();
        return Response.status(Response.Status.NOT_FOUND).entity(response).build();
    }

    @ServerExceptionMapper(DataIntegrityViolationException.class)
    public Response dataIntegrityViolationExceptionMapper(DataIntegrityViolationException e) {
        ExceptionResponse response = ExceptionResponse.builder()
                .status(Response.Status.CONFLICT.getStatusCode())
                .path(uriInfo.getRequestUri().getPath())
                .message(e.getMessage())
                .date(LocalDateTime.now())
                .build();
        return Response.status(Response.Status.CONFLICT).entity(response).build();
    }

    private String getViolationsMessages(List<ConstraintViolation<?>> violations) {
        StringJoiner messages = new StringJoiner(", ");
        violations.forEach(v -> messages.add(v.getMessage()));
        return messages.toString();
    }
}
