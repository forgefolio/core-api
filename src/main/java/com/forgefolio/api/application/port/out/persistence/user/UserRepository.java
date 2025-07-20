package com.forgefolio.api.application.port.out.persistence.user;

import com.forgefolio.api.domain.model.shared.Id;
import com.forgefolio.api.domain.model.user.User;
import io.smallrye.mutiny.Uni;

public interface UserRepository {

    Uni<Void> save(User user);

    Uni<User> findById(Id id);

}
