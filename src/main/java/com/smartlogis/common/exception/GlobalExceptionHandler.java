package com.smartlogis.common.exception;

import com.smartlogis.common.presentation.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${spring.application.name:UNKNOWN-SERVICE")
    private String service;

    @ExceptionHandler(AbstractException.class)
    public ResponseEntity<ApiResponse<Void>> globalException(AbstractException e) {
        if (e.getMessageArguments().length == 0) {
            ApiResponse<Void> failure = ApiResponse.failure(e.getMessageCode().getCode());
            log.error("[{}] {}", e.getClass().getSimpleName(), e.getMessage());
            return new ResponseEntity<>(failure, e.getMessageCode().getStatus());
        }

        ApiResponse<Void> failure = ApiResponse.failure(e.getMessageCode().getCode(), e.toString(), e.getMessageArguments());
        log.error("[{}] {}", e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(failure, e.getMessageCode().getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> exception(Exception e) {
        ApiResponse<Void> failure = ApiResponse.failure(service.toUpperCase() + ".UNKNOWN");
        log.error("[{}] {}", e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(failure, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
