# sjst-cashier-training

## 介绍

东软学院综合实战培训后端服务器工程模板

## 使用方法

### Step 1：

在本地的MySQL中创建一个新的数据库，命名为catering-management

### Step 2：

分别打开3个子工程shop, product, order下的src/main/resources/application.properties文件
修改其中的spring.datasource.username和spring.datasource.password和本地的MySQL一致

### Step 3：

通过git下载到本地，使用IDEA打开工程，分别找到如下三个启动类
<ul>
    <li>com.meituan.catering.management.CateringManagementShopApplication</li>
    <li>com.meituan.catering.management.CateringManagementProductApplication</li>
    <li>com.meituan.catering.management.CateringManagementOrderApplication</li>
</ul>
右键点击main方法，使用Debug模式启动

### Step 4：

启动成功后，MySQL中会自动创建表并添加测试数据

### Step 5：
示例程序：

#### http接口示例：
<ul>
    <li>
GET类型接口：shop模块中ShopController#findByBusinessNo</li>
<li>
POST类型接口：shop模块中ShopController#create</li>
</ul>

#### thrift接口示例：
<ul>
<li>
thrift接口：shop模块中ShopThriftServiceImpl#findByBusinessNo
</li>
<li>
调用该thrift接口：order模块中ShopRemoteServiceImpl#findByBusinessNo
</li>
</ul>

#### 测试用例示例：
shop模块中ShopBizServiceImplTest。