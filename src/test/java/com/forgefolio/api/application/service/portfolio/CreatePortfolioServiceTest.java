package com.forgefolio.api.application.service.portfolio;

import com.forgefolio.api.application.port.in.portfolio.command.CreatePortfolioCommand;
import com.forgefolio.api.application.port.out.persistence.PersistenceContextExecutor;
import com.forgefolio.api.application.port.out.persistence.portfolio.PortfolioRepository;
import com.forgefolio.api.application.port.out.persistence.user.UserRepository;
import com.forgefolio.api.domain.model.shared.Id;
import com.forgefolio.api.domain.model.user.User;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreatePortfolioServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final PortfolioRepository portfolioRepository = mock(PortfolioRepository.class);
    private final PersistenceContextExecutor executor = mock(PersistenceContextExecutor.class);
    private final CreatePortfolioService service = new CreatePortfolioService(userRepository, portfolioRepository, executor);

    @Test
    @DisplayName("When creating a portfolio, should return a PortfolioResponse with an ID, user ID, and name")
    void createPortfolio() {
        String userId = UUID.randomUUID().toString();
        String portfolioName = "Test Portfolio";

        when(userRepository.findById(any()))
                .thenReturn(Uni.createFrom().item(new User(new Id(userId))));

        when(portfolioRepository.save(any()))
                .thenReturn(Uni.createFrom().voidItem());

        when(executor.runInTransaction(any()))
                .thenAnswer((Answer<Uni<?>>) invocation -> {
                    Supplier<Uni<?>> supplier = invocation.getArgument(0);
                    return supplier.get();
                });

        CompletableFuture<Void> testDone = new CompletableFuture<>();

        service.createPortfolio(new CreatePortfolioCommand(userId, portfolioName))
                .subscribe()
                .with(portfolioResponse -> {
                            assertNotNull(portfolioResponse);
                            assertNotNull(portfolioResponse.getId());
                            assertNotNull(portfolioResponse.getUserId());
                            assertNotNull(portfolioResponse.getName());

                            assertEquals(userId, portfolioResponse.getUserId());
                            assertEquals(portfolioName, portfolioResponse.getName());

                            verify(userRepository).findById(new Id(userId));
                            verify(portfolioRepository).save(any());
                            verify(executor).runInTransaction(any());

                            testDone.complete(null);
                        },
                        testDone::completeExceptionally
                );

        testDone.join();
    }
}