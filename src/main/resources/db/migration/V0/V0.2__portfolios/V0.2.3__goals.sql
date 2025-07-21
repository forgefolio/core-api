CREATE TABLE goals
(
    id           UUID PRIMARY KEY,

    type         VARCHAR NOT NULL,

    amount       DECIMAL(20, 10),
    percentage   DECIMAL(20, 10),
    value        DECIMAL(20, 10),

    portfolio_id UUID    NOT NULL,
    CONSTRAINT fk_goals_portfolio_id FOREIGN KEY (portfolio_id) REFERENCES portfolios (id),

    asset_id     UUID    NOT NULL,
    CONSTRAINT fk_goals_asset_id FOREIGN KEY (asset_id) REFERENCES assets (id)
);

CREATE INDEX idx_goals_portfolio_id
    ON goals (portfolio_id);