drop table if exists country;

create table country
(
	id varchar(36) not null
		constraint pk_country
			primary key,
	isocode varchar(2),
	name varchar(255),
	information varchar(255),
	secret varchar(255),
	version bigint default 0
);

INSERT INTO country (id, isocode, name, information) values('1', 'de', 'Germany', 'North Europe');
INSERT INTO country (id, isocode, name, information) values('2', 'es', 'Spain', 'West Europe');
INSERT INTO country (id, isocode, name, information) values('3', 'it', 'Italy', 'South Europe');