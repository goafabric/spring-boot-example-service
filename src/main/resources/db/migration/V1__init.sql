drop table if exists country;

create table country
(
	id varchar(36) not null
		constraint pk_country
			primary key,
    tenantid varchar(3) not null,
	isocode varchar(2),
	name varchar(255),
	information varchar(255),
	secret varchar(2048), -- The field has to be much bigger because it has to hold encrypted info
	version bigint default 0
);