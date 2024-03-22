--liquibase formatted sql

--changeset Ilya:005
--comment: Создание таблицы product

CREATE TABLE IF NOT EXISTS product (
                id BIGSERIAL PRIMARY KEY,
                user_id BIGSERIAL NOT NULL,
                account_number VARCHAR(20) NOT NULL,
                balance DECIMAL(10,2) DEFAULT 0,
                product_type VARCHAR(10),
                CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
            )