package edu.cs544.mario477.exception;

import edu.cs544.mario477.common.Response;
import edu.cs544.mario477.common.ResponseBuilder;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.NoPermissionException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@ControllerAdvice
public class ControllerHandleException extends ResponseEntityExceptionHandler {

    @Autowired
    MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        return ResponseEntity.badRequest().body(ResponseBuilder.buildFail(HttpStatus.BAD_REQUEST, error));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        return new ResponseEntity<>(
                ResponseBuilder.buildFail(HttpStatus.BAD_REQUEST, builder.substring(0, builder.length() - 2), ex),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<String> errorMessages = result.getAllErrors()
                .stream()
                .map(objectError -> messageSource.getMessage(objectError, Locale.getDefault()))
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(
                ResponseBuilder.buildFail(HttpStatus.BAD_REQUEST, "Validation error", errorMessages)
        );
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        String error = "Malformed JSON request";
        return ResponseEntity.badRequest().body(
                ResponseBuilder.buildFail(HttpStatus.BAD_REQUEST, error, ex.getLocalizedMessage())
        );
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(
            HttpMessageNotWritableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String error = "Error writing JSON output";
        return new ResponseEntity<>(
                ResponseBuilder.buildFail(HttpStatus.INTERNAL_SERVER_ERROR, error, ex.getLocalizedMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return ResponseEntity.badRequest().body(
                ResponseBuilder.buildFail(HttpStatus.BAD_REQUEST,
                        String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()),
                        ex.getMessage())
        );
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(AppException.class)
    public Response handleAppException(AppException e) {
        String errorMsg = "App exception";
        if (e != null) {
            errorMsg = e.getMessage();
        }
        return ResponseBuilder.buildFail(errorMsg);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Response<Object> handleAppException(ResourceNotFoundException e) {
        return ResponseBuilder.buildFail(HttpStatus.NOT_FOUND, "Unexpected error", e.getLocalizedMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        String errorMsg = "Exception";
        if (e != null) {
            errorMsg = e.getCause() != null ? e.getCause().getLocalizedMessage() : e.getLocalizedMessage();
        }
        return ResponseBuilder.buildFail(errorMsg);
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NoPermissionException.class)
    public Response<Object> handleAppException(NoPermissionException e) {
        String errorMsg = "No permission exception";
        if (e != null) {
            errorMsg = e.getMessage();
        }
        return ResponseBuilder.buildFail(HttpStatus.UNAUTHORIZED, "No permission", errorMsg);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        return ResponseEntity.badRequest().body(
                ResponseBuilder.buildFail(HttpStatus.BAD_REQUEST,
                        String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()),
                        ex.getMessage())
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body(
                ResponseBuilder.buildFail(HttpStatus.BAD_REQUEST, "Validation error", ex.getLocalizedMessage()));
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    protected Response<Object> handleMethodArgumentTypeMismatch(
            AccessDeniedException ex,
            WebRequest request) {
        return ResponseBuilder.buildFail(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }
}
