CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table if not exists users (
    id uuid not null primary key,
    created timestamp not null default now()
);

create table if not exists credentials_auth (
    login varchar(255) not null primary key,
    passwd text not null,
    user_id uuid not null unique,
    foreign key (user_id) references users(id) on delete cascade on update cascade
)
