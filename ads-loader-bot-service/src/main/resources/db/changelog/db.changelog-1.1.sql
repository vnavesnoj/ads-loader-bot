--liquibase formatted sql

--changeset vnavesnoj:1
CREATE TABLE filter_builder
(
    id       BIGSERIAL PRIMARY KEY,
    instant  TIMESTAMP     NOT NULL,
    platform VARCHAR(7)    NOT NULL,
    pattern  JSONB,
    user_id  BIGINT UNIQUE NOT NULL REFERENCES users ON DELETE CASCADE
)