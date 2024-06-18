--liquibase formatted sql

--changeset vnavesnoj:1
CREATE TABLE ad
(
    id          BIGSERIAL PRIMARY KEY,
    platform    VARCHAR(7)   NOT NULL,
    url         VARCHAR(255) NOT NULL UNIQUE,
    title       VARCHAR(255) NOT NULL,
    pushup_time TIMESTAMP,
    instant     TIMESTAMP    NOT NULL,
    hash        INT          NOT NULL
);

--changeset vnavesnoj:2
CREATE TABLE ad_body
(
    ad_id BIGINT PRIMARY KEY REFERENCES ad ON DELETE CASCADE,
    body  JSONB NOT NULL
);

--changeset vnavesnoj:3
CREATE TABLE users
(
    id            BIGINT PRIMARY KEY,
    language_code VARCHAR(7),
    instant       TIMESTAMP NOT NULL,
    notify        BOOLEAN   NOT NULL
);

--changeset vnavesnoj:4
CREATE TABLE spot
(
    id          SERIAL PRIMARY KEY,
    platform    VARCHAR(7)   NOT NULL,
    url         VARCHAR(255) NOT NULL,
    name        VARCHAR(255) NOT NULL,
    analyzer    VARCHAR(15),
    category_id INT          NOT NULL,
    UNIQUE (platform, name)
);

--changeset vnavesnoj:5
CREATE TABLE filter
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(15) NOT NULL,
    description VARCHAR(63),
    instant     TIMESTAMP   NOT NULL,
    spot_id     INT         NOT NULL REFERENCES spot,
    enabled     BOOLEAN     NOT NULL,
    pattern     JSONB       NOT NULL,
    user_id     BIGINT      NOT NULL REFERENCES users ON DELETE CASCADE,
    UNIQUE (name, user_id)
);

--changeset vnavesnoj:6
CREATE TABLE filter_ad
(
    id        BIGSERIAL PRIMARY KEY,
    instant   TIMESTAMP    NOT NULL,
    ad_url    VARCHAR(255) NOT NULL REFERENCES ad (url),
    filter_id BIGINT       NOT NULL REFERENCES filter ON DELETE CASCADE,
    UNIQUE (ad_url, filter_id)
);