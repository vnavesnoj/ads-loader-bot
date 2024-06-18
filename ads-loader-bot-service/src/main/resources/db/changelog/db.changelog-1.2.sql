--liquibase formatted sql

--changeset vnavesnoj:1
INSERT INTO category(platform, name)
VALUES ('OLXUA', 'Допомога'),
       ('OLXUA', 'Дитячий світ'),
       ('OLXUA', 'Нерухомість'),
       ('OLXUA', 'Авто'),
       ('OLXUA', 'Запчастини для транспорту'),
       ('OLXUA', 'Тварини'),
       ('OLXUA', 'Дім і сад'),
       ('OLXUA', 'Електроніка'),
       ('OLXUA', 'Бізнес та послуги'),
       ('OLXUA', 'Оренда та прокат'),
       ('OLXUA', 'Мода і стиль'),
       ('OLXUA', 'Хобі, відпочинок і спорт')