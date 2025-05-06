package com.ssafy.ssafyhome.global.exception;

import com.ssafy.ssafyhome.global.exception.enums.ErrorType;
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
