drop table if exists country_audit;

create table country_audit
(
	id varchar(36) not null
		constraint pk_country_audit
			primary key,
    tenant_id varchar(3) not null,
    reference_id varchar(255),

    operation varchar(255),
    created_by varchar(255),
    created_at date,
    modified_by varchar(255),
    modified_at date,
    oldvalue TEXT,
    newvalue TEXT
);

