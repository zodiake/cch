CREATE TABLE by_menu_category (
  id   INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(20)
       CHARACTER SET gb2312,
  PRIMARY KEY (id)
);

CREATE TABLE by_menu (
  id          INT NOT NULL AUTO_INCREMENT,
  name        VARCHAR(20)
              CHARACTER SET gb2312 UNIQUE,
  category_id INT,
  href        VARCHAR(50),
  FOREIGN KEY (category_id) REFERENCES by_menu_category (id),
  PRIMARY KEY (id)
);

CREATE TABLE by_license (
  id   BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(20)
       CHARACTER SET gb2312,
  PRIMARY KEY (id)
);

CREATE TABLE by_user (
  id             BIGINT    NOT NULL       AUTO_INCREMENT,
  name           VARCHAR(20),
  password       CHAR(64),
  valid          SMALLINT  NULL           DEFAULT 1,
  user_authority VARCHAR(10),
  created_time   TIMESTAMP NULL,
  updated_time   TIMESTAMP NULL,
  updated_by     VARCHAR(20),
  created_by     VARCHAR(20),
  PRIMARY KEY (id)
);

CREATE TABLE by_shop (
  id           INT       NOT NULL AUTO_INCREMENT,
  name         VARCHAR(225)
               CHARACTER SET gb2312,
  user_id      BIGINT,
  created_by   VARCHAR(20),
  updated_by   VARCHAR(20),
  created_time TIMESTAMP NULL,
  updated_time TIMESTAMP NULL,
  shop_key     VARCHAR(225),
  img_href     VARCHAR(77),
  FOREIGN KEY (user_id) REFERENCES by_user (id),
  PRIMARY KEY (id)
);

CREATE TABLE by_authority (
  id           INT       NOT NULL       AUTO_INCREMENT,
  name         VARCHAR(30)
               CHARACTER SET gb2312,
  created_by   VARCHAR(20),
  updated_by   VARCHAR(20),
  created_time TIMESTAMP NULL,
  updated_time TIMESTAMP NULL,
  valid        SMALLINT  NULL           DEFAULT 1,
  PRIMARY KEY (id)
);

CREATE TABLE by_user_auth (
  user_id BIGINT NOT NULL,
  auth_id INT    NOT NULL,
  PRIMARY KEY (user_id, auth_id),
  FOREIGN KEY (user_id) REFERENCES by_user (id),
  FOREIGN KEY (auth_id) REFERENCES by_authority (id)
);

CREATE TABLE by_auth_menu (
  auth_id INT,
  menu_id INT,
  FOREIGN KEY (auth_id) REFERENCES by_authority (id),
  FOREIGN KEY (menu_id) REFERENCES by_menu (id),
  PRIMARY KEY (auth_id, menu_id)
);

