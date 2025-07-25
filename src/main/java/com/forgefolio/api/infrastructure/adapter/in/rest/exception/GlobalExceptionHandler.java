package com.forgefolio.api.infrastructure.adapter.in.rest.exception;

import com.forgefolio.api.domain.exception.BadArgumentException;
import com.forgefolio.api.domain.exception.ErrorCode;
import com.forgefolio.api.domain.exception.NotFoundException;
import io.quarkus.vertx.web.ReactiveRoutes;
import io.quarkus.vertx.web.Route;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GlobalExceptionHandler {

    @Route(type = Route.HandlerType.FAILURE, produces = ReactiveRoutes.APPLICATION_JSON, order = 1)
    void handleNotFound(NotFoundException e, HttpServerResponse response) {
        response.setStatusCode(404)
                .end(Json.encode(new ErrorResponse(e.getErrorCode())));
    }

    @Route(type = Route.HandlerType.FAILURE, produces = ReactiveRoutes.APPLICATION_JSON, order = 2)
    void handleBadRequest(BadArgumentException e, HttpServerResponse response) {
        response.setStatusCode(400)
                .end(Json.encode(new ErrorResponse(e.getErrorCode())));
    }

    @Route(type = Route.HandlerType.FAILURE, produces = ReactiveRoutes.APPLICATION_JSON, order = 10)
    void handleGeneric(Exception e, HttpServerResponse response) {
        e.printStackTrace();
        response.setStatusCode(500)
                .end(Json.encode(new ErrorResponse(ErrorCode.UNKNOWN_ERROR)));
    }


}
