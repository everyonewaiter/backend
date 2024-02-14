SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO store_option (id, use_break_time, use_waiting, use_order)
VALUES (1, true, true, true);

INSERT INTO store
(id, user_id, name, landline_number, status, store_option_id, last_opened_at, last_closed_at,
 created_at, updated_at)
VALUES (1, 1, '나루', '0551234567', 'CLOSE', 1, null, null, '2023-01-01 12:00:00.000000',
        '2023-01-01 12:00:00.000000');

INSERT INTO store_break_time (id, store_id, start, end, days_of_week)
VALUES (1, 1, '15:00:00.000000', '16:30:00.000000', '화,수,목,금');
INSERT INTO store_break_time (id, store_id, start, end, days_of_week)
VALUES (2, 1, '15:30:00.000000', '17:00:00.000000', '토,일');

INSERT INTO store_business_time (id, store_id, open, close, days_of_week)
VALUES (1, 1, '11:00:00.000000', '21:00:00.000000', '화,수,목,금,토,일');

INSERT INTO store_option (id, use_break_time, use_waiting, use_order)
VALUES (2, true, true, true);

INSERT INTO store
(id, user_id, name, landline_number, status, store_option_id, last_opened_at, last_closed_at,
 created_at, updated_at)
VALUES (2, 1, '나루 24시', '0551234567', 'OPEN', 2, '2023-01-01 12:00:00.000000', null,
        '2023-01-01 12:00:00.000000',
        '2023-01-01 12:00:00.000000');

INSERT INTO store_business_time (id, store_id, open, close, days_of_week)
VALUES (2, 2, '00:00:00.000000', '23:59:59.999999', '월,화,수,목,금,토,일');

SET FOREIGN_KEY_CHECKS = 1;
