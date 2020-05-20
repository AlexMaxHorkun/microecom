create table stocks (
    product_id uuid not null primary key,
    available int not null check ( available >= 0 )
);

create table reservations (
    id uuid not null primary key,
    order_id uuid not null,
    product_id uuid not null,
    number int not null check ( number > 0 ),
    fulfilled boolean not null,
    created timestamp not null,
    unique (product_id, order_id)
);
