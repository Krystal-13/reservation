package com.zerobase.reservation.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /* ControllerAdvice는 Filter, Interceptor 단에서 발생하는 Exception은 처리를 해주지 못함. */

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {

        log.error("{} is occurred.", e.getErrorCode());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(e.getErrorCode())
                .errorMessage(e.getErrorMessage())
                .status(e.getErrorCode().getStatus())
                .build();

        return new ResponseEntity<>(errorResponse, e.getErrorCode().getStatus());
    }

}
