create sequence hibernate_sequence start with 1 increment by 1;

create table clients
(
    id      bigserial not null primary key,
    name    varchar(50),
    address_id bigint
);

create table addresses
(
    id      bigserial not null primary key,
    street  varchar(100)
);

create table phones
(
    id      bigserial not null primary key,
    number  varchar(20),
    client_id bigint
);

alter table clients add constraint client_address_key foreign key (address_id) references addresses;
alter table phones add constraint client_phones_key foreign key (client_id) references clients;
