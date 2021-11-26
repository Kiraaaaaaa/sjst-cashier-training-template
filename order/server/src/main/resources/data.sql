-- Row 1
insert into catering_order ( id
                           , tenant_id
                           , created_by
                           , created_at
                           , last_modified_by
                           , last_modified_at
                           , status
                           , shop_id
                           , shop_business_no
                           , shop_name_on_place
                           , table_no
                           , customer_count
                           , total_price
                           , comment
                           , version)
values ( 10000
       , 500
       , 11000
       , '2021-08-01 11:32'
       , 11000
       , '2021-08-01 12:12'
       , 'PLACED'
       , 100
       , '1234567890'
       , '海棠川菜馆'
       , 'A08'
       , 2
       , 204.4
       , '主营经典川菜系列'
       , 2)
on duplicate key update tenant_id = 500;
insert into catering_order_item ( id
                                , tenant_id
                                , order_id
                                , seq_no
                                , status
                                , place_quantity
                                , produce_quantity
                                , latest_quantity
                                , product_id
                                , product_name_on_place
                                , product_unit_price_on_place
                                , product_unit_of_measure_on_place
                                , product_method_id
                                , product_method_name_on_place
                                , product_method_group_name_on_place
                                , version)
values ( 100001
       , 500
       , 10000
       , '1'
       , 'PLACED'
       , 1
       , 0
       , 1
       , 100
       , '宫保鸡丁'
       , 26.8
       , '元/份'
       , 10021
       , '微辣'
       , '辣度'
       , 1)
on duplicate key update tenant_id = 500;
insert into catering_order_item_accessory ( id
                                          , tenant_id
                                          , version
                                          , order_item_id
                                          , seq_no
                                          , status
                                          , place_quantity
                                          , produce_quantity
                                          , latest_quantity
                                          , product_accessory_id
                                          , product_accessory_name_on_place
                                          , product_accessory_group_name_on_place
                                          , product_accessory_unit_price_on_place
                                          , product_accessory_unit_of_measure_on_place)
values ( 10000101
       , 500
       , 1
       , 100001
       , '1-1'
       , 'PLACED'
       , 1
       , 0
       , 1
       , 10013
       , '米饭'
       , '配菜'
       , 2
       , '碗')
on duplicate key update tenant_id = 500;
insert into catering_order_item_accessory ( id
                                          , tenant_id
                                          , version
                                          , order_item_id
                                          , seq_no
                                          , status
                                          , place_quantity
                                          , produce_quantity
                                          , latest_quantity
                                          , product_accessory_id
                                          , product_accessory_name_on_place
                                          , product_accessory_group_name_on_place
                                          , product_accessory_unit_price_on_place
                                          , product_accessory_unit_of_measure_on_place)
values ( 10000102
       , 500
       , 1
       , 100001
       , '1-2'
       , 'PLACED'
       , 1
       , 0
       , 1
       , 10011
       , '泡萝卜'
       , '配菜'
       , 1
       , '份')
on duplicate key update tenant_id = 500;
insert into catering_order_item ( id
                                , tenant_id
                                , order_id
                                , seq_no
                                , status
                                , place_quantity
                                , produce_quantity
                                , latest_quantity
                                , product_id
                                , product_name_on_place
                                , product_unit_price_on_place
                                , product_unit_of_measure_on_place
                                , version)
values ( 100002
       , 500
       , 10000
       , '2'
       , 'PLACED'
       , 1
       , 0
       , 1
       , 101
       , '红烧肉'
       , 48.8
       , '元/份'
       , 1)
on duplicate key update tenant_id = 500;
insert into catering_order_item ( id
                                , tenant_id
                                , order_id
                                , seq_no
                                , status
                                , place_quantity
                                , produce_quantity
                                , latest_quantity
                                , product_id
                                , product_name_on_place
                                , product_unit_price_on_place
                                , product_unit_of_measure_on_place
                                , version)
values ( 100003
       , 500
       , 10000
       , '3'
       , 'PLACED'
       , 1
       , 0
       , 1
       , 102
       , '松鼠鱼'
       , 128.8
       , '元/份'
       , 1)
on duplicate key update tenant_id = 500;

-- Row 2
insert into catering_order ( id
                           , tenant_id
                           , created_by
                           , created_at
                           , last_modified_by
                           , last_modified_at
                           , status
                           , shop_id
                           , shop_business_no
                           , shop_name_on_place
                           , table_no
                           , customer_count
                           , total_price
                           , comment
                           , version)
