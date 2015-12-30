-- test user
INSERT INTO by_user (id, name, password, valid)
VALUES (1, 'tom', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 1);
INSERT INTO by_user (id, name, password, valid)
VALUES (2, 'mary', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', 1);

-- test menu_category
INSERT INTO by_menu_category (id, name) VALUES (1, 'icon-two');
INSERT INTO by_menu_category (id, name) VALUES (2, 'icon-three');
INSERT INTO by_menu_category (id, name) VALUES (3, 'icon-four');
INSERT INTO by_menu_category (id, name) VALUES (4, 'icon-five');
INSERT INTO by_menu_category (id, name) VALUES (5, 'icon-six');
INSERT INTO by_menu_category (id, name) VALUES (6, 'icon-seven');

-- test menu
INSERT INTO by_menu (id, name, category_id, href) VALUES (1, '会员卡', 1, '/admin/cards');
INSERT INTO by_menu (id, name, category_id, href) VALUES (2, '会员管理', 1, '/admin/members');
INSERT INTO by_menu (id, name, category_id, href) VALUES (3, '会员黑名单', 1, '/admin/members');
INSERT INTO by_menu (id, name, category_id, href) VALUES (4, '会员卡积分', 2, '/admin/cardRules');
INSERT INTO by_menu (id, name, category_id, href) VALUES (5, '店铺积分', 2, '/admin/shopRules');
INSERT INTO by_menu (id, name, category_id, href) VALUES (6, '人工积分', 2, '');
INSERT INTO by_menu (id, name, category_id, href) VALUES (7, '停车券', 3, '/admin/parkingCoupons');
INSERT INTO by_menu (id, name, category_id, href) VALUES (8, '礼品券', 3, '/admin/giftCoupons');
INSERT INTO by_menu (id, name, category_id, href) VALUES (9, '店铺券', 3, '/admin/shopCoupons');
INSERT INTO by_menu (id, name, category_id, href) VALUES (10, '店铺管理', 4, '/admin/shops');
INSERT INTO by_menu (id, name, category_id, href) VALUES (11, '注册协议', 5, '');
INSERT INTO by_menu (id, name, category_id, href) VALUES (12, '会员卡使用帮助', 5, '');
INSERT INTO by_menu (id, name, category_id, href) VALUES (13, '会员卡积分规则', 5, '');
INSERT INTO by_menu (id, name, category_id, href) VALUES (14, '权限管理', 6, '');
INSERT INTO by_menu (id, name, category_id, href) VALUES (15, '用户管理', 6, '');
INSERT INTO by_menu (id, name, category_id, href) VALUES (16, '账户设置', 6, '');

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
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 2);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 3);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 4);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 5);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 6);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 7);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 8);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 9);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 10);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 11);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 12);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 13);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 14);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 15);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (1, 16);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (2, 1);
INSERT INTO by_auth_menu (auth_id, menu_id) VALUES (2, 2);

-- test license
INSERT INTO by_license (id, name) VALUES (1, '沪A54321');
INSERT INTO by_license (id, name) VALUES (2, '沪A14321');

-- test card
INSERT INTO by_card (id, name, valid, init_score) VALUES (1, 'low', 1, 100);

-- test member
INSERT INTO by_member (id, name, card_id, score, valid, sumScore, created_time)
VALUES (1, '13611738422', 1, 100, 1, 10, '2012-12-12');
INSERT INTO by_member (id, name, card_id, score, valid, sumScore, created_time)
VALUES (2, '13811738422', 1, 200, 0, 10, '2012-12-12');

-- test pay
INSERT INTO by_pay (id, member_id, created_time, type, license, amount, parkingPayType)
VALUES (1, 1, '2015-11-25 13:14:22', 'p', '沪A54321', 15, 0);

-- test member_detail
INSERT INTO by_member_detail (id, member_id) VALUES (1, 1);

-- test category
INSERT INTO by_rule_category (id, name) VALUES (1, 'register rule'); -- register
INSERT INTO by_rule_category (id, name) VALUES (2, 'trading rule'); -- trading

