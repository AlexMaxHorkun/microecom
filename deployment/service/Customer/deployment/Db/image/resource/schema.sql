create table customers (
    id uuid not null primary key,
    user_id text not null unique,
    email text not null,
    first_name text not null,
    last_name text not null,
    since timestamp not null default now(),
    default_billing_id uuid,
    default_shipping_id uuid
);

create table addresses (
    id uuid not null primary key,
    address_line text not null,
    address_line2 text,
    zip_code int not null,
    customer_id uuid references customers(id) on update cascade on delete cascade
);

alter table customers add constraint def_bil_ref foreign key (default_billing_id) references  addresses(id) on update cascade on delete set null;
alter table customers add constraint def_ship_ref foreign key (default_shipping_id) references  addresses(id) on update cascade on delete set null;
