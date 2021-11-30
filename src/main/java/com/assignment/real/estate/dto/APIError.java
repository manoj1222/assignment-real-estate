package com.assignment.real.estate.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
public class APIError {

    private final HttpStatus status;
    private final String message;
    private final List<String> errors = new ArrayList<>();

    public APIError(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }

    public APIError(final HttpStatus status, final String message, final List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors.addAll(errors);
    }

    public APIError(final HttpStatus status, final String message, final String error) {
        this.status = status;
        this.message = message;
        this.errors.add(error);
    }

}
