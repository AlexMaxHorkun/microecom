create table orders (
    id uuid not null primary key,
    customer_id uuid not null,
    cost float not null check ( cost > 0.0 ),
    status smallint not null
);

create table ordered_products (
    id uuid not null unique,
    order_id uuid not null,
    product_id uuid not null,
    qty smallint not null check ( qty > 0 ),
    primary key (order_id, product_id),
    foreign key (order_id) references orders(id) on delete cascade on update cascade
);
