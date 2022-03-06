package com.bootcamp.retoreactive.core.exception;

import org.springframework.http.HttpStatus;

public class BlogMaxException extends RuntimeException {

    private HttpStatus status = HttpStatus.BAD_REQUEST;
    private String message;

    public BlogMaxException(String message) {
        this.message = message;
    }
}
