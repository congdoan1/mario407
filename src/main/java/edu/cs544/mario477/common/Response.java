package edu.cs544.mario477.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private int code;
    private String message;
    private Object errors;
    private T data;

    public Response<T> setCode(HttpStatus httpStatus) {
        this.code = httpStatus.value();
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Response setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Response setData(T data) {
        this.data = data;
        return this;
    }

    public Object getErrors() {
        return errors;
    }

    public Response setErrors(Object errors) {
        this.errors = errors;
        return this;
    }
}