values ( 10001
       , 500
       , 11000
       , '2021-09-13 21:30'
       , 11000
       , '2021-09-13 21:48'
       , 'PLACED'
       , 100
       , '1234567890'
       , '海棠川菜馆'
       , 'B23'
       , 4
       , 204.4
       , '主营经典川菜系列'
       , 2)
on duplicate key update tenant_id = 500;
insert into catering_order_item ( id
                                , tenant_id
                                , order_id
                                , seq_no
                                , status
                                , place_quantity
                                , produce_quantity
                                , latest_quantity
                                , product_id
                                , product_name_on_place
                                , product_unit_price_on_place
                                , product_unit_of_measure_on_place
                                , version)
values ( 100011
       , 500
       , 10001
       , '1'
       , 'PLACED'
       , 3
       , 0
       , 3
       , 104
       , '啤酒鸭'
       , 26.8
       , '元/份'
       , 1)
on duplicate key update tenant_id = 500;
insert into catering_order_item ( id
                                , tenant_id
                                , order_id
                                , seq_no
                                , status
                                , place_quantity
                                , produce_quantity
                                , latest_quantity
                                , product_id
                                , product_name_on_place
                                , product_unit_price_on_place
                                , product_unit_of_measure_on_place
                                , version)
values ( 100012
       , 500
       , 10001
       , '2'
       , 'PLACED'
       , 1
       , 0
       , 1
       , 105
       , '鱼香肉丝'
       , 36.6
       , '元/份'
       , 1)
on duplicate key update tenant_id = 500;

-- Row 3
insert into catering_order ( id
                           , tenant_id
                           , created_by
                           , created_at
                           , last_modified_by
                           , last_modified_at
                           , status
                           , shop_id
                           , shop_business_no
                           , shop_name_on_place
                           , table_no
                           , customer_count
                           , total_price
                           , comment
                           , version)
values ( 10002
       , 500
       , 11000
       , '2021-09-17 13:42'
       , 11000
       , '2021-09-18 14:08'
       , 'PREPARING'
       , 101
       , '1234567891'
       , '聚源美食'
       , 'C01'
       , 9
       , 782
       , '使用包间C01'
       , 3)
on duplicate key update tenant_id = 500;
insert into catering_order_item ( id
                                , tenant_id
                                , order_id
                                , seq_no
                                , status
                                , place_quantity
                                , produce_quantity
                                , latest_quantity
                                , product_id
                                , product_name_on_place
                                , product_unit_price_on_place
                                , product_unit_of_measure_on_place
                                , version)
values ( 100021
       , 500
       , 10002
       , '1'
       , 'PREPARING'
       , 1
       , 0
       , 1
       , 108
       , '番茄牛肉'
       , 86.6
       , '元/份'
       , 2)
on duplicate key update tenant_id = 500;
insert into catering_order_item ( id
                                , tenant_id
                                , order_id
                                , seq_no
                                , status
                                , place_quantity
                                , produce_quantity
                                , latest_quantity
                                , product_id
                                , product_name_on_place
                                , product_unit_price_on_place
                                , product_unit_of_measure_on_place
                                , version)
values ( 100022
       , 500
       , 10002
       , '2'
       , 'PREPARING'
       , 1
       , 0
       , 1
       , 102
       , '松鼠鱼'
       , 128.8
       , '元/份'
       , 2)
on duplicate key update tenant_id = 500;
insert into catering_order_item ( id
                                , tenant_id
                                , order_id
                                , seq_no
                                , status
                                , place_quantity
                                , produce_quantity
                                , latest_quantity
                                , product_id
                                , product_name_on_place
                                , product_unit_price_on_place
                                , product_unit_of_measure_on_place
                                , version)
values ( 100023
       , 500
       , 10002
       , '3'
       , 'PREPARING'
       , 3
       , 0
       , 3
       , 106
       , '酸菜鱼'
       , 88.8
       , '元/份'
       , 2)
on duplicate key update tenant_id = 500;
insert into catering_order_item ( id
                                , tenant_id
                                , order_id
                                , seq_no
                                , status
                                , place_quantity
                                , produce_quantity
                                , latest_quantity
                                , product_id
                                , product_name_on_place
                                , product_unit_price_on_place
                                , product_unit_of_measure_on_place
                                , version)
