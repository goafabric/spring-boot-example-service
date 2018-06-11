drop table if exisits country;

create table country
(
	id varchar(36) not null
		constraint pk_country
			primary key,
	isocode varchar(2)
	name varchar(100),

	version bigint default 0
);

INSERT INTO country(1, "de", "Germany")
INSERT INTO country(2, "es", "Spain")
INSERT INTO country(3, "it", "Italy")