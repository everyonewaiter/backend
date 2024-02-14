SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO user
(id, username, password, phone_number, role, status, last_logged_in, created_at, updated_at)
VALUES (1, 'handwoong', '$2a$10$JO23xFLoDqsHBSfdzwrmVuDAH67nJKdZbrYb8/lxdzeNO0h/kOd4i',
        '01012345678', 'ROLE_USER', 'ACTIVE', null, '2023-01-01 12:00:00.000000',
        '2023-01-01 12:00:00.000000');

INSERT INTO user
(id, username, password, phone_number, role, status, last_logged_in, created_at, updated_at)
VALUES (2, 'admin', '$2a$10$JO23xFLoDqsHBSfdzwrmVuDAH67nJKdZbrYb8/lxdzeNO0h/kOd4i', '01012345678',
        'ROLE_ADMIN', 'ACTIVE', null, '2023-01-01 12:00:00.000000', '2023-01-01 12:00:00.000000');

SET FOREIGN_KEY_CHECKS = 1;
