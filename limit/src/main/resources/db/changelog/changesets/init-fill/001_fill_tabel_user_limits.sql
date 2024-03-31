--liquibase formatted sql

--changeset Ilya:001
--comment: Заполнение таблицы users

WITH RECURSIVE t AS (
  SELECT 1 as n
  UNION ALL
  SELECT n + 1 FROM t WHERE n < 100
)
INSERT INTO user_limits (daily_limit)
SELECT 10000.00 FROM t;