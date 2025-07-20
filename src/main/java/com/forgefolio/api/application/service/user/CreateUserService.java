package com.forgefolio.api.application.service.user;

import com.forgefolio.api.application.port.in.user.CreateUserUseCase;
import com.forgefolio.api.application.port.in.user.response.UserResponse;
import com.forgefolio.api.application.port.out.persistence.PersistenceContextExecutor;
import com.forgefolio.api.application.port.out.persistence.user.UserRepository;
import com.forgefolio.api.domain.model.user.User;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateUserService implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final PersistenceContextExecutor persistenceContextExecutor;

    public CreateUserService(UserRepository userRepository, PersistenceContextExecutor persistenceContextExecutor) {
        this.userRepository = userRepository;
        this.persistenceContextExecutor = persistenceContextExecutor;
    }

    @Override
    public Uni<UserResponse> createUser() {
        User user = new User();

        return persistenceContextExecutor.runInTransaction(() ->
                userRepository.save(user)
                        .replaceWith(new UserResponse(user))
        );
    }

}
