CREATE TABLE by_menu (
  id   BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(20),
  href VARCHAR(50),
  PRIMARY KEY (id)
);

CREATE TABLE by_license (
  id   BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(20),
  PRIMARY KEY (id)
);

CREATE TABLE by_user (
  id       BIGINT NOT NULL AUTO_INCREMENT,
  name     VARCHAR(20),
  password CHAR(64),
  valid    SMALLINT        DEFAULT 1,
  PRIMARY KEY (id)
);

CREATE TABLE by_shop (
  id           BIGINT NOT NULL AUTO_INCREMENT,
  name         VARCHAR(225),
  user_id      BIGINT,
  created_by   VARCHAR(20),
  updated_by   VARCHAR(20),
  created_time TIMESTAMP,
  updated_time TIMESTAMP,
  key          VARCHAR(225),
  FOREIGN KEY (user_id) REFERENCES by_user (id),
  PRIMARY KEY (id)
);

CREATE TABLE by_authority (
  id           BIGINT NOT NULL AUTO_INCREMENT,
  name         VARCHAR(30),
  created_by   VARCHAR(20),
  updated_by   VARCHAR(20),
  created_time TIMESTAMP,
  updated_time TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE by_user_auth (
  user_id BIGINT NOT NULL,
  auth_id BIGINT NOT NULL,
  PRIMARY KEY (user_id, auth_id),
  FOREIGN KEY (user_id) REFERENCES by_user (id),
  FOREIGN KEY (auth_id) REFERENCES by_authority (id)
);

CREATE TABLE by_auth_menu (
  auth_id BIGINT,
  menu_id BIGINT,
  FOREIGN KEY (auth_id) REFERENCES by_authority (id),
  FOREIGN KEY (menu_id) REFERENCES by_menu (id),
  PRIMARY KEY (auth_id, menu_id)
);

CREATE TABLE by_rule_category (
  id   BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(20),
  PRIMARY KEY (id)
);


CREATE TABLE by_card (
  id           BIGINT NOT NULL AUTO_INCREMENT,
  name         VARCHAR(50),
  valid        SMALLINT,
  init_score   INT             DEFAULT 0,
  created_time TIMESTAMP,
  updated_time TIMESTAMP,
  updated_by   VARCHAR(20),
  created_by   VARCHAR(20),
  PRIMARY KEY (id)
);

CREATE TABLE by_card_aud (
  id           BIGINT  NOT NULL AUTO_INCREMENT,
  name         VARCHAR(50),
  valid        SMALLINT,
  init_score   INT              DEFAULT 0,
  created_time TIMESTAMP,
  updated_time TIMESTAMP,
  updated_by   VARCHAR(20),
  created_by   VARCHAR(20),
  REV          INTEGER NOT NULL,
  REVTYPE      TINYINT,
  PRIMARY KEY (id, REV)
);

CREATE TABLE by_rule (
  id          BIGINT NOT NULL AUTO_INCREMENT,
  rate        DOUBLE,
  score       INT             DEFAULT 0,
  card_id     BIGINT,
  summary     VARCHAR(50),
  valid       SMALLINT,
  beginTime   TIMESTAMP,
  endTime     TIMESTAMP,
  category_id BIGINT,
  name        VARCHAR(20),
  type        CHAR(1),
  FOREIGN KEY (card_id) REFERENCES by_card (id),
  FOREIGN KEY (category_id) REFERENCES by_rule_category (id),
  PRIMARY KEY (id)
);

CREATE TABLE by_shop_rule (
  shop_id BIGINT,
  rule_id BIGINT,
  PRIMARY KEY (shop_id, rule_id),
  FOREIGN KEY (shop_id) REFERENCES by_shop (id),
  FOREIGN KEY (rule_id) REFERENCES by_rule (id)
);

CREATE TABLE by_member (
  id           BIGINT NOT NULL AUTO_INCREMENT,
  name         CHAR(11) UNIQUE,
  card_id      BIGINT,
  score        INT,
  sumScore     INT,
  created_time TIMESTAMP,
  valid        SMALLINT        DEFAULT 1,
  invalid_time TIMESTAMP,
  invalid_by   VARCHAR(20),
  updated_time TIMESTAMP,
  FOREIGN KEY (card_id) REFERENCES by_card (id),
  PRIMARY KEY (id)
);

CREATE TABLE by_member_detail (
  id           BIGINT NOT NULL AUTO_INCREMENT,
  real_name    VARCHAR(10),
  address      VARCHAR(225),
  password     CHAR(64),
  birthday     TIMESTAMP,
  member_id    BIGINT,
  created_time TIMESTAMP,
  updated_time TIMESTAMP,
  updated_by   VARCHAR(20),
  created_by   VARCHAR(20),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  PRIMARY KEY (id)
);

CREATE TABLE by_member_detail_aud (
  id           BIGINT  NOT NULL AUTO_INCREMENT,
  real_name    VARCHAR(10),
  address      VARCHAR(225),
  password     CHAR(64),
  birthday     TIMESTAMP,
  member_id    BIGINT,
  created_time TIMESTAMP,
  updated_time TIMESTAMP,
  updated_by   VARCHAR(20),
  created_by   VARCHAR(20),
  REV          INTEGER NOT NULL,
  REVTYPE      TINYINT,
  FOREIGN KEY (member_id) REFERENCES by_member (id),
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
  id              BIGINT NOT NULL AUTO_INCREMENT,
  type            CHAR(1),
  name            VARCHAR(50),
  begin_time      TIMESTAMP,
  end_time        TIMESTAMP,
  created_time    TIMESTAMP,
  created_by      VARCHAR(20),
  score           INT             DEFAULT 0,
  coupon_end_time TIMESTAMP,
  valid           SMALLINT        DEFAULT 1,
  total           SMALLINT        DEFAULT 0,
  duplicate       SMALLINT        DEFAULT 1,
  amount          DOUBLE          DEFAULT 0,
  cover_img       VARCHAR(50),
  content_img     VARCHAR(50),
  summary         VARCHAR(255),
  shop_id         BIGINT,
  sortOrder       SMALLINT        DEFAULT 0,
  PRIMARY KEY (id),
  FOREIGN KEY (shop_id) REFERENCES by_shop (id)
);

CREATE TABLE by_parking_coupon_member (
  member_id BIGINT,
  coupon_id BIGINT,
  total     INT,
  PRIMARY KEY (member_id, coupon_id),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  FOREIGN KEY (coupon_id) REFERENCES by_coupon (id)
);

CREATE TABLE by_preferential_coupon_member (
  id             BIGINT AUTO_INCREMENT,
  member_id      BIGINT,
  coupon_id      BIGINT,
  total          INT,
  code           CHAR(17),
  state          SMALLINT,
  exchanged_time TIMESTAMP,
  used_time      TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  FOREIGN KEY (coupon_id) REFERENCES by_coupon (id)
);

CREATE TABLE by_parking_coupon_exchange_history (
  member_id    BIGINT,
  coupon_id    BIGINT,
  created_time TIMESTAMP,
  created_by   VARCHAR(20),
  total        INT,
  PRIMARY KEY (member_id, coupon_id),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  FOREIGN KEY (coupon_id) REFERENCES by_coupon (id)
);

CREATE TABLE by_parking_coupon_use_history (
  member_id         BIGINT,
  parking_coupon_id BIGINT,
  created_time      TIMESTAMP,
  total             INT,
  license           VARCHAR(10),
  PRIMARY KEY (member_id, parking_coupon_id),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  FOREIGN KEY (parking_coupon_id) REFERENCES by_coupon (id)
);

CREATE TABLE by_score_history (
  id           BIGINT NOT NULL AUTO_INCREMENT,
  member_id    BIGINT,
  created_time TIMESTAMP,
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
  shop_id BIGINT,
  menu_id BIGINT,
  FOREIGN KEY (shop_id) REFERENCES by_shop (id),
  FOREIGN KEY (menu_id) REFERENCES by_menu (id),
  PRIMARY KEY (shop_id, menu_id)
);

CREATE TABLE by_score_add_history (
  id           BIGINT NOT NULL AUTO_INCREMENT,
  member_id    BIGINT,
  created_time TIMESTAMP,
  total        INT,
  reason       VARCHAR(20),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  PRIMARY KEY (id)
);

CREATE TABLE by_parking_history (
  id         BIGINT NOT NULL AUTO_INCREMENT,
  license    VARCHAR(20),
  member_id  BIGINT,
  start_time TIMESTAMP,
  end_time   TIMESTAMP,
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  PRIMARY KEY (id)
);

CREATE TABLE by_parking_withnomember_history (
  id         BIGINT NOT NULL AUTO_INCREMENT,
  license    VARCHAR(20),
  start_time TIMESTAMP,
  end_time   TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE by_pay (
  id             BIGINT NOT NULL AUTO_INCREMENT,
  member_id      BIGINT,
  created_time   TIMESTAMP,
  type           CHAR(1),
  license        VARCHAR(20),
  amount         INT,
  parkingPayType SMALLINT        DEFAULT 0,
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  PRIMARY KEY (id)
);

CREATE TABLE by_consumption_history (
  id           BIGINT NOT NULL AUTO_INCREMENT,
  member_id    BIGINT,
  created_time TIMESTAMP,
  amount       DOUBLE,
  shop         VARCHAR(100),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  PRIMARY KEY (id)
);

CREATE TABLE by_trading (
  id           BIGINT NOT NULL AUTO_INCREMENT,
  shop_id      BIGINT,
  member_id    BIGINT,
  created_time TIMESTAMP,
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
  id             BIGINT NOT NULL AUTO_INCREMENT,
  member_id      BIGINT,
  coupon_id      BIGINT,
  exchanged_time TIMESTAMP,
  used_time      TIMESTAMP,
  code           CHAR(17),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  FOREIGN KEY (coupon_id) REFERENCES by_coupon (id),
  PRIMARY KEY (id)
);

CREATE UNIQUE INDEX ON by_trading (code);
CREATE UNIQUE INDEX ON by_member (name);
CREATE UNIQUE INDEX ON by_shop (key);
CREATE UNIQUE INDEX ON by_preferential_coupon_member (code);
CREATE INDEX ON by_trading (created_time);
