--liquibase formatted sql

--changeset Ilya:001
--comment: Заполнение таблицы users

INSERT INTO users (username)
VALUES ('Илья'),
       ('Александр'),
       ('Володя'),
       ('Настя');