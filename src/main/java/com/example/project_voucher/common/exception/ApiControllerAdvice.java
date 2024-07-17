package com.example.project_voucher.common.exception;

import org.apache.logging.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;


@RestControllerAdvice
public class ApiControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(ApiControllerAdvice.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
        log.info(Arrays.toString(e.getStackTrace()));
        return createErrorResponse(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ErrorResponse handleIllegalStateException(IllegalStateException e) {
        log.info(Arrays.toString(e.getStackTrace()));
        return createErrorResponse(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
        log.error(Arrays.toString(e.getStackTrace()));
        return createErrorResponse(e.getMessage());
    }

    private static ErrorResponse createErrorResponse(final String e) {
        return new ErrorResponse(e, LocalDateTime.now(), UUID.randomUUID());
    }
}












