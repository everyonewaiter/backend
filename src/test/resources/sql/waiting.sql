SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO waiting (id, adult, children, number, created_at, store_id, updated_at,
                     unique_code, phone_number, notification_type, status)
VALUES (1, 2, 0, 1, '2024-01-01 12:00:00.000000', 2, '2024-01-01 12:00:00.000000',
        'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '01011112222', 'REGISTER', 'WAIT');

INSERT INTO waiting (id, adult, children, number, created_at, store_id, updated_at,
                     unique_code, phone_number, notification_type, status)
VALUES (2, 4, 0, 2, '2024-01-01 13:00:00.000000', 2, '2024-01-01 13:00:00.000000',
        'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '01033334444', 'CANCEL', 'CANCEL');

INSERT INTO waiting (id, adult, children, number, created_at, store_id, updated_at,
                     unique_code, phone_number, notification_type, status)
VALUES (3, 2, 2, 3, '2024-01-01 14:00:00.000000', 2, '2024-01-01 14:00:00.000000',
        'cccccccc-cccc-cccc-cccc-cccccccccccc', '01055556666', 'REGISTER', 'WAIT');

INSERT INTO waiting (id, adult, children, number, created_at, store_id, updated_at,
                     unique_code, phone_number, notification_type, status)
VALUES (4, 4, 2, 4, '2024-01-01 15:00:00.000000', 2, '2024-01-01 15:00:00.000000',
        'dddddddd-dddd-dddd-dddd-dddddddddddd', '01077778888', 'REGISTER', 'WAIT');

SET FOREIGN_KEY_CHECKS = 1;
