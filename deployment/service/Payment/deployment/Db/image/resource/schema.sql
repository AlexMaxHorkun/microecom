create table payments (
    id uuid primary key not null,
    order_id uuid not null,
    customer_id uuid not null,
    amount float not null check ( amount > 0.0 ),
    posted boolean,
    code int,
    details text
);
