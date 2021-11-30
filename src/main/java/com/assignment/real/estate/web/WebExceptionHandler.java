package com.assignment.real.estate.web;

import com.assignment.real.estate.dto.APIError;
import com.assignment.real.estate.exceptions.BizException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class WebExceptionHandler {

    @ResponseBody
    @ExceptionHandler(BizException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public APIError handleException(final BizException e) {
        log.error(e.getMessage(), e);
        return new APIError(HttpStatus.INTERNAL_SERVER_ERROR, "", e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public APIError handleException(final MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        final List<String> errors = e.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return new APIError(HttpStatus.BAD_REQUEST, "Invalid input", errors);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public APIError handleException(final HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return new APIError(HttpStatus.BAD_REQUEST, e.getMostSpecificCause().getMessage().split(":")[0], e.getMostSpecificCause().getMessage().split(":")[0]);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public APIError handleException(final ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        final List<String> errors = new ArrayList<>();
        for(ConstraintViolation<?> violation: e.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
        return new APIError(HttpStatus.BAD_REQUEST, e.getMessage(), errors);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public APIError handleException(final Exception e) {
        log.error(e.getMessage(), e);
        return new APIError(HttpStatus.INTERNAL_SERVER_ERROR, "", e.getMessage());
    }

}
