create table payments (
    id uuid primary key not null,
    order_id uuid not null,
    customer_id uuid not null,
    posted boolean,
    code int,
    details text
);
