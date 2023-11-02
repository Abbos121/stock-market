create sequence users_id_seq;

alter sequence users_id_seq owned by users.id;

create sequence security_id_seq;

alter sequence security_id_seq owned by security_credentials.id;

create sequence stocks_id_seq;

alter sequence stocks_id_seq owned by stocks.id;

create sequence favourite_companies_id_seq;

alter sequence favourite_companies_id_seq owned by favourite_companies.id;