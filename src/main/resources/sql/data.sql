-- test user
insert into by_user(id,name,password) values(1,'tom','1');

-- test authority
insert into by_authority(id,name) values(1,'ROLE_ADMIN');
insert into by_authority(id,name) values(2,'ROLE_USER');

-- test user_authority
insert into by_user_auth values(1,1);
insert into by_user_auth values(1,2);

-- test category
insert into by_category(id,parent_id) values(1,null);
insert into by_category(id,parent_id) values(2,1);
insert into by_category(id,parent_id) values(3,1);
insert into by_category(id,parent_id) values(4,1);

-- test card
insert into by_card(id,name) values(1,'low');

-- test member
insert into by_member(id,name,card_id) values(1,'tom',1);
insert into by_member(id,name,card_id) values(2,'mary',1);

-- test rule
insert into by_rule(id,rate,card_id) values(1,2.0,1);

-- test parking_coupon
insert into by_parking_coupon(id,name,amount,score,valid) values(1,'haha',100,100,1);

-- test parking_coupon_member
insert into by_parking_coupon_member(member_id,parking_coupon_id,total) values(2,1,10);