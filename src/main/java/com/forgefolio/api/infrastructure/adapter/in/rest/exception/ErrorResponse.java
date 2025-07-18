package com.forgefolio.api.infrastructure.adapter.in.rest.exception;

import com.forgefolio.api.domain.exception.ErrorCode;

public record ErrorResponse(
        ErrorCode code
) {
}
