-- test user
insert into by_user(id,name) values(1,'tom');

-- test authority
insert into by_authority(id,name) values(1,'role_admin');
insert into by_authority(id,name) values(2,'role_user');

-- test user_authority
insert into by_user_auth values(1,1);
insert into by_user_auth values(1,2);

-- test category
insert into by_category(id,parent_id) values(1,null);
insert into by_category(id,parent_id) values(2,1);
insert into by_category(id,parent_id) values(3,1);
insert into by_category(id,parent_id) values(4,1);