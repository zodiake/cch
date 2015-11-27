-- test user
INSERT INTO by_user (id, name, password) VALUES (1, 'tom', '1');
INSERT INTO by_user (id, name, password) VALUES (2, 'mary', '1');

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
INSERT INTO by_card (id, name, valid) VALUES (1, 'low', 1);

-- test member
INSERT INTO by_member (id, name, card_id, score) VALUES (1, 'tom', 1, 110);
INSERT INTO by_member (id, name, card_id, score) VALUES (2, 'mary', 1, 200);

-- test pay
INSERT INTO by_pay (id, member_id, created_time, type, license, amount, parkingPayType)
VALUES (1, 1, '2015-11-25 13:14:22', 'p', '沪A54321', 15, 0);

-- test member_detail
INSERT INTO by_member_detail (id, member_id) VALUES (1, 1);

-- test category
INSERT INTO by_rule_category (id, name) VALUES (1, 'asdf');

-- test rule
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score) VALUES (1, 2.0, 1, 1, 1, 100);
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score) VALUES (2, 2.0, 1, 1, 1, 100);

-- test parking_coupon
INSERT INTO by_parking_coupon (id, name, amount, score, valid) VALUES (1, 'haha', 100, 100, 1);

-- test parking_coupon_member
INSERT INTO by_parking_coupon_member (member_id, parking_coupon_id, total) VALUES (1, 1, 10);
INSERT INTO by_parking_coupon_member (member_id, parking_coupon_id, total) VALUES (2, 1, 10);

-- test shop
INSERT INTO by_shop (id, name, user_id) VALUES (1, 'shop1', 2);

-- test shop_menu
INSERT INTO by_shop_menu (shop_id, menu_id) VALUES (1, 1);
INSERT INTO by_shop_menu (shop_id, menu_id) VALUES (1, 2);
INSERT INTO by_shop_menu (shop_id, menu_id) VALUES (1, 3);


-- test parking_coupon_use_history
INSERT INTO by_parking_coupon_use_history (id, member_id, parking_coupon_id, created_time, total, license)
VALUES (1, 1, 1, '2015-11-25 13:15:22', 1, 'abc');

INSERT INTO by_parking_coupon_use_history (id, member_id, parking_coupon_id, created_time, total, license)
VALUES (2, 1, 1, '2015-11-25 14:15:23', 1, 'abc');

-- test score add
INSERT INTO by_score_add_history (id, member_id, total, summary) VALUES (1, 1, 1, 'h');
INSERT INTO by_score_add_history (id, member_id, total, summary) VALUES (2, 1, 2, 'h');
INSERT INTO by_score_add_history (id, member_id, total, summary) VALUES (3, 1, 3, 'h');
INSERT INTO by_score_add_history (id, member_id, total, summary) VALUES (4, 1, 4, 'h');
INSERT INTO by_score_add_history (id, member_id, total, summary) VALUES (5, 2, 1, 'h');
INSERT INTO by_score_add_history (id, member_id, total, summary) VALUES (6, 2, 2, 'h');
INSERT INTO by_score_add_history (id, member_id, total, summary) VALUES (7, 2, 3, 'h');
INSERT INTO by_score_add_history (id, member_id, total, summary) VALUES (8, 2, 4, 'h');