CREATE TABLE by_rule_category (
  id   INT                  NOT NULL AUTO_INCREMENT,
  name VARCHAR(20)
       CHARACTER SET gb2312 NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE by_card (
  id           INT       NOT NULL            AUTO_INCREMENT,
  name         VARCHAR(50) UNIQUE,
  valid        SMALLINT  NULL                DEFAULT 1,
  img_href     VARCHAR(77),
  init_score   INT       NULL                DEFAULT 0,
  created_time TIMESTAMP NULL,
  updated_time TIMESTAMP NULL,
  updated_by   VARCHAR(20),
  created_by   VARCHAR(20),
  summary      VARCHAR(500),
  PRIMARY KEY (id)
);

CREATE TABLE by_card_aud (
  id           BIGINT    NOT NULL            AUTO_INCREMENT,
  name         VARCHAR(50),
  valid        SMALLINT,
  img_href     VARCHAR(77),
  init_score   INT       NULL                DEFAULT 0,
  created_time TIMESTAMP NULL,
  updated_time TIMESTAMP NULL,
  updated_by   VARCHAR(20),
  created_by   VARCHAR(20),
  summary      VARCHAR(500),
  REV          INTEGER   NOT NULL,
  REVTYPE      TINYINT,
  PRIMARY KEY (id, REV)
);

CREATE TABLE by_rule_aud (
  id           BIGINT    NOT NULL            AUTO_INCREMENT,
  rate         DOUBLE,
  score        INT       NULL                DEFAULT 0,
  card_id      INT,
  summary      VARCHAR(50),
  valid        SMALLINT  NULL                DEFAULT 1,
  beginTime    TIMESTAMP NULL,
  endTime      TIMESTAMP NULL,
  category_id  INT,
  name         VARCHAR(20),
  type         CHAR(1),
  created_time TIMESTAMP NULL,
  updated_time TIMESTAMP NULL,
  updated_by   VARCHAR(20),
  created_by   VARCHAR(20),
  FOREIGN KEY (card_id) REFERENCES by_card (id),
  FOREIGN KEY (category_id) REFERENCES by_rule_category (id),
  REV          INTEGER   NOT NULL,
  REVTYPE      TINYINT,
  PRIMARY KEY (id, REV)
);

CREATE TABLE by_rule (
  id           BIGINT    NOT NULL            AUTO_INCREMENT,
  rate         DOUBLE,
  score        INT       NULL                DEFAULT 0,
  card_id      INT,
  summary      VARCHAR(50),
  valid        SMALLINT  NULL                DEFAULT 1,
  beginTime    TIMESTAMP NULL,
  endTime      TIMESTAMP NULL,
  category_id  INT,
  name         VARCHAR(20),
  type         CHAR(1),
  created_time TIMESTAMP NULL,
  updated_time TIMESTAMP NULL,
  updated_by   VARCHAR(20),
  created_by   VARCHAR(20),
  FOREIGN KEY (card_id) REFERENCES by_card (id),
  FOREIGN KEY (category_id) REFERENCES by_rule_category (id),
  PRIMARY KEY (id)
);

CREATE TABLE by_shop_rule (
  shop_id INT,
  rule_id BIGINT,
  PRIMARY KEY (shop_id, rule_id),
  FOREIGN KEY (shop_id) REFERENCES by_shop (id),
  FOREIGN KEY (rule_id) REFERENCES by_rule (id)
);

CREATE TABLE by_member_detail (
  id           BIGINT    NOT NULL AUTO_INCREMENT,
  real_name    VARCHAR(10),
  address      VARCHAR(225),
  password     CHAR(64),
  birthday     TIMESTAMP NULL,
  created_time TIMESTAMP NULL,
  updated_time TIMESTAMP NULL,
  updated_by   VARCHAR(20),
  created_by   VARCHAR(20),
  PRIMARY KEY (id)
);

CREATE TABLE by_member (
  id                   BIGINT    NOT NULL AUTO_INCREMENT,
  name                 CHAR(11) UNIQUE,
  card_id              INT,
  score                INT,
  sumScore             INT,
  created_time         TIMESTAMP NULL,
  valid                SMALLINT  NULL     DEFAULT 1,
  invalid_time         TIMESTAMP NULL,
  invalid_by           VARCHAR(20),
  updated_time         TIMESTAMP NULL,
  created_by           VARCHAR(20),
  updated_by           VARCHAR(20),
  total_parking_coupon INT       NULL     DEFAULT 0,
  detail_id            BIGINT,
  FOREIGN KEY (card_id) REFERENCES by_card (id),
  FOREIGN KEY (detail_id) REFERENCES by_member_detail (id),
  PRIMARY KEY (id)
);


CREATE TABLE by_member_detail_aud (
  id           BIGINT    NOT NULL AUTO_INCREMENT,
  real_name    VARCHAR(10),
  address      VARCHAR(225),
  password     CHAR(64),
  birthday     TIMESTAMP NULL,
  detail_id    BIGINT,
  created_time TIMESTAMP NULL,
  updated_time TIMESTAMP NULL,
  updated_by   VARCHAR(20),
  created_by   VARCHAR(20),
  REV          INTEGER   NOT NULL,
  REVTYPE      TINYINT,
  FOREIGN KEY (detail_id) REFERENCES by_member (id),
  PRIMARY KEY (id, REV)
);

CREATE TABLE by_member_license (
  member_id  BIGINT NOT NULL,
  license_id BIGINT NOT NULL,
  PRIMARY KEY (member_id, license_id),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  FOREIGN KEY (license_id) REFERENCES by_license (id)
);

CREATE TABLE by_coupon (
  id              INT       NOT NULL                      AUTO_INCREMENT,
  type            CHAR(1),
  name            VARCHAR(50),
  begin_time      TIMESTAMP NULL,
  end_time        TIMESTAMP NULL,
  created_time    TIMESTAMP NULL,
  created_by      VARCHAR(20),
  updated_time    TIMESTAMP NULL,
  updated_by      VARCHAR(20),
  score           INT       NULL                          DEFAULT 0,
  coupon_end_time TIMESTAMP NULL,
  valid           SMALLINT  NULL                          DEFAULT 1,
  total           SMALLINT  NULL                          DEFAULT 0,
  duplicate       SMALLINT  NULL                          DEFAULT 1,
  amount          DOUBLE    NULL                          DEFAULT 0,
  cover_img       VARCHAR(77),
  content_img     VARCHAR(77),
  summary         VARCHAR(255),
  shop_id         INT,
  sortOrder       SMALLINT                                DEFAULT 0,
  PRIMARY KEY (id),
  FOREIGN KEY (shop_id) REFERENCES by_shop (id)
);

CREATE TABLE by_gift_coupon_member (
  id             BIGINT AUTO_INCREMENT,
  member_id      BIGINT,
  coupon_id      INT,
  code           CHAR(17),
  state          SMALLINT,
  exchanged_time TIMESTAMP NULL,
  used_time      TIMESTAMP NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  FOREIGN KEY (coupon_id) REFERENCES by_coupon (id)
);

CREATE TABLE by_parking_coupon_exchange_history (
  id           INT AUTO_INCREMENT,
  member_id    BIGINT,
  coupon_id    INT,
  created_time TIMESTAMP NULL,
  created_by   VARCHAR(20),
  total        INT,
  PRIMARY KEY (id),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  FOREIGN KEY (coupon_id) REFERENCES by_coupon (id)
);

CREATE TABLE by_parking_coupon_use_history (
  id                INT AUTO_INCREMENT,
  member_id         BIGINT,
  parking_coupon_id INT,
  created_time      TIMESTAMP NULL,
  total             INT,
  license           VARCHAR(10),
  PRIMARY KEY (id),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  FOREIGN KEY (parking_coupon_id) REFERENCES by_coupon (id)
);

CREATE TABLE by_score_history (
  id           BIGINT    NOT NULL AUTO_INCREMENT,
  member_id    BIGINT,
  created_time TIMESTAMP NULL,
  deposit      INT,
  reason       VARCHAR(20),
  type         SMALLINT,
  PRIMARY KEY (id),
  FOREIGN KEY (member_id) REFERENCES by_member (id)
);

CREATE TABLE REVINFO (
  REV      BIGINT NOT NULL AUTO_INCREMENT,
  REVTSTMP BIGINT,
  PRIMARY KEY (REV)
);

CREATE TABLE by_shop_menu (
  shop_id INT,
  menu_id INT,
  FOREIGN KEY (shop_id) REFERENCES by_shop (id),
  FOREIGN KEY (menu_id) REFERENCES by_menu (id),
  PRIMARY KEY (shop_id, menu_id)
);

CREATE TABLE by_score_add_history (
  id           BIGINT    NOT NULL AUTO_INCREMENT,
  member_id    BIGINT,
  created_time TIMESTAMP NULL,
  total        INT,
  reason       VARCHAR(20),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  PRIMARY KEY (id)
);

CREATE TABLE by_parking_history (
  id         BIGINT    NOT NULL AUTO_INCREMENT,
  license    VARCHAR(20),
  member_id  BIGINT,
  start_time TIMESTAMP NULL,
  end_time   TIMESTAMP NULL,
  amount     FLOAT,
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  PRIMARY KEY (id)
);

CREATE TABLE by_parking_withnomember_history (
  id         BIGINT    NOT NULL AUTO_INCREMENT,
  license    VARCHAR(20),
  start_time TIMESTAMP NULL,
  end_time   TIMESTAMP NULL,
  PRIMARY KEY (id)
);

CREATE TABLE by_pay (
  id             BIGINT    NOT NULL AUTO_INCREMENT,
  member_id      BIGINT,
  created_time   TIMESTAMP NULL,
  type           CHAR(1),
  license        VARCHAR(20),
  amount         INT,
  parkingPayType SMALLINT  NULL     DEFAULT 0,
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  PRIMARY KEY (id)
);

CREATE TABLE by_consumption_history (
  id           BIGINT    NOT NULL AUTO_INCREMENT,
  member_id    BIGINT,
  created_time TIMESTAMP NULL,
  amount       DOUBLE,
  shop         VARCHAR(100),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  PRIMARY KEY (id)
);

CREATE TABLE by_trading (
  id           BIGINT    NOT NULL AUTO_INCREMENT,
  shop_id      INT,
  member_id    BIGINT,
  created_time TIMESTAMP NULL,
  amount       DOUBLE,
  code         CHAR(10),
  FOREIGN KEY (shop_id) REFERENCES by_shop (id),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  PRIMARY KEY (id)
);

CREATE TABLE by_sequence (
  id BIGINT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id)
);

