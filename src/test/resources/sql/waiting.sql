SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO waiting (id, adult, children, number, created_at, store_id, updated_at,
                     unique_code, phone_number, notification_type, status)
VALUES (1, 2, 0, 1, '2024-01-01 12:00:00.000000', 2, '2024-01-01 12:00:00.000000',
        'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '01012345678', 'REGISTER', 'WAIT');

SET FOREIGN_KEY_CHECKS = 1;
