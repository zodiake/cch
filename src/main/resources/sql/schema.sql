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
  password VARCHAR(20),
  valid    SMALLINT        DEFAULT 1,
  PRIMARY KEY (id)
);

CREATE TABLE by_shop (
  id      BIGINT NOT NULL AUTO_INCREMENT,
  name    VARCHAR(225),
  user_id BIGINT,
  FOREIGN KEY (user_id) REFERENCES by_user (id),
  PRIMARY KEY (id)
);

CREATE TABLE by_authority (
  id   BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(30),
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

CREATE TABLE by_category (
  id        BIGINT NOT NULL AUTO_INCREMENT,
  parent_id BIGINT,
  PRIMARY KEY (id),
  FOREIGN KEY (parent_id) REFERENCES by_category (id)
);


CREATE TABLE by_card (
  id        BIGINT NOT NULL AUTO_INCREMENT,
  name      VARCHAR(50),
  valid     SMALLINT,
  initscore INT,
  PRIMARY KEY (id)
);

CREATE TABLE by_rule (
  id        BIGINT NOT NULL AUTO_INCREMENT,
  rate      DOUBLE,
  card_id   BIGINT,
  summary   VARCHAR(50),
  valid     SMALLINT,
  beginTime TIMESTAMP,
  endTime   TIMESTAMP,
  permanent SMALLINT,
  FOREIGN KEY (card_id) REFERENCES by_card (id),
  PRIMARY KEY (id)
);

CREATE TABLE by_member (
  id           BIGINT NOT NULL AUTO_INCREMENT,
  name         CHAR(11) UNIQUE,
  password     VARCHAR(20),
  card_id      BIGINT,
  score        INT,
  created_time TIMESTAMP,
  FOREIGN KEY (card_id) REFERENCES by_card (id),
  PRIMARY KEY (id),
);

CREATE TABLE by_member_detail (
  id           BIGINT NOT NULL AUTO_INCREMENT,
  real_name    VARCHAR(10),
  address      VARCHAR(225),
  member_id    BIGINT,
  created_time TIMESTAMP,
  updated_time TIMESTAMP,
  updated_by   VARCHAR(20),
  created_by   VARCHAR(20),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  PRIMARY KEY (id),
);

CREATE TABLE by_member_detail_aud (
  id           BIGINT  NOT NULL AUTO_INCREMENT,
  real_name    VARCHAR(10),
  address      VARCHAR(225),
  member_id    BIGINT,
  created_time TIMESTAMP,
  updated_time TIMESTAMP,
  updated_by   VARCHAR(20),
  created_by   VARCHAR(20),
  REV          INTEGER NOT NULL,
  REVTYPE      TINYINT,
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  PRIMARY KEY (id),
);

CREATE TABLE by_member_license (
  member_id  BIGINT NOT NULL,
  license_id BIGINT NOT NULL,
  PRIMARY KEY (member_id, license_id),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  FOREIGN KEY (license_id) REFERENCES by_license (id)
);

CREATE TABLE by_coupon_summary (
  id           BIGINT NOT NULL AUTO_INCREMENT,
  name         VARCHAR(30),
  total        SMALLINT,
  score        SMALLINT,
  valid        SMALLINT        DEFAULT 1,
  created_Time TIMESTAMP,
  created_By   VARCHAR(10),
  updated_Time TIMESTAMP,
  updated_By   VARCHAR(10),
  begin_Time   TIMESTAMP,
  end_Time     TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE by_coupon (
  id             BIGINT NOT NULL AUTO_INCREMENT,
  code           CHAR(64),
  member_id      BIGINT,
  summary_id     BIGINT,
  exchange_Time  TIMESTAMP,
  exchange_state SMALLINT,
  FOREIGN KEY (summary_id) REFERENCES by_coupon_summary (id),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  PRIMARY KEY (id)
);

CREATE TABLE by_parking_coupon (
  id           BIGINT NOT NULL AUTO_INCREMENT,
  name         VARCHAR(50),
  amount       SMALLINT,
  score        INT,
  valid        SMALLINT,
  created_time TIMESTAMP,
  created_by   VARCHAR(20),
  updated_time TIMESTAMP,
  updated_by   VARCHAR(20),
  PRIMARY KEY (id)
);

CREATE TABLE by_parking_coupon_member (
  member_id         BIGINT,
  parking_coupon_id BIGINT,
  total             INT,
  PRIMARY KEY (member_id, parking_coupon_id),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  FOREIGN KEY (parking_coupon_id) REFERENCES by_parking_coupon (id)
);

CREATE TABLE by_parking_coupon_exchange_history (
  id                BIGINT NOT NULL AUTO_INCREMENT,
  member_id         BIGINT,
  parking_coupon_id BIGINT,
  created_time      TIMESTAMP,
  created_by        VARCHAR(20),
  shop_id           BIGINT,
  total             INT,
  PRIMARY KEY (id),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  FOREIGN KEY (parking_coupon_id) REFERENCES by_parking_coupon (id),
  FOREIGN KEY (shop_id) REFERENCES by_shop (id)
);

CREATE TABLE by_parking_coupon_use_history (
  id                BIGINT NOT NULL AUTO_INCREMENT,
  member_id         BIGINT,
  parking_coupon_id BIGINT,
  created_time      TIMESTAMP,
  total             INT,
  license           VARCHAR(10),
  PRIMARY KEY (id),
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  FOREIGN KEY (parking_coupon_id) REFERENCES by_parking_coupon (id),
);

CREATE TABLE by_score_history (
  id           BIGINT NOT NULL AUTO_INCREMENT,
  member_id    BIGINT,
  created_time TIMESTAMP,
  deposit      INT,
  summary      VARCHAR(225),
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
  summary      VARCHAR(20),
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
  FOREIGN KEY (member_id) REFERENCES by_member (id),
  type           CHAR(1),
  license        VARCHAR(20),
  amount         INT,
  parkingPayType SMALLINT        DEFAULT 0,
  PRIMARY KEY (id)
);

