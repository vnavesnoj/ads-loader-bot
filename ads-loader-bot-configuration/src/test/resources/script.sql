INSERT INTO spot(platform, url, name, analyzer)
VALUES ('OLX', 'transport/', 'Транспорт', 'OLX_DEFAULT');

INSERT INTO users(id, language_code, instant, notify)
VALUES (1, 'UA_ua', now(), true);

INSERT INTO filter(name, description, instant, spot_id, enabled, pattern, user_id)
VALUES ('Opel', 'Поиск Опель', now(), 1, true, '{
  "descriptionPatterns": [
    "Opel",
    "Опель"
  ],
  "priceType": "ALL"
}', 1);

INSERT INTO filter(name, description, instant, spot_id, enabled, pattern, user_id)
VALUES ('Авто', 'Поиск Авто', now(), 1, true, '{
  "descriptionPatterns": [
    "Авто"
  ],
  "priceType": "ALL"
}', 1)