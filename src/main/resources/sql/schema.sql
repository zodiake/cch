create table by_user(
	id bigint not null AUTO_INCREMENT,
	name varchar(20),
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

create table by_category(
	id bigint not null AUTO_INCREMENT,
	parent_id bigint,
	primary key(id),
	foreign key(parent_id) references by_category(id)
);


create table by_card(
	id bigint not null AUTO_INCREMENT,
	name varchar(50),
	primary key(id)
);

create table by_rule(
	id bigint not null AUTO_INCREMENT,
	rate double,
	card_id bigint,
	summary varchar(50),
	foreign key(card_id) references by_card(id),
	primary key(id)
);

create table by_member(
	id bigint not null AUTO_INCREMENT,
	name char(11),
	password varchar(20),
	card_id bigint,
	score int,
	foreign key(card_id) references by_card(id),
	primary key(id),
);
