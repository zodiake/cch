create table by_menu(
	id bigint not null AUTO_INCREMENT,
	name varchar(20),
	href varchar(50),
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
	level smallint default 0,
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
	signupTime timestamp,
	foreign key(card_id) references by_card(id),
	primary key(id),
);

create table activtity(
	id bigint not null AUTO_INCREMENT,
	primary key(id),
);

create table by_coupon(
	id bigint not null AUTO_INCREMENT,
	primary key(id),
);
