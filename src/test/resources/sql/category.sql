SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO category (created_at, id, store_id, updated_at, icon, name)
VALUES ('2023-01-01 12:00:00.000000', 1, 2, '2023-01-01 12:00:00.000000', 'drumstick', '스테이크');

INSERT INTO category (created_at, id, store_id, updated_at, icon, name)
VALUES ('2023-01-01 12:00:00.000000', 2, 2, '2023-01-01 12:00:00.000000', 'utensils', '파스타');

INSERT INTO category (created_at, id, store_id, updated_at, icon, name)
VALUES ('2023-01-01 12:00:00.000000', 3, 2, '2023-01-01 12:00:00.000000', 'pizza', '피자');

SET FOREIGN_KEY_CHECKS = 1;