values ( 100024
       , 500
       , 10002
       , '4'
       , 'PREPARING'
       , 5
       , 1
       , 5
       , 106
       , '肉末茄子'
       , 28.8
       , '元/份'
       , 4)
on duplicate key update tenant_id = 500;
insert into catering_order_item ( id
                                , tenant_id
                                , order_id
                                , seq_no
                                , status
                                , place_quantity
                                , produce_quantity
                                , latest_quantity
                                , product_id
                                , product_name_on_place
                                , product_unit_price_on_place
                                , product_unit_of_measure_on_place
                                , version)
values ( 100025
       , 500
       , 10002
       , '5'
       , 'PREPARED'
       , 3
       , 3
       , 3
       , 109
       , '京酱肉丝'
       , 38.8
       , '元/份'
       , 5)
on duplicate key update tenant_id = 500;
insert into catering_order_item ( id
                                , tenant_id
                                , order_id
                                , seq_no
                                , status
                                , place_quantity
                                , produce_quantity
                                , latest_quantity
                                , product_id
                                , product_name_on_place
                                , product_unit_price_on_place
                                , product_unit_of_measure_on_place
                                , product_method_id
                                , product_method_name_on_place
                                , product_method_group_name_on_place
                                , version)
values ( 100026
       , 500
       , 10002
       , '6'
       , 'PREPARING'
       , 4
       , 1
       , 4
       , 100
       , '宫保鸡丁'
       , 26.8
       , '元/份'
       , 10023
       , '重辣'
       , '辣度'
       , 3)
on duplicate key update tenant_id = 500;
insert into catering_order_item_accessory ( id
                                          , tenant_id
                                          , version
                                          , order_item_id
                                          , seq_no
                                          , status
                                          , place_quantity
                                          , produce_quantity
                                          , latest_quantity
                                          , product_accessory_id
                                          , product_accessory_name_on_place
                                          , product_accessory_group_name_on_place
                                          , product_accessory_unit_price_on_place
                                          , product_accessory_unit_of_measure_on_place)
values ( 10002601
       , 500
       , 3
       , 100026
       , '6-1'
       , 'PREPARING'
       , 4
       , 1
       , 4
       , 10013
       , '米饭'
       , '配菜'
       , 2
       , '碗')
on duplicate key update tenant_id = 500;
insert into catering_order_item_accessory ( id
                                          , tenant_id
                                          , version
                                          , order_item_id
                                          , seq_no
                                          , status
                                          , place_quantity
                                          , produce_quantity
                                          , latest_quantity
                                          , product_accessory_id
                                          , product_accessory_name_on_place
                                          , product_accessory_group_name_on_place
                                          , product_accessory_unit_price_on_place
                                          , product_accessory_unit_of_measure_on_place)
values ( 10002602
       , 500
       , 3
       , 100026
       , '6-2'
       , 'PREPARING'
       , 8
       , 2
       , 8
       , 10011
       , '泡萝卜'
       , '配菜'
       , 1
       , '份')
on duplicate key update tenant_id = 500;

-- Row 4
insert into catering_order ( id
                           , tenant_id
                           , created_by
                           , created_at
                           , last_modified_by
                           , last_modified_at
                           , status
                           , shop_id
                           , shop_business_no
                           , shop_name_on_place
                           , table_no
                           , customer_count
                           , total_price
                           , comment
                           , version)
values ( 10003
       , 500
       , 11000
       , '2021-10-06 11:23'
       , 11000
       , '2021-10-06 12:33'
       , 'PREPARED'
       , 102
       , '1234567892'
       , '皮特西餐馆'
       , 'A03'
       , 9
       , 165
       , '单人餐'
       , 5)
on duplicate key update tenant_id = 500;
insert into catering_order_item ( id
                                , tenant_id
                                , order_id
                                , seq_no
                                , status
                                , place_quantity
                                , produce_quantity
                                , latest_quantity
                                , product_id
                                , product_name_on_place
                                , product_unit_price_on_place
                                , product_unit_of_measure_on_place
                                , version)
values ( 100031
       , 500
       , 10003
       , '1'
       , 'PREPARED'
       , 1
       , 1
       , 1
       , 110
       , '榴莲披萨'
       , 78.8
       , '元/份'
       , 7)
on duplicate key update tenant_id = 500;
insert into catering_order_item ( id
                                , tenant_id
                                , order_id
                                , seq_no
                                , status
                                , place_quantity
                                , produce_quantity
                                , latest_quantity
                                , product_id
                                , product_name_on_place
                                , product_unit_price_on_place
                                , product_unit_of_measure_on_place
                                , version)
