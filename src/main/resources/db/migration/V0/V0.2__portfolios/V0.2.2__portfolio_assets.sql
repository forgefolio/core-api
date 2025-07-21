CREATE TABLE portfolio_assets
(
    portfolio_id UUID            NOT NULL,
    CONSTRAINT fk_portfolio_assets_portfolio_id FOREIGN KEY (portfolio_id) REFERENCES portfolios (id),

    asset_id     UUID            NOT NULL,
    CONSTRAINT fk_portfolio_assets_asset_id FOREIGN KEY (asset_id) REFERENCES assets (id),

    CONSTRAINT pk_portfolio_assets PRIMARY KEY (portfolio_id, asset_id),

    amount       DECIMAL(20, 10) NOT NULL
);
