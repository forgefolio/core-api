CREATE TABLE entries
(
    id           UUID PRIMARY KEY,

    date         TIMESTAMPTZ     NOT NULL,
    type         VARCHAR         NOT NULL,
    amount       DECIMAL(20, 10) NOT NULL,
    unit_price   DECIMAL(20, 10) NOT NULL,

    portfolio_id UUID            NOT NULL,
    CONSTRAINT fk_entries_portfolio_id FOREIGN KEY (portfolio_id) REFERENCES portfolios (id),

    asset_id     UUID            NOT NULL,
    CONSTRAINT fk_entries_asset_id FOREIGN KEY (asset_id) REFERENCES assets (id)
);

CREATE INDEX idx_entries_portfolio_id
    ON entries (portfolio_id);
