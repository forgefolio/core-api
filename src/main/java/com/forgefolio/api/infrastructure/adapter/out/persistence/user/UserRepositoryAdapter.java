package com.forgefolio.api.infrastructure.adapter.out.persistence.user;

import com.forgefolio.api.application.port.out.user.UserRepository;
import com.forgefolio.api.domain.model.shared.Id;
import com.forgefolio.api.domain.model.user.User;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepositoryAdapter implements UserRepository {

    private final UserPanacheRepository userPanacheRepository;

    public UserRepositoryAdapter(UserPanacheRepository userPanacheRepository) {
        this.userPanacheRepository = userPanacheRepository;
    }

    @Override
    public Uni<Void> save(User user) {
        UserEntity entity = new UserEntity(user);

        return userPanacheRepository.persist(entity).replaceWithVoid();
    }

    @Override
    public Uni<User> findById(Id id) {
        return userPanacheRepository.findById(id.getValue())
                .map(UserEntity::toDomain);
    }
}
