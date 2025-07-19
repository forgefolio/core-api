package com.forgefolio.api.infrastructure.adapter.out.persistence.asset;

import com.forgefolio.api.application.port.out.asset.AssetRepository;
import com.forgefolio.api.domain.dto.Pair;
import com.forgefolio.api.domain.model.asset.Asset;
import com.forgefolio.api.domain.model.asset.AssetPrice;
import com.forgefolio.api.domain.model.shared.Id;
import com.forgefolio.api.domain.pagination.PageResponse;
import com.forgefolio.api.domain.pagination.asset.ListAssetsCommand;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AssetRepositoryAdapter implements AssetRepository {

    private final AssetPanacheRepository assetPanacheRepository;
    private final AssetPricePanacheRepository assetPricePanacheRepository;

    public AssetRepositoryAdapter(AssetPanacheRepository assetPanacheRepository, AssetPricePanacheRepository assetPricePanacheRepository) {
        this.assetPanacheRepository = assetPanacheRepository;
        this.assetPricePanacheRepository = assetPricePanacheRepository;
    }

    @Override
    public Uni<PageResponse<Pair<Asset, AssetPrice>>> findAssetsWithCurrentPrices(ListAssetsCommand command) {
        String sort = command.getSortQuery();
        String tickerFilter = command.getTicker() != null ? command.getTicker().toUpperCase() + "%" : "%";
        int offset = command.getPage() * command.getSize();
        int limit = command.getSize();

        String baseSql = """
                    WITH latest_prices AS (
                        SELECT DISTINCT ON (a.id)
                            a.id AS asset_id,
                            a.ticker,
                            a.name,
                            ap.id AS price_id,
                            ap.price,
                            ap.date
                        FROM assets a
                        LEFT JOIN asset_prices ap ON ap.asset_id = a.id
                        WHERE a.ticker LIKE :ticker
                        ORDER BY a.id, ap.date DESC
                    )
                    SELECT * FROM latest_prices
                    ORDER BY %s
                    OFFSET :offset LIMIT :limit
                """.formatted(sort);

        String countSql = """
                    SELECT COUNT(a.id) AS total
                    FROM assets a
                    WHERE a.ticker LIKE :ticker
                """;
        return Panache.getSession().flatMap(session -> {
            Uni<List<Pair<Asset, AssetPrice>>> contentUni = session
                    .createNativeQuery(baseSql)
                    .setParameter("ticker", tickerFilter)
                    .setParameter("offset", offset)
                    .setParameter("limit", limit)
                    .getResultList()
                    .map(results -> results.stream().map(row -> {
                        Object[] cols = (Object[]) row;

                        UUID assetId = (UUID) cols[0];
                        String ticker = (String) cols[1];
                        String name = (String) cols[2];
                        UUID priceId = (UUID) cols[3];
                        BigDecimal price = (BigDecimal) cols[4];
                        OffsetDateTime date = (OffsetDateTime) cols[5];

                        Asset asset = new Asset(assetId, ticker, name);
                        AssetPrice assetPrice = new AssetPrice(priceId, asset, price,
                                date != null ? date.toZonedDateTime() : null);

                        return new Pair<>(asset, assetPrice);
                    }).toList());

            Uni<Long> countUni = session
                    .createNativeQuery(countSql)
                    .setParameter("ticker", tickerFilter)
                    .getSingleResult()
                    .map(result -> ((Number) result).longValue());

            return Uni.combine().all().unis(contentUni, countUni).asTuple()
                    .map(tuple -> new PageResponse<>(
                            command.getPage(),
                            command.getSize(),
                            tuple.getItem2(),
                            tuple.getItem1()
                    ));
        });
    }

    @Override
    public Uni<Asset> findById(Id id) {
        return assetPanacheRepository.findById(id.getValue())
                .map(AssetEntity::toDomain);
    }

    @Override
    public Uni<Asset> upsertAsset(Asset asset) {
        return assetPanacheRepository.findByTicker(asset.getTicker().getValue())
                .flatMap(existing -> {
                    if (existing != null) {
                        existing.setName(asset.getName());
                        return assetPanacheRepository.persist(existing).map(AssetEntity::toDomain);
                    } else {
                        AssetEntity newEntity = new AssetEntity(asset);
                        return assetPanacheRepository.persist(newEntity).map(AssetEntity::toDomain);
                    }
                });
    }

    @Override
    public Uni<Void> saveAssetPrice(AssetPrice assetPrice) {
        AssetPriceEntity entity = new AssetPriceEntity(assetPrice);
        return assetPricePanacheRepository.persist(entity).replaceWithVoid();
    }
}