CREATE TABLE by_shop_coupon_member (
  id             BIGINT    NOT NULL AUTO_INCREMENT,
  member_id      BIGINT,
  coupon_id      INT,
  exchanged_time TIMESTAMP NULL,
  used_time      TIMESTAMP NULL,
  code           CHAR(17),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  FOREIGN KEY (coupon_id) REFERENCES by_coupon (id),
  PRIMARY KEY (id)
);

CREATE TABLE by_content (
  id      INT NOT NULL AUTO_INCREMENT,
  summary VARCHAR(1000),
  PRIMARY KEY (id)
);

CREATE TABLE by_member_help (
  id           INT       NOT NULL AUTO_INCREMENT,
  content_id   INT,
  title        VARCHAR(50),
  created_time TIMESTAMP NULL,
  updated_time TIMESTAMP NULL,
  updated_by   VARCHAR(20),
  created_by   VARCHAR(20),
  PRIMARY KEY (id),
  FOREIGN KEY (content_id) REFERENCES by_content (id)
);

CREATE TABLE by_parking_coupon_count (
  id        INT NOT NULL AUTO_INCREMENT,
  total     INT,
  member_id BIGINT,
  PRIMARY KEY (id),
  FOREIGN KEY (member_id) REFERENCES by_member (id)
);

CREATE UNIQUE INDEX by_trading_code_unique ON by_trading (code);
CREATE UNIQUE INDEX by_member_name_unique ON by_member (name);
CREATE UNIQUE INDEX by_shop_shopkey_unique ON by_shop (shop_key);
CREATE UNIQUE INDEX by_gift_coupon_member_code_unique ON by_gift_coupon_member (code);
CREATE UNIQUE INDEX by_user_unique ON by_user (name);
CREATE INDEX by_trading_createdTime_index ON by_trading (created_time);
