-- Product

-- Row 1
insert into product ( id
                    , tenant_id
                    , created_by
                    , created_at
                    , last_modified_by
                    , last_modified_at
                    , name
                    , unit_price
                    , unit_of_measure
                    , min_sales_quantity
                    , increase_sales_quantity
                    , description
                    , enabled
                    , version)
values ( 100
       , 500
       , 11000
       , '2021-08-01 11:32'
       , 11000
       , '2021-08-01 12:12'
       , '宫保鸡丁'
       , 26.8
       , '元/份'
       , 1
       , 1
       , '是汉族传统经典的名菜,属川菜名菜。创始人为四川地区居民,后被宫保丁宝桢改良发扬光大,流传至今。此道菜也被归纳为北京宫廷菜。 红而不辣、辣而不猛、香辣味浓、肉质滑脆。'
       , 1
       , 2)
on duplicate key update tenant_id = 500;

-- Row 2
insert into product ( id
                    , tenant_id
                    , created_by
                    , created_at
                    , last_modified_by
                    , last_modified_at
                    , name
                    , unit_price
                    , unit_of_measure
                    , min_sales_quantity
                    , increase_sales_quantity
                    , description
                    , enabled
                    , version)
values ( 101
       , 500
       , 11000
       , '2021-08-01 11:41'
       , null
       , null
       , '红烧肉'
       , 48.8
       , '元/份'
       , 1
       , 1
       , '一道著名的大众菜肴，各大菜系都有自己特色的红烧肉。其以五花肉为制作主料，最好选用肥瘦相间的三层肉（五花肉）来做，锅具以砂锅为主，做出来的肉肥瘦相间，香甜松软，营养丰富，入口即化。'
       , 1
       , 1)
on duplicate key update tenant_id = 500;

-- Row 3
insert into product ( id
                    , tenant_id
                    , created_by
                    , created_at
                    , last_modified_by
                    , last_modified_at
                    , name
                    , unit_price
                    , unit_of_measure
                    , min_sales_quantity
                    , increase_sales_quantity
                    , description
                    , enabled
                    , version)
values ( 102
       , 500
       , 11000
       , '2021-08-01 13:18'
       , 11000
       , '2021-08-08 14:21'
       , '松鼠鱼'
       , 128.8
       , '元/份'
       , 1
       , 1
       , '松鼠鱼是江苏省的一道传统名菜，属于苏菜系，该菜品以黄鱼为主要材料；松鼠鱼因形似松鼠而得名，通常以黄鱼、鲤鱼、鳜鱼等鱼类为原料，将鱼去鳞、鳃、内脏，用清水洗净。以胸腹鳍处下刀，将鱼头切下，然后再从下颌处下刀，将鱼头劈半刀，用刀略拍，剔下两面鱼肉，除净胸部细刺，鱼尾相连入油锅炸到金黄色，再浇上酱汁拼盘而成的美食；其色泽鲜艳，鲜嫩酥香，酸甜适口。'
       , 0
       , 9)
on duplicate key update tenant_id = 500;

-- Row 4
insert into product ( id
                    , tenant_id
                    , created_by
                    , created_at
                    , last_modified_by
                    , last_modified_at
                    , name
                    , unit_price
                    , unit_of_measure
                    , min_sales_quantity
                    , increase_sales_quantity
                    , description
                    , enabled
                    , version)
values ( 103
       , 500
       , 11000
       , '2021-08-02 19:56'
       , 11000
       , '2021-08-05 20:39'
       , '红烧狮子头'
       , 62.6
       , '元/份'
       , 1
       , 1
       , '也叫四喜丸子，是一道淮扬名菜。将有肥有瘦的肉配上荸荠，香菇等材料，做成丸子，然后先炸后煮。出锅后香味扑鼻，光闻起来就引动食欲，醇香味浓的肉块与汁液，超级美味。'
       , 1
       , 5)
on duplicate key update tenant_id = 500;

-- Row 5
insert into product ( id
                    , tenant_id
                    , created_by
                    , created_at
                    , last_modified_by
                    , last_modified_at
                    , name
                    , unit_price
                    , unit_of_measure
                    , min_sales_quantity
                    , increase_sales_quantity
                    , description
                    , enabled
                    , version)
values ( 104
       , 500
       , 11000
       , '2021-08-06 16:23'
       , 11000
       , '2021-08-06 23:12'
       , '啤酒鸭'
       , 26.8
       , '元/份'
       , 1
       , 1
       , '以鸭子、啤酒为主料的特色佳肴，据传起源于清代。将鸭肉与啤酒一同炖煮成菜，使滋补的鸭肉味道更加浓厚，鸭肉不仅入口鲜香，还带有一股啤酒清香。'
       , 0,
         3)
on duplicate key update tenant_id = 500;

-- Row 6
insert into product ( id
                    , tenant_id
                    , created_by
                    , created_at
                    , last_modified_by
                    , last_modified_at
                    , name
                    , unit_price
                    , unit_of_measure
                    , min_sales_quantity
                    , increase_sales_quantity
                    , description
                    , enabled
                    , version)
values ( 105
       , 500
       , 11000
       , '2021-08-07 10:23'
       , null
       , null
       , '鱼香肉丝'
       , 36.6
       , '元/份'
       , 1
       , 1
       , '是四川的一道特色名菜，该菜品以泡辣椒、子姜、大蒜、糖和醋炒制猪里脊肉丝而成，由民国时期的一位川菜大厨所创制，相传灵感来自泡椒肉丝。'
       , 1
       , 1)
