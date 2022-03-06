package com.bootcamp.retoreactive.core.exception;

import org.springframework.http.HttpStatus;

public class BlogAgeException extends RuntimeException {

    private HttpStatus status = HttpStatus.BAD_REQUEST;
    private String message;

    public BlogAgeException(String message) {
        this.message = message;
    }
}
