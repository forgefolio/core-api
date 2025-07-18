package com.forgefolio.api.domain.exception;

public class NotFoundException extends DomainException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
