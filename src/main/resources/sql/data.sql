-- test user
INSERT INTO by_user (id, name, password, valid)
VALUES (1, 'tom', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 1);
INSERT INTO by_user (id, name, password, valid)
VALUES (2, 'mary', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 1);

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
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score,name) VALUES (1, 2.0, 1, 1, 1, 100,'rule1');
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score,name) VALUES (2, 2.0, 1, 1, 1, 100,'rule1');
-- 交易用规则
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score,name) VALUES (3, 2.0, 1, 2, 1, 100,'rule1');
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score,name) VALUES (4, 2.0, 1, 2, 1, 100,'rule1');

-- test shop
INSERT INTO by_shop (id, name, user_id) VALUES (1, 'shop1', 2);

-- test shop_menu
INSERT INTO by_shop_menu (shop_id, menu_id) VALUES (1, 1);

-- test parking_coupon_use_history

-- test score add
INSERT INTO by_score_add_history (id, member_id, total, reason) VALUES (1, 1, 1, 'h');
INSERT INTO by_score_add_history (id, member_id, total, reason) VALUES (2, 1, 2, 'h');
INSERT INTO by_score_add_history (id, member_id, total, reason) VALUES (3, 1, 3, 'h');
INSERT INTO by_score_add_history (id, member_id, total, reason) VALUES (4, 1, 4, 'h');
INSERT INTO by_score_add_history (id, member_id, total, reason) VALUES (5, 2, 1, 'h');
INSERT INTO by_score_add_history (id, member_id, total, reason) VALUES (6, 2, 2, 'h');
INSERT INTO by_score_add_history (id, member_id, total, reason) VALUES (7, 2, 3, 'h');
INSERT INTO by_score_add_history (id, member_id, total, reason) VALUES (8, 2, 4, 'h');

-- test by_trading
INSERT INTO by_trading (id, member_id, code, amount) VALUES (1, 1, '321', 100);
INSERT INTO by_trading (id, member_id, code, amount) VALUES (2, NULL, '123', 100);

-- test coupon
INSERT INTO by_coupon (id, type, begin_time, end_time, score, couponEndTime, valid, total, duplicate, amount, name)
VALUES (1, 'p', '2015-12-1', '2015-12-30', 10, '2016-1-1', 1, 100, 1, 20, 'coupon1');
INSERT INTO by_coupon (id, type, begin_time, end_time, score, couponEndTime, valid, total, duplicate, amount, name)
VALUES (2, 'c', '2015-12-1', '2015-12-30', 10, '2016-1-1', 1, 100, 0, 20, 'coupon2');
INSERT INTO by_coupon (id, type, begin_time, end_time, score, couponEndTime, valid, total, duplicate, amount, name)
VALUES (3, 'p', '2014-12-1', '2014-12-30', 10, '2016-1-1', 1, 100, 0, 20, 'coupon3');
INSERT INTO by_coupon (id, type, begin_time, end_time, score, couponEndTime, valid, total, duplicate, amount, name)
VALUES (4, 'c', '2015-12-1', '2015-12-30', 10, '2016-1-1', 0, 100, 0, 20, 'coupon2');
INSERT INTO by_coupon (id, type, begin_time, end_time, score, couponEndTime, valid, total, duplicate, amount, name, shop_id)
VALUES (5, 's', '2015-12-1', '2015-12-30', 10, '2016-1-1', 1, 100, 0, 20, 'shopCoupon1', 1);

-- test parkingCoupon
INSERT INTO by_parking_coupon_member (member_id, coupon_id, total) VALUES (1, 1, 10);
INSERT INTO by_parking_coupon_member (member_id, coupon_id, total) VALUES (1, 3, 10);

-- test preferentialCoupon
INSERT INTO by_preferential_coupon_member (id, member_id, coupon_id, total) VALUES (1, 1, 2, 20);
INSERT INTO by_preferential_coupon_member (id, member_id, coupon_id, total) VALUES (2, 1, 4, 20);

-- test score_history
INSERT INTO by_score_history (id, member_id, created_time, deposit, reason)
VALUES (1, 1, '2012-12-1 12:22:55', 10, 'hehe');
INSERT INTO by_score_history (id, member_id, created_time, deposit, reason)
VALUES (2, 1, '2012-12-2 12:22:55', 10, 'hehe');
INSERT INTO by_score_history (id, member_id, created_time, deposit, reason)
VALUES (3, 1, '2012-12-4 12:22:55', 10, 'hehe');
INSERT INTO by_score_history (id, member_id, created_time, deposit, reason)
VALUES (4, 1, '2012-12-5 12:22:55', 10, 'hehe');
INSERT INTO by_score_history (id, member_id, created_time, deposit, reason)
VALUES (5, 1, '2012-12-6 12:22:55', 10, 'hehe');

INSERT INTO by_sequence (id) VALUES (1001);
