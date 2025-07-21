CREATE TABLE asset_prices
(
    id       UUID PRIMARY KEY,

    date     TIMESTAMPTZ     NOT NULL,
    price    DECIMAL(20, 10) NOT NULL,

    asset_id UUID            NOT NULL,
    CONSTRAINT fk_asset_prices_asset_id FOREIGN KEY (asset_id) REFERENCES assets (id)
);

CREATE INDEX idx_asset_prices_asset_id_date_desc
    ON asset_prices (asset_id, date DESC);