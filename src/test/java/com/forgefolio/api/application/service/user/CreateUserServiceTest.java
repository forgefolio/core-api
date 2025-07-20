package com.forgefolio.api.application.service.user;

import com.forgefolio.api.application.port.in.user.response.UserResponse;
import com.forgefolio.api.application.port.out.persistence.PersistenceContextExecutor;
import com.forgefolio.api.application.port.out.persistence.user.UserRepository;
import com.forgefolio.api.domain.model.user.User;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateUserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final PersistenceContextExecutor executor = mock(PersistenceContextExecutor.class);
    private final CreateUserService service = new CreateUserService(userRepository, executor);

    @Test
    void createUser() {
        when(userRepository.save(any()))
                .thenReturn(Uni.createFrom().voidItem());

        when(executor.runInTransaction(any()))
                .thenAnswer((Answer<Uni<UserResponse>>) invocation -> {
                    Supplier<Uni<UserResponse>> supplier = invocation.getArgument(0);
                    return supplier.get();
                });

        Uni<UserResponse> result = service.createUser();

        UserResponse userResponse = result.await().indefinitely();

        assertNotNull(userResponse);
        assertNotNull(userResponse.getId());

        verify(userRepository).save(any(User.class));
    }
}