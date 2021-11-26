create table if not exists shop
(
    id                       bigint        not null auto_increment,
    tenant_id                bigint        not null,
    created_by               bigint        not null,
    created_at               datetime(3)   not null,
    last_modified_by         bigint        null,
    last_modified_at         datetime(3)   null,
    business_no              nvarchar(50)  not null unique,
    name                     nvarchar(50)  not null,
    business_type            nvarchar(20)  not null,
    management_type          nvarchar(20)  not null,
    contact_telephone        nvarchar(20),
    contact_cellphone        nvarchar(20),
    contact_name             nvarchar(50),
    contact_address          nvarchar(200),
    opening_hours_open_time  nvarchar(50)  not null,
    opening_hours_close_time nvarchar(50)  not null,
    business_area            nvarchar(20)  not null,
    comment                  nvarchar(200) not null,
    enabled                  tinyint(1)    not null,
    version                  tinyint(10)   not null,
    primary key (`id`)
);