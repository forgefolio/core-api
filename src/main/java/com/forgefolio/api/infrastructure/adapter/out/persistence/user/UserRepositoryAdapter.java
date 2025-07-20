package com.forgefolio.api.infrastructure.adapter.out.persistence.user;

import com.forgefolio.api.application.port.out.persistence.user.UserRepository;
import com.forgefolio.api.domain.exception.ErrorCode;
import com.forgefolio.api.domain.exception.NotFoundException;
import com.forgefolio.api.domain.model.shared.Id;
import com.forgefolio.api.domain.model.user.User;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepositoryAdapter implements UserRepository {

    private final UserPanacheRepository userPanacheRepository;

    public UserRepositoryAdapter(UserPanacheRepository userPanacheRepository) {
        this.userPanacheRepository = userPanacheRepository;
    }

    @Override
    @WithTransaction
    public Uni<Void> save(User user) {
        UserEntity entity = new UserEntity(user);

        return userPanacheRepository.persist(entity).replaceWithVoid();
    }

    @Override
    @WithSession
    public Uni<User> findById(Id id) {
        return userPanacheRepository.findById(id.getValue())
                .onItem().ifNull().failWith(new NotFoundException(ErrorCode.USER_NOT_FOUND))
                .map(UserEntity::toDomain);
    }
}