-- test rule
-- 注册用规则
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (1, 2.0, 1, 1, 1, 100, 'rule1', 's', '2012-12-01', '2012-12-30 23:59:59');
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (2, 2.0, 1, 1, 1, 100, 'rule2', 's', '2012-12-01', '2012-12-30 23:59:59');
-- 交易用规则
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (3, 2.0, 1, 2, 1, 100, 'rule3', 'c', '2015-12-01', '2015-12-30 23:59:59');
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (4, 2.0, 1, 2, 1, 100, 'rule4', 'c', '2015-12-01', '2015-12-7');
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (5, 2.0, 1, 2, 1, 100, 'rule5', 'c', '2015-12-10', '2015-12-20');
INSERT INTO by_rule (id, rate, card_id, category_id, valid, score, name, type, beginTime, endTime)
VALUES (6, 2.0, 1, 2, 1, 100, 'rule6', 'c', NULL, NULL);

-- test shop
INSERT INTO by_shop (id, name, shop_key, user_id) VALUES (1, 'shop1', 'abc1', 2);
INSERT INTO by_shop (id, name, shop_key, user_id) VALUES (2, '光明', 'abc2', 2);
INSERT INTO by_shop (id, name, shop_key, user_id) VALUES (3, '哈根达斯', 'abc3', 2);
INSERT INTO by_shop (id, name, shop_key, user_id) VALUES (4, 'h&m', 'abc4', 2);
INSERT INTO by_shop (id, name, shop_key, user_id) VALUES (5, '索尼', 'abc5', 2);
INSERT INTO by_shop (id, name, shop_key, user_id) VALUES (6, '三星', 'abc6', 2);
INSERT INTO by_shop (id, name, shop_key, user_id) VALUES (7, 'java', 'abc7', 2);
INSERT INTO by_shop (id, name, shop_key, user_id) VALUES (8, 'scala', 'abc8', 2);
INSERT INTO by_shop (id, name, shop_key, user_id) VALUES (9, 'csharp', 'abc9', 2);

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
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (1, 'p', '2015-12-1', '2015-12-30', 11, '2016-1-1', 1, 100, 1, 20, 'parkingCoupon1');
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (2, 'p', '2014-12-1', '2014-12-30', 10, '2016-1-3', 1, 100, 0, 20, 'parkingCoupon3');
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (3, 'g', '2015-12-1', '2015-12-30', 10, '2016-1-2', 1, 100, 1, 20, 'pCoupon2');
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (4, 'g', '2016-12-1', '2016-12-30', 10, '2016-1-4', 0, 100, 0, 20, 'pCoupon2');
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name, shop_id)
VALUES (5, 's', '2015-12-1', '2015-12-30', 10, '2016-1-5', 1, 100, 1, 20, 'shopCoupon1', 1);
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name, shop_id)
VALUES (6, 's', '2015-12-1', '2015-12-30', 10, '2016-1-5', 1, 100, 0, 20, 'shopCoupon2', 1);
INSERT INTO by_coupon (id, type, begin_time, end_time, score, coupon_end_time, valid, total, duplicate, amount, name)
VALUES (7, 'p', '2015-12-1', '2015-12-30', 9, '2016-1-3', 1, 100, 0, 20, 'parkingCoupon3');

-- test preferentialCoupon
INSERT INTO by_gift_coupon_member (id, member_id, coupon_id, total) VALUES (1, 1, 3, 20);
INSERT INTO by_gift_coupon_member (id, member_id, coupon_id, total) VALUES (2, 1, 4, 20);

-- test shop coupon
INSERT INTO by_shop_coupon_member (member_id, coupon_id) VALUES (1, 5);

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

-- test by_member_license
INSERT INTO by_member_license (member_id, license_id) VALUES (1, 1);
INSERT INTO by_member_license (member_id, license_id) VALUES (1, 2);

-- test help
INSERT INTO by_content (id, summary) VALUES (1, 'ahahahahah');
INSERT INTO by_member_help (content_id, title, id) VALUES (1, 'h', 1);

