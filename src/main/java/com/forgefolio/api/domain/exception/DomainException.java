package com.forgefolio.api.domain.exception;


public abstract class DomainException extends RuntimeException {
    private final ErrorCode errorCode;

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public DomainException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
