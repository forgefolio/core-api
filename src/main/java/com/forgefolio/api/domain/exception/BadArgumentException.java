package com.forgefolio.api.domain.exception;

public class BadArgumentException extends DomainException {
    public BadArgumentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
