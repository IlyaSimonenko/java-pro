--liquibase formatted sql

--changeset Ilya:001
--comment: Создание таблицы users
CREATE TABLE IF NOT EXISTS users (id bigserial primary key, username varchar(255) unique);