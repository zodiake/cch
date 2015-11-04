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