
    drop table if exists client cascade;

    drop table if exists consumption cascade;

    drop table if exists meters cascade;

    drop table if exists payment cascade;

    drop table if exists payment_category cascade;

    create table client (
        category_id integer not null,
        number integer not null,
        postal_code integer not null,
        id bigserial not null,
        meter_id bigint not null,
        city varchar(255) not null,
        customer_name varchar(255) not null,
        email varchar(255) not null,
        password varchar(255) not null,
        street varchar(255) not null,
        primary key (id)
    );

    create table consumption (
        consumption float(53),
        day integer,
        month integer,
        year integer,
        id bigserial not null,
        meter_id bigint not null,
        primary key (id)
    );

    create table meters (
        client_id bigint not null,
        id bigserial not null,
        primary key (id)
    );

    create table payment (
        completed integer not null,
        month integer not null,
        payment float(53) not null,
        year integer not null,
        client_id bigint not null,
        id bigserial not null,
        primary key (id)
    );

    create table payment_category (
        price float(53),
        id bigserial not null,
        primary key (id)
    );

    drop table if exists client cascade;

    drop table if exists consumption cascade;

    drop table if exists meters cascade;

    drop table if exists payment cascade;

    drop table if exists payment_category cascade;

    create table client (
        category_id integer not null,
        number integer not null,
        postal_code integer not null,
        id bigserial not null,
        meter_id bigint not null,
        city varchar(255) not null,
        customer_name varchar(255) not null,
        email varchar(255) not null,
        password varchar(255) not null,
        street varchar(255) not null,
        primary key (id)
    );

    create table consumption (
        consumption float(53),
        day integer,
        month integer,
        year integer,
        id bigserial not null,
        meter_id bigint not null,
        primary key (id)
    );

    create table meters (
        client_id bigint not null,
        id bigserial not null,
        primary key (id)
    );

    create table payment (
        completed integer not null,
        month integer not null,
        payment float(53) not null,
        year integer not null,
        client_id bigint not null,
        id bigserial not null,
        primary key (id)
    );

    create table payment_category (
        price float(53),
        id bigserial not null,
        primary key (id)
    );

    drop table if exists client cascade;

    drop table if exists consumption cascade;

    drop table if exists meters cascade;

    drop table if exists payment cascade;

    drop table if exists payment_category cascade;

    create table client (
        category_id integer not null,
        number integer not null,
        postal_code integer not null,
        id bigserial not null,
        meter_id bigint not null,
        city varchar(255) not null,
        customer_name varchar(255) not null,
        email varchar(255) not null,
        password varchar(255) not null,
        street varchar(255) not null,
        primary key (id)
    );

    create table consumption (
        consumption float(53),
        day integer,
        month integer,
        year integer,
        id bigserial not null,
        meter_id bigint not null,
        primary key (id)
    );

    create table meters (
        client_id bigint not null,
        id bigserial not null,
        primary key (id)
    );

    create table payment (
        completed integer not null,
        month integer not null,
        payment float(53) not null,
        year integer not null,
        client_id bigint not null,
        id bigserial not null,
        primary key (id)
    );

    create table payment_category (
        price float(53),
        id bigserial not null,
        primary key (id)
    );

    drop table if exists client cascade;

    drop table if exists consumption cascade;

    drop table if exists meters cascade;

    drop table if exists payment cascade;

    drop table if exists payment_category cascade;

    create table client (
        category_id integer not null,
        number integer not null,
        postal_code integer not null,
        id bigserial not null,
        meter_id bigint not null,
        city varchar(255) not null,
        customer_name varchar(255) not null,
        email varchar(255) not null,
        password varchar(255) not null,
        street varchar(255) not null,
        primary key (id)
    );

    create table consumption (
        consumption float(53),
        day integer,
        month integer,
        year integer,
        id bigserial not null,
        meter_id bigint not null,
        primary key (id)
    );

    create table meters (
        client_id bigint not null,
        id bigserial not null,
        primary key (id)
    );

    create table payment (
        completed integer not null,
        month integer not null,
        payment float(53) not null,
        year integer not null,
        client_id bigint not null,
        id bigserial not null,
        primary key (id)
    );

    create table payment_category (
        price float(53),
        id bigserial not null,
        primary key (id)
    );