values ( 100032
       , 500
       , 10003
       , '2'
       , 'PREPARED'
       , 1
       , 1
       , 1
       , 110
       , '宫保鸡丁'
       , 26.8
       , '元/份'
       , 5)
on duplicate key update tenant_id = 500;
insert into catering_order_item_accessory ( id
                                          , tenant_id
                                          , version
                                          , order_item_id
                                          , seq_no
                                          , status
                                          , place_quantity
                                          , produce_quantity
                                          , latest_quantity
                                          , product_accessory_id
                                          , product_accessory_name_on_place
                                          , product_accessory_group_name_on_place
                                          , product_accessory_unit_price_on_place
                                          , product_accessory_unit_of_measure_on_place)
values ( 10003201
       , 500
       , 3
       , 100032
       , '2-1'
       , 'PREPARED'
       , 1
       , 1
       , 1
       , 10011
       , '泡萝卜'
       , '配菜'
       , 1
       , '份')
on duplicate key update tenant_id = 500;

-- Row 5
insert into catering_order ( id
                           , tenant_id
                           , created_by
                           , created_at
                           , last_modified_by
                           , last_modified_at
                           , status
                           , shop_id
                           , shop_business_no
                           , shop_name_on_place
                           , table_no
                           , customer_count
                           , total_price
                           , comment
                           , version)
values ( 10004
       , 500
       , 11000
       , '2021-09-13 21:30'
       , 11000
       , '2021-09-13 21:48'
       , 'PLACED'
       , 100
       , '1234567890'
       , '海棠川菜馆'
       , 'B23'
       , 4
       , 204.4
       , '主营经典川菜系列'
       , 2)
on duplicate key update tenant_id = 500;
insert into catering_order_item ( id
                                , tenant_id
                                , order_id
                                , seq_no
                                , status
                                , place_quantity
                                , produce_quantity
                                , latest_quantity
                                , product_id
                                , product_name_on_place
                                , product_unit_price_on_place
                                , product_unit_of_measure_on_place
                                , version)
values ( 100041
       , 500
       , 10004
       , '1'
       , 'PLACED'
       , 3
       , 0
       , 3
       , 104
       , '啤酒鸭'
       , 26.8
       , '元/份'
       , 1)
on duplicate key update tenant_id = 500;
insert into catering_order_item ( id
                                , tenant_id
                                , order_id
                                , seq_no
                                , status
                                , place_quantity
                                , produce_quantity
                                , latest_quantity
                                , product_id
                                , product_name_on_place
                                , product_unit_price_on_place
                                , product_unit_of_measure_on_place
                                , version)
values ( 100042
       , 500
       , 10004
       , '2'
       , 'PLACED'
       , 1
       , 0
       , 1
       , 105
       , '鱼香肉丝'
       , 36.6
       , '元/份'
       , 1)
on duplicate key update tenant_id = 500;

-- Row 6
insert into catering_order ( id
                           , tenant_id
                           , created_by
                           , created_at
                           , last_modified_by
                           , last_modified_at
                           , status
                           , shop_id
                           , shop_business_no
                           , shop_name_on_place
                           , table_no
                           , customer_count
                           , total_price
                           , comment
                           , version)
values ( 10005
       , 500
       , 11000
       , '2021-09-17 13:42'
       , 11000
       , '2021-09-18 14:08'
       , 'PREPARING'
       , 101
       , '1234567891'
       , '聚源美食'
       , 'C01'
       , 9
       , 782
       , '使用包间C01'
       , 3)
on duplicate key update tenant_id = 500;
insert into catering_order_item ( id
                                , tenant_id
                                , order_id
                                , seq_no
                                , status
                                , place_quantity
                                , produce_quantity
                                , latest_quantity
                                , product_id
                                , product_name_on_place
                                , product_unit_price_on_place
                                , product_unit_of_measure_on_place
                                , version)
values ( 100051
       , 500
       , 10005
       , '1'
       , 'PREPARING'
       , 1
       , 0
       , 1
       , 108
       , '番茄牛肉'
       , 86.6
       , '元/份'
       , 2)
