package com.ssafy.bango.global.exception;

import com.ssafy.bango.global.exception.enums.ErrorType;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private final ErrorType errorType;

    public CustomException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public int getHttpStatus() {
        return errorType.getHttpStatusCode();
    }
}
