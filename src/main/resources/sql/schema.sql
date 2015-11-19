create table by_menu(
	id bigint not null AUTO_INCREMENT,
	name varchar(20),
	href varchar(50),
	primary key(id)
);

create table by_license(
	id bigint not null AUTO_INCREMENT,
	name varchar(20),
	primary key(id)
);

create table by_user(
	id bigint not null AUTO_INCREMENT,
	name varchar(20),
	password varchar(20),
	valid smallint default 1,
	primary key(id)
);

create table by_authority(
	id bigint not null AUTO_INCREMENT,
	name varchar(30),
	primary key(id)
);

create table by_user_auth(
	user_id bigint not null,
	auth_id bigint not null,
	primary key(user_id,auth_id),
	foreign key(user_id) references by_user(id),
	foreign key(auth_id) references by_authority(id)
);

create table by_auth_menu(
	auth_id bigint,
	menu_id bigint,
	foreign key(auth_id) references by_authority(id),
	foreign key(menu_id) references by_menu(id),
	primary key(auth_id,menu_id)
);

create table by_category(
	id bigint not null AUTO_INCREMENT,
	parent_id bigint,
	primary key(id),
	foreign key(parent_id) references by_category(id)
);


create table by_card(
	id bigint not null AUTO_INCREMENT,
	name varchar(50),
	valid smallint,
	initscore int,
	primary key(id)
);

create table by_rule(
	id bigint not null AUTO_INCREMENT,
	rate double,
	card_id bigint,
	summary varchar(50),
	valid smallint,
	beginTime timestamp,
	endTime timestamp,
	permanent smallint,
	foreign key(card_id) references by_card(id),
	primary key(id)
);

create table by_member(
	id bigint not null AUTO_INCREMENT,
	name char(11) unique,
	password varchar(20),
	card_id bigint,
	created_time timestamp,
	foreign key(card_id) references by_card(id),
	primary key(id),
);

create table by_member_license(
	member_id bigint not null,
	license_id bigint not null,
	primary key(member_id,license_id),
	foreign key(member_id) references by_member(id),
	foreign key(license_id) references by_license(id)
);

create table activtity(
	id bigint not null AUTO_INCREMENT,
	primary key(id),
);

create table by_coupon_summary(
	id bigint not null AUTO_INCREMENT,
	name varchar(30),
	total smallint,
	score smallint,
	valid smallint default 1,
	created_Time timestamp,
	created_By varchar(10),
	updated_Time timestamp,
	updated_By varchar(10),
	begin_Time timestamp,
	end_Time timestamp,
	primary key(id)
);

create table by_coupon(
	id bigint not null AUTO_INCREMENT,
	code char(64),
	member_id bigint,
	summary_id bigint,
	exchange_Time timestamp,
	foreign key(summary_id) references by_coupon_summary(id),
	foreign key(member_id) references by_member(id),
	primary key(id)
);

create table by_parking_coupon_member(
	member_id bigint,
	card_id bigint,
	created_time timestamp,
	total int,
	primary key(member_id,card_id),
	foreign key(member_id) references by_member(id),
	foreign key(card_id) references by_card(id)
);

create table by_parking_coupon(
	id bigint not null AUTO_INCREMENT,
	name varchar(50),
	amount smallint,
	valid smallint,
	created_time timestamp,
	created_by varchar(20),
	updated_time timestamp,
	updated_by varchar(20),
	primary key(id)
);

create table by_parking_coupon_member_history(
	id bigint not null AUTO_INCREMENT,
	member_id bigint,
	parking_coupon_id bigint,
	exchange_time timestamp,
	created_by varchar(20),
	total int,
	primary key(id),
	foreign key(member_id) references by_member(id),
	foreign key(parking_coupon_id) references by_parking_coupon(id)
);