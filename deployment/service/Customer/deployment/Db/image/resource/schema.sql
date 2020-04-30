create table customers (
    id uuid not null primary key,
    user_id text not null unique,
    email text not null,
    first_name text not null,
    last_name text not null,
    since timestamp not null default now()
);
