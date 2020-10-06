CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table if not exists users (
    id uuid not null primary key,
    created timestamp not null default now()
);

create table if not exists credentials_auth (
    id uuid not null primary key,
    login varchar(255) not null unique,
    passwd text not null,
    user_id uuid not null unique,
    foreign key (user_id) references users(id) on delete cascade on update cascade
)