on duplicate key update tenant_id = 500;
insert into catering_order_item ( id
                                , tenant_id
                                , order_id
                                , seq_no
                                , status
                                , place_quantity
                                , produce_quantity
                                , latest_quantity
                                , product_id
                                , product_name_on_place
                                , product_unit_price_on_place
                                , product_unit_of_measure_on_place
                                , version)
values ( 100052
       , 500
       , 10005
       , '2'
       , 'PREPARING'
       , 1
       , 0
       , 1
       , 102
       , '松鼠鱼'
       , 128.8
       , '元/份'
       , 2)
on duplicate key update tenant_id = 500;
insert into catering_order_item ( id
                                , tenant_id
                                , order_id
                                , seq_no
                                , status
                                , place_quantity
                                , produce_quantity
                                , latest_quantity
                                , product_id
                                , product_name_on_place
                                , product_unit_price_on_place
                                , product_unit_of_measure_on_place
                                , version)
values ( 100053
       , 500
       , 10005
       , '3'
       , 'PREPARING'
       , 3
       , 0
       , 3
       , 106
       , '酸菜鱼'
       , 88.8
       , '元/份'
       , 2)
on duplicate key update tenant_id = 500;
insert into catering_order_item ( id
                                , tenant_id
                                , order_id
                                , seq_no
                                , status
                                , place_quantity
                                , produce_quantity
                                , latest_quantity
                                , product_id
                                , product_name_on_place
                                , product_unit_price_on_place
                                , product_unit_of_measure_on_place
                                , version)
values ( 100054
       , 500
       , 10005
       , '4'
       , 'PREPARING'
       , 5
       , 0
       , 5
       , 106
       , '肉末茄子'
       , 28.8
       , '元/份'
       , 4)
on duplicate key update tenant_id = 500;
insert into catering_order_item ( id
                                , tenant_id
                                , order_id
                                , seq_no
                                , status
                                , place_quantity
                                , produce_quantity
                                , latest_quantity
                                , product_id
                                , product_name_on_place
                                , product_unit_price_on_place
                                , product_unit_of_measure_on_place
                                , version)
values ( 100055
       , 500
       , 10005
       , '5'
       , 'PREPARED'
       , 3
       , 0
       , 3
       , 109
       , '京酱肉丝'
       , 38.8
       , '元/份'
       , 5)
on duplicate key update tenant_id = 500;
insert into catering_order_item ( id
                                , tenant_id
                                , order_id
                                , seq_no
                                , status
                                , place_quantity
                                , produce_quantity
                                , latest_quantity
                                , product_id
                                , product_name_on_place
                                , product_unit_price_on_place
                                , product_unit_of_measure_on_place
                                , product_method_id
                                , product_method_name_on_place
                                , product_method_group_name_on_place
                                , version)
values ( 100056
       , 500
       , 10005
       , '6'
       , 'PREPARING'
       , 4
       , 0
       , 4
       , 100
       , '宫保鸡丁'
       , 26.8
       , '元/份'
       , 10023
       , '重辣'
       , '辣度'
       , 3)
on duplicate key update tenant_id = 500;
insert into catering_order_item_accessory ( id
                                          , tenant_id
                                          , version
                                          , order_item_id
                                          , seq_no
                                          , status
                                          , place_quantity
                                          , produce_quantity
                                          , latest_quantity
                                          , product_accessory_id
                                          , product_accessory_name_on_place
                                          , product_accessory_group_name_on_place
                                          , product_accessory_unit_price_on_place
                                          , product_accessory_unit_of_measure_on_place)
values ( 10005601
       , 500
       , 3
       , 100056
       , '6-1'
       , 'PREPARING'
       , 4
       , 0
       , 4
       , 10013
       , '米饭'
       , '配菜'
       , 2
       , '碗')
on duplicate key update tenant_id = 500;
insert into catering_order_item_accessory ( id
                                          , tenant_id
                                          , version
                                          , order_item_id
                                          , seq_no
                                          , status
                                          , place_quantity
                                          , produce_quantity
                                          , latest_quantity
                                          , product_accessory_id
                                          , product_accessory_name_on_place
                                          , product_accessory_group_name_on_place
                                          , product_accessory_unit_price_on_place
                                          , product_accessory_unit_of_measure_on_place)
values ( 10005602
       , 500
       , 3
       , 100056
       , '6-2'
       , 'PREPARING'
       , 8
       , 0
       , 8
       , 10011
       , '泡萝卜'
       , '配菜'
       , 1
       , '份')
on duplicate key update tenant_id = 500;