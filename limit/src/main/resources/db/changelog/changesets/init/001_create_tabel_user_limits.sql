--liquibase formatted sql

--changeset Ilya:001
--comment: Создание таблицы user_limits
CREATE TABLE IF NOT EXISTS user_limits (id bigserial primary key, daily_limit DECIMAL(10,2));