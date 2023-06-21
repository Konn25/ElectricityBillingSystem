

    alter table if exists client 
       drop constraint if exists FKbjb8ev0uwm5v9q1a1uwuvavv;

    alter table if exists consumption 
       drop constraint if exists FK4c7r6ikxhqywnsatwma921xff;

    alter table if exists meters 
       drop constraint if exists FKa9fqbki8hurp9aabj8ig4ry02;

    drop table if exists client cascade;

    drop table if exists consumption cascade;

    drop table if exists meters cascade;

    drop table if exists payment_category cascade;

    create table client (
        category_id bigint,
        id bigserial not null,
        meter_id bigint not null,
        address varchar(255) not null,
        customername varchar(255) not null,
        primary key (id)
    );

    create table consumption (
        consumption float(53),
        day integer,
        year integer,
        id bigserial not null,
        meter_id bigint,
        month varchar(255),
        primary key (id)
    );

    create table meters (
        customer_id bigint,
        id bigserial not null,
        primary key (id)
    );

    create table payment_category (
        price integer,
        id bigserial not null,
        primary key (id)
    );

    alter table if exists client 
       add constraint FKbjb8ev0uwm5v9q1a1uwuvavv 
       foreign key (category_id) 
       references payment_category;

    alter table if exists consumption 
       add constraint FK4c7r6ikxhqywnsatwma921xff 
       foreign key (meter_id) 
       references meters;

    alter table if exists meters 
       add constraint FKa9fqbki8hurp9aabj8ig4ry02 
       foreign key (customer_id) 
       references client;
