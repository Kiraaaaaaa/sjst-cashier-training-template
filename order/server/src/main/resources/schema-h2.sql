create table if not exists catering_order
(
    id                      bigint       not null auto_increment,
    tenant_id               bigint       not null,
    created_by              bigint       not null,
    created_at              datetime(3)  not null,
    last_modified_by        bigint       null,
    last_modified_at        datetime(3)  null,
    version                 int(10)      not null,
    status                  nvarchar(20) not null,
    shop_id                 bigint       not null,
    shop_business_no        nvarchar(20) not null,
    shop_name_on_place      nvarchar(50) not null,
    table_no                nvarchar(10),
    customer_count          nvarchar(10),
    total_price             decimal(10, 2),
    comment                 nvarchar(50),
    billing_promotion       decimal(10, 2),
    billing_paid            decimal(10, 2),
    billing_payment_channel nvarchar(50),
    primary key (`id`)
);

create table if not exists catering_order_item
(
    id                                 bigint         not null auto_increment,
    tenant_id                          bigint         not null,
    version                            int(10)        not null,
    order_id                           bigint         not null,
    seq_no                             nvarchar(10)   not null,
    status                             nvarchar(20)   not null,
    place_quantity                     decimal(10, 2) not null,
    produce_quantity                   decimal(10, 2) not null,
    latest_quantity                    decimal(10, 2) not null,
    product_id                         bigint         not null,
    product_name_on_place              nvarchar(20)   not null,
    product_unit_price_on_place        decimal(10, 2) not null,
    product_unit_of_measure_on_place   nvarchar(20)   not null,
    product_method_id                  bigint,
    product_method_name_on_place       nvarchar(20),
    product_method_group_name_on_place nvarchar(20),
    primary key (`id`)
);

create table if not exists catering_order_item_accessory
(
    id                                         bigint         not null auto_increment,
    tenant_id                                  bigint         not null,
    version                                    int(10)        not null,
    order_item_id                              bigint         not null,
    seq_no                                     nvarchar(10)   not null,
    status                                     nvarchar(20)   not null,
    place_quantity                             decimal(10, 2) not null,
    produce_quantity                           decimal(10, 2) not null,
    latest_quantity                            decimal(10, 2) not null,
    product_accessory_id                       bigint         not null,
    product_accessory_name_on_place            nvarchar(20)   not null,
    product_accessory_group_name_on_place      nvarchar(20)   not null,
    product_accessory_unit_price_on_place      decimal(10, 2) not null,
    product_accessory_unit_of_measure_on_place nvarchar(20)   not null,
    primary key (`id`)
);
