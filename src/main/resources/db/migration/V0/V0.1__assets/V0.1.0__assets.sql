CREATE TABLE assets
(
    id     UUID PRIMARY KEY,

    ticker VARCHAR      NOT NULL,
    name   VARCHAR(255) NOT NULL
);

CREATE UNIQUE INDEX idx_assets_ticker ON assets (ticker);