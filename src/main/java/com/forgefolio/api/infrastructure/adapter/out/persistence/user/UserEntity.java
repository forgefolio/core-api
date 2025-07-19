package com.forgefolio.api.infrastructure.adapter.out.persistence.user;

import com.forgefolio.api.domain.model.user.User;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity extends PanacheEntityBase {

    @Id
    private UUID id;

    public UserEntity(User user) {
        this.id = user.getId().getValue();
    }

    public UserEntity() {
    }

    public User toDomain() {
        return new User(new com.forgefolio.api.domain.model.shared.Id(id));
    }
}
