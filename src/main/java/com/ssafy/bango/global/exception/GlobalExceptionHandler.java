package com.ssafy.bango.global.exception;

import com.ssafy.bango.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    /**
     * CUSTOM_ERROR
     */
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ApiResponse<?>> handleCustomException(CustomException e) {

//        log.warn("CustomException Occured: {}", e.getMessage());

        return ResponseEntity.status(e.getHttpStatus())
                .body(ApiResponse.error(e.getErrorType()));
    }
}
