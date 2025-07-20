package com.forgefolio.api.application.service.portfolio;

import com.forgefolio.api.application.port.in.portfolio.CreateEntryUseCase;
import com.forgefolio.api.application.port.in.portfolio.command.CreateEntryCommand;
import com.forgefolio.api.application.port.in.portfolio.response.EntryResponse;
import com.forgefolio.api.application.port.out.persistence.PersistenceContextExecutor;
import com.forgefolio.api.application.port.out.persistence.asset.AssetRepository;
import com.forgefolio.api.application.port.out.persistence.portfolio.EntryRepository;
import com.forgefolio.api.application.port.out.persistence.portfolio.PortfolioRepository;
import com.forgefolio.api.domain.exception.ErrorCode;
import com.forgefolio.api.domain.exception.NotFoundException;
import com.forgefolio.api.domain.model.portfolio.Entry;
import com.forgefolio.api.domain.model.shared.Id;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateEntryService implements CreateEntryUseCase {

    private final PortfolioRepository portfolioRepository;
    private final AssetRepository assetRepository;
    private final EntryRepository entryRepository;
    private final PersistenceContextExecutor persistenceContextExecutor;

    public CreateEntryService(PortfolioRepository portfolioRepository, AssetRepository assetRepository, EntryRepository entryRepository, PersistenceContextExecutor persistenceContextExecutor) {
        this.portfolioRepository = portfolioRepository;
        this.assetRepository = assetRepository;
        this.entryRepository = entryRepository;
        this.persistenceContextExecutor = persistenceContextExecutor;
    }

    @Override
    public Uni<EntryResponse> createEntry(CreateEntryCommand command) {
        Id portfolioId = new Id(command.getPortfolioId());
        Id assetId = new Id(command.getAssetId());

        return persistenceContextExecutor.runInTransaction(() ->
                portfolioRepository.findById(portfolioId)
                        .onItem().ifNull().failWith(() -> new NotFoundException(ErrorCode.PORTFOLIO_NOT_FOUND))
                        .flatMap(portfolio ->
                                assetRepository.findById(assetId)
                                        .onItem().ifNull().failWith(() -> new NotFoundException(ErrorCode.ASSET_NOT_FOUND))
                                        .flatMap(asset -> {
                                            Entry entry = new Entry(portfolio, asset, command.getDate(),
                                                    command.getType(), command.getAmount(), command.getUnitPrice());

                                            return entryRepository.save(entry)
                                                    .replaceWith(entry);
                                        })
                                        .flatMap(entry -> {
                                            Uni<Void> updateAmount;

                                            if (entry.getType() == Entry.Type.BUY)
                                                updateAmount = portfolioRepository.increaseAssetAmount(entry.getPortfolio(), entry.getAsset(), entry.getAmount());
                                            else
                                                updateAmount = portfolioRepository.decreaseAssetAmount(entry.getPortfolio(), entry.getAsset(), entry.getAmount());


                                            return updateAmount
                                                    .replaceWith(new EntryResponse(entry));
                                        })
                        )
        );
    }
}
