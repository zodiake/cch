-- test user
INSERT INTO by_user (id, name, password) VALUES (1, 'tom', '1');
INSERT INTO by_user (id, name, password) VALUES (2, 'mary', '1');

-- test authority
INSERT INTO by_authority (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO by_authority (id, name) VALUES (2, 'ROLE_USER');
INSERT INTO by_authority (id, name) VALUES (3, 'ROLE_SHOP');

-- test user_auth
INSERT INTO by_user_auth (user_id, auth_id) VALUES (1, 1);
INSERT INTO by_user_auth (user_id, auth_id) VALUES (1, 2);

INSERT INTO by_user_auth (user_id, auth_id) VALUES (2, 3);

-- test menu
INSERT INTO by_menu (id, name, href) VALUES (1, 'menu1', 'haha');
INSERT INTO by_menu (id, name, href) VALUES (2, 'menu2', 'haha');
INSERT INTO by_menu (id, name, href) VALUES (3, 'menu3', 'haha');

-- test auth_menu
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 1);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (2, 1);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (2, 2);

-- test card
INSERT INTO by_card (id, name) VALUES (1, 'low');

-- test member
INSERT INTO by_member (id, name, card_id, score) VALUES (1, 'tom', 1, 100);
INSERT INTO by_member (id, name, card_id, score) VALUES (2, 'mary', 1, 200);

-- test member_detail
INSERT INTO by_member_detail (id, member_id) VALUES (1,1);

-- test rule
INSERT INTO by_rule (id, rate, card_id) VALUES (1, 2.0, 1);

-- test parking_coupon
INSERT INTO by_parking_coupon (id, name, amount, score, valid) VALUES (1, 'haha', 100, 100, 1);

-- test parking_coupon_member
INSERT INTO by_parking_coupon_member (member_id, parking_coupon_id, total) VALUES (2, 1, 10);

-- test shop
INSERT INTO by_shop (id, name, user_id) VALUES (1, 'shop1', 2);

-- test shop_menu
INSERT INTO by_shop_menu (shop_id, menu_id) VALUES (1, 1);
INSERT INTO by_shop_menu (shop_id, menu_id) VALUES (1, 2);
INSERT INTO by_shop_menu (shop_id, menu_id) VALUES (1, 3);
