-- test user
INSERT INTO by_user (id, name, password) VALUES (1, 'tom22222222', '1');
INSERT INTO by_user (id, name, password) VALUES (2, 'mary4444444', '1');

-- test menu
INSERT INTO by_menu (id, name, href) VALUES (1, 'menu1', 'haha');
INSERT INTO by_menu (id, name, href) VALUES (2, 'menu2', 'haha');
INSERT INTO by_menu (id, name, href) VALUES (3, 'menu3', 'haha');

-- test authority
INSERT INTO by_authority (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO by_authority (id, name) VALUES (2, 'ROLE_USER');
INSERT INTO by_authority (id, name) VALUES (3, 'ROLE_SHOP');

-- test user_auth
INSERT INTO by_user_auth (user_id, auth_id) VALUES (1, 1);
--tom role_admin
INSERT INTO by_user_auth (user_id, auth_id) VALUES (1, 2);
--tom role_user

INSERT INTO by_user_auth (user_id, auth_id) VALUES (2, 3);
-- mary role_shop

-- test auth_menu
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 1);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (2, 1);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (2, 2);

-- test license
INSERT INTO by_license (id, name) VALUES (1, '沪A54321');

-- test card
INSERT INTO by_card (id, name, valid, init_score) VALUES (1, 'low', 1, 100);

-- test member
INSERT INTO by_member (id, name, card_id, score) VALUES (1, '13611738422', 1, 110);
INSERT INTO by_member (id, name, card_id, score, valid) VALUES (2, '13811738422', 1, 200, 0);

-- test pay
INSERT INTO by_pay (id, member_id, created_time, type, license, amount, parkingPayType)
VALUES (1, 1, '2015-11-25 13:14:22', 'p', '沪A54321', 15, 0);

-- test member_detail
INSERT INTO by_member_detail (id, member_id) VALUES (1, 1);

-- test category
INSERT INTO by_rule_category (id, name) VALUES (1, 'asdf');
INSERT INTO by_rule_category (id, name) VALUES (2, 'asdf');

-- test rule
-- 注册用规则
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score) VALUES (1, 2.0, 1, 1, 1, 100);
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score) VALUES (2, 2.0, 1, 1, 1, 100);
-- 交易用规则
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score) VALUES (3, 2.0, 1, 2, 1, 100);
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score) VALUES (4, 2.0, 1, 2, 1, 100);

-- test shop
INSERT INTO by_shop (id, name, user_id) VALUES (1, 'shop1', 2);

-- test shop_menu
INSERT INTO by_shop_menu (shop_id, menu_id) VALUES (1, 1);
INSERT INTO by_shop_menu (shop_id, menu_id) VALUES (1, 2);
INSERT INTO by_shop_menu (shop_id, menu_id) VALUES (1, 3);


-- test parking_coupon_use_history

-- test score add
INSERT INTO by_score_add_history (id, member_id, total, summary) VALUES (1, 1, 1, 'h');
INSERT INTO by_score_add_history (id, member_id, total, summary) VALUES (2, 1, 2, 'h');
INSERT INTO by_score_add_history (id, member_id, total, summary) VALUES (3, 1, 3, 'h');
INSERT INTO by_score_add_history (id, member_id, total, summary) VALUES (4, 1, 4, 'h');
INSERT INTO by_score_add_history (id, member_id, total, summary) VALUES (5, 2, 1, 'h');
INSERT INTO by_score_add_history (id, member_id, total, summary) VALUES (6, 2, 2, 'h');
INSERT INTO by_score_add_history (id, member_id, total, summary) VALUES (7, 2, 3, 'h');
INSERT INTO by_score_add_history (id, member_id, total, summary) VALUES (8, 2, 4, 'h');

-- test by_trading
INSERT INTO by_trading (id, member_id, code, amount) VALUES (1, 1, '321', 100);
INSERT INTO by_trading (id, member_id, code, amount) VALUES (2, NULL, '123', 100);

-- test coupon
INSERT INTO by_coupon (id, type, begin_time, end_time, score, couponEndTime, valid, total, duplicate, amount)
VALUES (1, 'p', '2015-12-1', '2015-12-30', 10, '2016-1-1', 1, 100, 1, 20);
INSERT INTO by_coupon (id, type, begin_time, end_time, score, couponEndTime, valid, total, duplicate, amount)
VALUES (2, 'p', '2015-12-1', '2015-12-30', 10, '2016-1-1', 1, 100, 0, 20);

