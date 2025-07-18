package com.forgefolio.api.application.port.in.user;

import com.forgefolio.api.application.port.in.user.response.UserResponse;
import io.smallrye.mutiny.Uni;

public interface CreateUserUseCase {

    Uni<UserResponse> createUser();

}
