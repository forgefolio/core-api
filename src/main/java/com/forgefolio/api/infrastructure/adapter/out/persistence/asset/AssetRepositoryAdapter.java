package com.forgefolio.api.infrastructure.adapter.out.persistence.asset;

import com.forgefolio.api.application.port.out.asset.AssetRepository;
import com.forgefolio.api.domain.dto.Pair;
import com.forgefolio.api.domain.exception.ErrorCode;
import com.forgefolio.api.domain.exception.NotFoundException;
import com.forgefolio.api.domain.model.asset.Asset;
import com.forgefolio.api.domain.model.asset.AssetPrice;
import com.forgefolio.api.domain.model.shared.Id;
import com.forgefolio.api.domain.pagination.PageResponse;
import com.forgefolio.api.domain.pagination.asset.ListAssetsCommand;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.hibernate.reactive.mutiny.Mutiny;

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
    @WithSession
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
    @WithSession
    public Uni<Asset> findById(Id id) {
        return assetPanacheRepository.findById(id.getValue())
                .onItem().ifNull().failWith(new NotFoundException(ErrorCode.ASSET_NOT_FOUND))
                .map(AssetEntity::toDomain);
    }

    @Override
    @WithTransaction
    public Uni<List<Asset>> upsertAssets(List<Asset> assets) {
        String baseSql = """
                INSERT INTO assets (id, ticker, name)
                VALUES
                """;

        StringBuilder values = new StringBuilder();
        for (int i = 0; i < assets.size(); i++) {
            if (i > 0) values.append(", ");

            values.append("(")
                    .append(":id_").append(i)
                    .append(", :ticker_").append(i)
                    .append(", :name_").append(i)
                    .append(")");
        }

        String conflictSql = """
                ON CONFLICT (ticker) DO UPDATE
                SET name = EXCLUDED.name
                RETURNING id, ticker, name
                """;

        String sql = baseSql + values + conflictSql;

        return Panache.getSession().flatMap(session -> {
            Mutiny.Query<Object> query = session.createNativeQuery(sql);

            for (int i = 0; i < assets.size(); i++) {
                Asset asset = assets.get(i);

                query.setParameter("id_" + i, asset.getId().getValue());
                query.setParameter("ticker_" + i, asset.getTicker().getValue());
                query.setParameter("name_" + i, asset.getName());
            }

            return query.getResultList()
                    .map(results ->
                            results.stream()
                                    .map(row -> {
                                        Object[] cols = (Object[]) row;

                                        UUID id = (UUID) cols[0];
                                        String ticker = (String) cols[1];
                                        String name = (String) cols[2];

                                        return new AssetEntity(id, ticker, name);
                                    })
                                    .map(AssetEntity::toDomain)
                                    .toList()
                    );
        });
    }

    @Override
    @WithTransaction
    public Uni<Void> saveAssetPrices(List<AssetPrice> assetPrices) {
        String baseSql = """
                INSERT INTO asset_prices (id, asset_id, price, date)
                VALUES
                """;

        StringBuilder values = new StringBuilder();
        for (int i = 0; i < assetPrices.size(); i++) {
            if (i > 0) values.append(", ");

            values.append("(")
                    .append(":id_").append(i)
                    .append(", :asset_id_").append(i)
                    .append(", :price_").append(i)
                    .append(", :date_").append(i)
                    .append(")");
        }

        String sql = baseSql + values;

        return Panache.getSession().flatMap(session -> {
            Mutiny.Query<Void> query = session.createNativeQuery(sql);

            for (int i = 0; i < assetPrices.size(); i++) {
                AssetPrice assetPrice = assetPrices.get(i);

                query.setParameter("id_" + i, assetPrice.getId().getValue());
                query.setParameter("asset_id_" + i, assetPrice.getAsset().getId().getValue());
                query.setParameter("price_" + i, assetPrice.getPrice().getValue());
                query.setParameter("date_" + i, assetPrice.getDate().toOffsetDateTime());
            }

            return query.executeUpdate().replaceWithVoid();
        });
    }
}
