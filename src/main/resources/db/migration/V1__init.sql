drop table if exists country;

create table country
(
	id varchar(36) not null
		constraint pk_country
			primary key,
	isocode varchar(2),
	name varchar(255),
	information varchar(255),
	secret varchar(2048), -- The field has to be much bigger because it has to hold encrypted info
	version bigint default 0
);

INSERT INTO country (id, isocode, name, information, secret) values('1', 'de', 'Germany', 'North Europe', null);
INSERT INTO country (id, isocode, name, information, secret) values('2', 'es', 'Spain', 'West Europe', null);
INSERT INTO country (id, isocode, name, information, secret) values('3', 'it', 'Italy', 'South Europe', null);