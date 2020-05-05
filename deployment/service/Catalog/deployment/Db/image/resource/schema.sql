create table categories (
    id uuid not null primary key,
    name text not null
);

create table products (
    id uuid not null primary key,
    title text not null,
    sku varchar(255) not null unique,
    description text not null,
    price float not null,
    category_id uuid not null references categories(id) on update cascade on delete restrict,
    available boolean
);
