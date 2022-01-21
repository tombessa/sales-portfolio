package github.com.tombessa.salesportfolio.exception;

import github.com.tombessa.salesportfolio.util.Constants;
import github.com.tombessa.salesportfolio.util.Messages;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
@EnableWebMvc
public class UniversalExceptionHandler {


    private Messages messages;

    public UniversalExceptionHandler(Messages messages) {
        this.messages = messages;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public void handleNotFound(ResourceNotFoundException ex, HttpServletResponse resp) throws IOException {
        resp.sendError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public void handleBadRequest(IOException ex, HttpServletResponse resp) throws IOException {
        resp.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage() + " Detail: " + NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public void handleConflict(BusinessException ex, HttpServletResponse resp) throws IOException {
        resp.sendError(HttpStatus.CONFLICT.value(), ex.getMessage() );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDenied(AccessDeniedException ex, HttpServletResponse resp) throws IOException {
        resp.sendError(HttpStatus.FORBIDDEN.value(), messages.get(Constants.EXCEPTION_USER_NOT_FOUND));
    }
}
