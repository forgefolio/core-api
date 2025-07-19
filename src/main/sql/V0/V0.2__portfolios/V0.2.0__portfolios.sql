CREATE TABLE portfolios
(
    id      UUID PRIMARY KEY,

    name    VARCHAR(255) NOT NULL,

    user_id UUID         NOT NULL,
    CONSTRAINT fk_portfolios_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE INDEX idx_portfolios_user_id ON portfolios (user_id);