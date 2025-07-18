package com.forgefolio.api.domain.model.user;

import com.forgefolio.api.domain.model.shared.Id;

public class User {

    private final Id id;

    public User() {
        this.id = new Id();
    }

    public User(Id id) {
        this.id = id;
    }

    public Id getId() {
        return id;
    }
}