on duplicate key update tenant_id = 500;

-- Row 7
insert into product ( id
                    , tenant_id
                    , created_by
                    , created_at
                    , last_modified_by
                    , last_modified_at
                    , name
                    , unit_price
                    , unit_of_measure
                    , min_sales_quantity
                    , increase_sales_quantity
                    , description
                    , enabled
                    , version)
values ( 106
       , 500
       , 11000
       , '2021-08-09 16:21'
       , 11000
       , '2021-08-10 12:21'
       , '酸菜鱼'
       , 88.8
       , '元/份'
       , 1
       , 1
       , '也称为酸汤鱼，是一道源自重庆市的经典菜品，以其特有的调味和独特的烹调技法而著称。流行于上世纪90年代，是重庆江湖菜的开路先锋之一。'
       , 1
       , 4)
on duplicate key update tenant_id = 500;


-- Row 8
insert into product ( id
                    , tenant_id
                    , created_by
                    , created_at
                    , last_modified_by
                    , last_modified_at
                    , name
                    , unit_price
                    , unit_of_measure
                    , min_sales_quantity
                    , increase_sales_quantity
                    , description
                    , enabled
                    , version)
values ( 107
       , 500
       , 11000
       , '2021-08-21 09:18'
       , 11000
       , '2021-08-30 04:17'
       , '肉末茄子'
       , 28.8
       , '元/份'
       , 1
       , 1
       , '肉末茄子是一道以茄子和猪肉为主要食材，大蒜、红椒、香葱、油、黄豆酱、草菇老抽等作为辅料制作而成的家常菜。'
       , 1
       , 4)
on duplicate key update tenant_id = 500;

-- Row 8
insert into product ( id
                    , tenant_id
                    , created_by
                    , created_at
                    , last_modified_by
                    , last_modified_at
                    , name
                    , unit_price
                    , unit_of_measure
                    , min_sales_quantity
                    , increase_sales_quantity
                    , description
                    , enabled
                    , version)
values ( 108
       , 600
       , 12000
       , '2021-08-07 18:09'
       , null
       , null
       , '番茄牛肉'
       , 86.6
       , '元/份'
       , 1
       , 1
       , '是东北的传统名菜，属于东北菜系。此菜汁浓味香。酸甜可口。既有蛋白质又有维生素。做法实在简单。放在饭上，分外赏心悦目。勾起食欲。'
       , 1
       , 1)
on duplicate key update tenant_id = 600;

-- Row 9
insert into product ( id
                    , tenant_id
                    , created_by
                    , created_at
                    , last_modified_by
                    , last_modified_at
                    , name
                    , unit_price
                    , unit_of_measure
                    , min_sales_quantity
                    , increase_sales_quantity
                    , description
                    , enabled
                    , version)
values ( 109
       , 500
       , 11000
       , '2021-10-09 21:22'
       , 11000
       , '2021-10-11 16:06'
       , '京酱肉丝'
       , 38.8
       , '元/份'
       , 1
       , 1
       , '京酱肉丝是北京市的一道著名小吃，属于北京菜；该菜品在制作时选用猪瘦肉为主料，辅以甜面酱、葱、姜及其它调料，用北方特有烹调技法“六爆”之一的“酱爆”烹制而成。成菜后，咸甜适中，酱香浓郁，风味独特。'
       , 1
       , 4)
on duplicate key update tenant_id = 500;

-- Product Accessory

-- Row 1
insert into product_accessory ( id
                              , product_id
                              , tenant_id
                              , name
                              , group_name
                              , unit_price
                              , unit_of_measure)
values ( 10011
       , 100
       , 500
       , '泡萝卜'
       , '配菜'
       , 1
       , '份')
on duplicate key update unit_price = 1;

-- Row 2
insert into product_accessory ( id
                              , product_id
                              , tenant_id
                              , name
                              , group_name
                              , unit_price
                              , unit_of_measure)
values ( 10012
       , 100
       , 500
       , '辣椒酱'
       , '配菜'
       , 1
       , '两')
on duplicate key update unit_price = 1;

-- Row 3
insert into product_accessory ( id
                              , product_id
                              , tenant_id
                              , name
                              , group_name
                              , unit_price
                              , unit_of_measure)
values ( 10013
       , 100
       , 500
       , '米饭'
       , '配菜'
       , 2
       , '碗')
on duplicate key update unit_price = 2;

-- Product Method

-- Row 1
insert into product_method ( id
                           , product_id
                           , tenant_id
                           , name
                           , group_name)
values ( 10021
       , 100
       , 500
       , '微辣'
       , '辣度')
on duplicate key update group_name = '辣度';

-- Row 2
insert into product_method ( id
                           , product_id
                           , tenant_id
                           , name
                           , group_name)
values ( 10022
       , 100
       , 500
       , '中辣'
       , '辣度')
on duplicate key update group_name = '辣度';

-- Row 3
insert into product_method ( id
                           , product_id
                           , tenant_id
                           , name
                           , group_name)
values ( 10023
       , 100
       , 500
       , '重辣'
       , '辣度')
on duplicate key update group_name = '辣度';