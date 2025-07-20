package com.forgefolio.api.infrastructure.adapter.in.rest.exception;

import com.forgefolio.api.domain.exception.ErrorCode;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ErrorResponse {
    private ErrorCode code;

    public ErrorResponse() {
    }

    public ErrorResponse(ErrorCode code) {
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }
}
