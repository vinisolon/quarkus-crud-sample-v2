package application.exceptions.resources;

import application.exceptions.DataIntegrityViolationException;
import application.exceptions.EntityNotFoundException;
import application.requests.BookRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@QuarkusTest
class ExceptionHandlerTest {

    @Inject
    ExceptionHandler exceptionHandler;

    @Inject
    Validator validator;

    @InjectMock
    UriInfo uriInfo;

    private static final String STRING_TEST = "test";

    @BeforeEach
    void setUp() {
        when(uriInfo.getPath()).thenReturn(STRING_TEST);
    }

    @Test
    void exceptionMapper() {
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                exceptionHandler.exceptionMapper(new Exception(STRING_TEST)).getStatus());
    }

    @Test
    void constraintViolationExceptionMapper() {
        Set<ConstraintViolation<BookRequest>> violations = validator.validate(BookRequest.builder().build());
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),
                exceptionHandler.constraintViolationExceptionMapper(new ConstraintViolationException(violations)).getStatus());
    }

    @Test
    void entityNotFoundExceptionMapper() {
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),
                exceptionHandler.entityNotFoundExceptionMapper(new EntityNotFoundException(STRING_TEST)).getStatus());
    }

    @Test
    void dataIntegrityViolationExceptionMapper() {
        assertEquals(Response.Status.CONFLICT.getStatusCode(),
                exceptionHandler.dataIntegrityViolationExceptionMapper(new DataIntegrityViolationException(STRING_TEST)).getStatus());
    }
}