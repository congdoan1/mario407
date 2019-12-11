package edu.cs544.mario477.exception;

import edu.cs544.mario477.common.Response;
import edu.cs544.mario477.common.ResponseBuilder;
import edu.cs544.mario477.common.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.NoPermissionException;
import java.util.ArrayList;
import java.util.List;

@Component
@ControllerAdvice
public class ControllerHandleException {

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
    public Response handleAppException(ResourceNotFoundException e) {
        String errorMsg = "Not found exception";
        if (e != null) {
            errorMsg = e.getMessage();
        }
        return ResponseBuilder.buildFail(ResponseCode.NOT_FOUND, "Resource not found", errorMsg);
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errors.add(error.getDefaultMessage());
        });
        return ResponseBuilder.buildFail(ResponseCode.BAD_REQUEST, "Validation Failed", errors);
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NoPermissionException.class)
    public Response handleAppException(NoPermissionException e) {
        String errorMsg = "No permission exception";
        if (e != null) {
            errorMsg = e.getMessage();
        }
        return ResponseBuilder.buildFail(ResponseCode.UNAUTHORIZED, "No permission", errorMsg);
    }
}
