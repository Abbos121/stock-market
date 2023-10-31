create table if not exists users
(
    id            bigserial
        constraint users_pk
            primary key,
    first_name    varchar(100),
    second_name   varchar(100),
    date_of_birth date not null,
    created_at    timestamp default now(),
    updated_at    timestamp
);

create table if not exists security_credentials
(
    id         bigint    default nextval('security_id_seq'::regclass) not null
        constraint security_pk
            primary key,
    user_id    bigint
        constraint security_users_id_fk
            references users
            on update cascade,
    email      varchar(100)                                           not null,
    password   varchar(100),
    created_at timestamp default now(),
    updated_at timestamp,
    roles      varchar
);

create unique index if not exists security_credentials_email_uindex
    on security_credentials (email);

create table if not exists stocks
(
    id         bigserial
        constraint stocks_pk
            primary key,
    symbol     varchar not null,
    name       varchar,
    currency   varchar,
    exchange   varchar,
    mix_code   varchar,
    country    varchar,
    type       varchar,
    created_at timestamp default now(),
    updated_at timestamp,
    price      numeric   default 0
);

create unique index if not exists stocks_symbol_uindex
    on stocks (symbol);

create table if not exists favourite_companies
(
    id             bigserial
        constraint favourite_companies_pk
            primary key,
    user_id        bigint,
    company_symbol varchar,
    created_at     timestamp default now(),
    updated_at     timestamp
);