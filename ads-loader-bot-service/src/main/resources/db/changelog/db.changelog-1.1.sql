--liquibase formatted sql

--changeset vnavesnoj:1
CREATE TABLE filter_builder
(
    id            BIGSERIAL PRIMARY KEY,
    instant       TIMESTAMP     NOT NULL,
    pattern       JSONB,
    current_input VARCHAR(31),
    spot_id       INT           NOT NULL REFERENCES spot,
    user_id       BIGINT UNIQUE NOT NULL REFERENCES users ON DELETE CASCADE
);

--changeset vnavesnoj:2
CREATE TABLE category
(
    id       SERIAL PRIMARY KEY,
    platform VARCHAR(7)   NOT NULL,
    name     VARCHAR(255) NOT NULL,
    UNIQUE (platform, name)
);