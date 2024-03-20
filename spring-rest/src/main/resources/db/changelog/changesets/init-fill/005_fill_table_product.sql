--liquibase formatted sql

--changeset Ilya:005
--comment: Создание таблицы product

INSERT INTO product (user_id, account_number, balance, product_type) VALUES
    ((SELECT id FROM users WHERE username='Илья'), '1234567890', 100.00, 'CARD'),
    ((SELECT id FROM users WHERE username='Настя'), '9876543210', 0.00, 'CARD');