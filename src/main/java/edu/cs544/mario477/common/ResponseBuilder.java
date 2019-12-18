package edu.cs544.mario477.common;

import org.springframework.http.HttpStatus;

import java.util.Collections;

public class ResponseBuilder {
    private static final String DEFAULT_SUCCESS_MESSAGE = "success";

    public static Response<Void> buildSuccess() {
        return new Response()
                .setCode(HttpStatus.OK)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> Response<T> buildSuccess(T data) {
        return new Response()
                .setCode(HttpStatus.OK)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static <T> Response<T> buildSuccess(String message, T data) {
        return new Response()
                .setCode(HttpStatus.OK)
                .setMessage(message)
                .setData(data);
    }

    public static Response buildFail(String message) {
        return new Response()
                .setCode(HttpStatus.BAD_REQUEST)
                .setMessage(message);
    }

    public static Response buildFail(HttpStatus status, String message) {
        return new Response()
                .setCode(status)
                .setMessage(message);
    }

    public static Response buildFail(HttpStatus code, String message, Object errors) {
        return new Response()
                .setCode(code)
                .setMessage(message)
                .setErrors(errors);
    }

    public static Response<Object> buildFail(HttpStatus code, String message, String error) {
        return new Response()
                .setCode(code)
                .setMessage(message)
                .setErrors(Collections.singletonList(error));
    }
}
