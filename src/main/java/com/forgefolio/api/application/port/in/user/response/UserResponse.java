package com.forgefolio.api.application.port.in.user.response;

import com.forgefolio.api.domain.model.user.User;

public class UserResponse {

    private final String id;

    public UserResponse(User user) {
        this.id = user.getId().toString();
    }

    public String getId() {
        return id;
    }
}
