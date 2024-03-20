SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO menu (print_bill_in_kitchen, spicy, category_id, created_at, id, price, store_id,
                  updated_at, name, description, image, label, status)
VALUES (true, 0, 1, '2023-01-01 12:00:00.000000', 1, 29900, 2, '2023-01-01 12:00:00.000000',
        '수비드 소고기 스테이크', '부채살을 수비드 방식으로 조리하여 촉촉하고 부드러운 식감을 즐길수 있는 스테이크', '', 'REPRESENT', 'BASIC');

INSERT INTO menu_option_group (id, menu_id, created_at, name, updated_at, use_option_price)
VALUES (1, 1, '2023-01-01 12:00:00.000000', '맵기 조절', '2023-01-01 12:00:00.000000', false);

INSERT INTO menu_single_select_option (id, menu_option_group_id, created_at, is_default, name,
                                       price, updated_at)
VALUES (1, 1, '2023-01-01 12:00:00.000000', false, '안맵게', 0, '2023-01-01 12:00:00.000000');
INSERT INTO menu_single_select_option (id, menu_option_group_id, created_at, is_default, name,
                                       price, updated_at)
VALUES (2, 1, '2023-01-01 12:00:00.000000', true, '기본', 0, '2023-01-01 12:00:00.000000');
INSERT INTO menu_single_select_option (id, menu_option_group_id, created_at, is_default, name,
                                       price, updated_at)
VALUES (3, 1, '2023-01-01 12:00:00.000000', false, '맵게', 0, '2023-01-01 12:00:00.000000');

SET FOREIGN_KEY_CHECKS = 1;
