create table if not exists product
(
    id                      bigint         not null primary key auto_increment,
    tenant_id               bigint         not null,
    created_by              bigint         not null,
    created_at              datetime(3)    not null,
    last_modified_by        bigint         null,
    last_modified_at        datetime(3)    null,
    name                    nvarchar(50)   not null,
    unit_price              decimal(10, 2) not null,
    unit_of_measure         nvarchar(20)   null,
    min_sales_quantity      decimal(10, 2) null,
    increase_sales_quantity decimal(10, 2) null,
    description             nvarchar(200)  null,
    enabled                 tinyint(1)     not null,
    version                 tinyint(10)    not null
)
    default charset = utf8mb4
    collate = utf8mb4_unicode_ci;

create table if not exists product_accessory
(
    id              bigint         not null primary key auto_increment,
    product_id      bigint         not null,
    tenant_id       bigint         not null,
    name            nvarchar(50)   not null,
    group_name      nvarchar(50)   not null,
    unit_price      decimal(10, 2) not null,
    unit_of_measure nvarchar(20)   null
)
    default charset = utf8mb4
    collate = utf8mb4_unicode_ci;

create table if not exists product_method
(
    id         bigint       not null primary key auto_increment,
    product_id bigint       not null,
    tenant_id  bigint       not null,
    name       nvarchar(50) not null,
    group_name nvarchar(50) not null
)
    default charset = utf8mb4
    collate = utf8mb4_unicode_ci;