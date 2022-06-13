import { Button, Checkbox, Form, Row, Col, Input, Modal, InputNumber, Space, notification, Tag, Select, Skeleton, Table, Popconfirm, message, Cascader } from "antd";
import {LeftOutlined, UnorderedListOutlined, MinusCircleOutlined, DoubleLeftOutlined, RightOutlined, DoubleRightOutlined } from "@ant-design/icons";
import React, { useEffect, useState } from "react";
import "../../css/createAndEditOrder.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import moment from 'moment';

const { Option } = Select;

const USER_INFO = {tenantId: 500, userId: 11000};
const CURRENCY_UNIT = '元'; //统一价格单位;
const DEFULT_PAGE_TOTALCOUNT = [1, 2, 3, 4, 5];
const BUSINESS_TYPE = new Map([
    ['DINNER', '正餐'], ['FAST_FOOD', '快餐'], 
    ['HOT_POT', '火锅'], ['BARBECUE', '烧烤'], 
    ['WESTERN_FOOD', '西餐'],
]);
const MANAGEMENT_TYPE = new Map([['DIRECT_SALES', '直营'], ['ALLIANCE', '加盟']]);
const DEFUALT_PAGE_INDEX = 1;
const DEFAULT_PAGE_SIZE = 10;
const MIN_SALE_NUMBER = 1; //菜品最少销售量

/** 骨架屏计时器(限制搜索过程中没有物品时骨架屏所显示时间1s) */
function useCounter() {
  const [count, setCount] = useState(0); // 计数
  useEffect(() => {
    const timer = setTimeout(() => {
      count<=1 && setCount(count + 1); // 时间小于2s，计数+1，否则暂停计数器
    }, 1000);
    return () => clearTimeout(timer); // 组件销毁前和更新前，清理 timer
  }, [count]); // 监听依赖列表
  // 重新搜索时重置计数
  const reset = () => setCount(0);

  return [count, reset]; 
}
/** 搜索结果提示 */
const openNotification = (placement, totalpageCount) => {
  notification.info({
    message: `一共${totalpageCount}条结果`,
    placement,
    duration: 1,
  });
};
export default function CreatProduct(){

  let toOrder = false;
  let navigate = useNavigate();
  const [loaddingTime, resetTime] = useCounter();

  /** 默认添加的菜品选择项 */
  const productItems = [
    {
      key: 'DEFAULT_ITEM',
      name: null,
      children: null,
      type: 'product',
      quantity: MIN_SALE_NUMBER,
    }
  ];

  const [form] = Form.useForm();
  const [informationModalVisible, setInformationModalVisible] = useState(false);
  const [orderItemList, setOrderItemList] = useState(productItems);

  /** 门店模块状态 */
  const [shopForm] = Form.useForm();
  const [shopPageSize, setShopPageSize] = useState(DEFAULT_PAGE_SIZE);
  const [shopList, setShopList] = useState([]);
  const [isSearchBtn, setIsSearchBtn] = useState(false);
  const [shopPageIndex, setShopPageIndex] = useState(DEFUALT_PAGE_INDEX);
  const [shopModalVisible, setShopModalVisible] = useState(false);
  const [shopPageBtnGroup, setShopPageBtnGroup] = useState(DEFULT_PAGE_TOTALCOUNT);
  const [shopTotalPageCount, setShopTotalPageCount] = useState();
  
  /** 菜品模块状态 */
  const [productForm] = Form.useForm();
  const [productPageSize, setProductPageSize] = useState(DEFAULT_PAGE_SIZE);
  const [productList, setProductList] = useState([]);
  const [productPageIndex, setProductPageIndex] = useState(DEFUALT_PAGE_INDEX);
  const [productPageBtnGroup, setProductPageBtnGroup] = useState(DEFULT_PAGE_TOTALCOUNT);
  const [productTotalPageCount, setProductTotalPageCount] = useState();
  const [productModalVisible, setProductModalVisible] = useState();
  const [backCache, setBackCache] = useState();
  const [changeItem, setChangeItem] = useState();
  const [lastItem, setLastItem] = useState();
  const [productListTitle, setProductListTitle] =useState();
  const [methodCache, setMethodCache] = useState();
  const [methodBackCache, setMethodBackCache] = useState();
  const [checkedProductCache, setCheckedProductCache] = useState([]);

  const columns = [
    {
        title: '序号',
        render: (text, records, index)=> 
        <Tag style={{fontSize: 15}}>{getOrderItemNo(records, index)}</Tag>,
    },
    {
        title: '菜品名',
        dataIndex: 'name',
        width: '33%',
        render: (text, records, index) => (
          <Row className="shop-select-btn">
            <Col span={19}>
              <Input 
                style={{
                  borderRadius: '10px 0px 0px 10px',
                  borderRight: 0,
                  pointerEvents: "none",
                  background: 'white',
                  paddingLeft: '6%',
                  marginLeft: (records.type==='accessory'?"10%":""),
                }}
                readOnly
                placeholder={
                  text === null ?
                  `请选择菜品${index+1}` : 
                  records.methodName !== undefined || records.methodName != null ?
                  `${text}-${records.methodName}` : text
                }
              />
            </Col>
            <Col>
              <Button onClick={()=>{changeProduct(records)}}>选择</Button>
            </Col>
          </Row>
        )
    },
    {
        title: '数量',
        dataIndex: 'quantity',
        key: 'quantity',
        sorter: (a, b) => a.quantity - b.quantity,
        render: (text, records, index) => (
          <InputNumber min={1} max={999} value={text} onChange={(value)=>quantityChange(value, records, index)}/>
        )
    },
    {
        title: '单价',
        dataIndex: 'unitPrice',
        key: 'unitPrice',
        sorter: (a, b) => a.unitPrice - b.unitPrice,
    },
    {
      title:  '计量单位',
      dataIndex: 'unitOfMeasure',
      key: 'unitOfMeasure',
      filterSearch: true,
            filters: [
                {
                    text: '斤',
                    value: '元/斤',
                },
                {
                    text: '份',
                    value: '元/份',
                },
                {
                    text: '盘',
                    value: '元/盘',
                },
                {
                    text: '碗',
                    value: '元/碗'
                },
                {
                    text: '克',
                    value: '元/克',
                },
                {
                    text: '公斤',
                    value: '元/公斤',
                },
            ],
            onFilter: (value, record) => record.unitOfMeasure.indexOf(value) === 0,
      render: (text, records, index) => (
        text != null && (text.indexOf('/') !== -1 ? text.split('/')[1] : text)
      )
    },
    {
      title: '总价',
      dataIndex: 'totalPrice',
      key: 'totalPrice',
      sorter: (a, b) => a.totalPrice - b.totalPrice,
    },
    {
      render: (text, records, index) => (
        <Popconfirm
        title={`确定删除此菜品?`}
        onConfirm={()=>{confirm(records, index)}}
        onCancel={()=>{cancel()}}
        okText="确定"
        cancelText="取消"
        >
          {
            records.name===null ? 
              "" : orderItemList.length>1 ? 
                <MinusCircleOutlined style={{fontSize: '200%', paddingTop: 2, color: "gray"}}/> 
                : 
                (records.children != null && records.children.length > 0) || records.type==='accessory' ?
                  <MinusCircleOutlined style={{fontSize: '200%', paddingTop: 2, color: "gray"}}/>
                  :
                  ""
          } 
        </Popconfirm>                 
      ),
    },
];
/** 选择菜品(支持多选) */
const handleProductSelect = (index) => {
  setProductListTitle('新增菜品(可多选/取消菜品)');
  resetProductSearch();
  cacheManage('backup'); //备份取消前的菜品选择缓存
  setProductModalVisible(true);
};

/** 更换菜品(仅支持单选) */
const changeProduct = (records) => {
  setProductListTitle('更换菜品');
  resetProductSearch();
  setProductModalVisible(true);
  cacheManage('backup'); //备份取消前的菜品选择缓存
  setChangeItem(records);
};
/** 菜品缓存管理(备份&恢复缓存) */
const cacheManage = (option) => {
  switch (option) {
    case 'backup':
      const backupCache = JSON.parse(JSON.stringify(checkedProductCache));
      const backupMethodCache = structuredClone(methodCache);
      setBackCache(backupCache); //设置菜品缓存备份
      setMethodBackCache(backupMethodCache); //设置做法缓存备份
      break;
    case 'restore':
      const restoreCache = JSON.parse(JSON.stringify(backCache));
      const restoreMethodCache = structuredClone(methodBackCache);
      setCheckedProductCache(restoreCache); //恢复菜品缓存备份
      setMethodCache(restoreMethodCache); //恢复做法缓存备份
      break;
    default:
      break;
  }
};

/** 生成订单项的序号 */
const getOrderItemNo = (orderItem, index) => {
  let itemNo = null;
  if(orderItem.type === 'product'){
    itemNo = index + 1;
  }else{
    orderItemList.map((item, index)=>{
      item.children != null && item.children.map((i, ind)=>{
        if(orderItem.id === i.id){
          itemNo = `${index+1}-${ind+1}`;
        }
      })
    })
  }
  return itemNo;
};

/** 订单项中菜品或者配料数量的修改 */
const quantityChange = (value, records, index) =>{
  let newOrderItemList = [].concat(orderItemList);
  records.type === 'product' ? 
    [newOrderItemList[index].quantity, newOrderItemList[index].totalPrice] = [value, records.name !== null ? newOrderItemList[index].unitPrice * value : '']
    : 
    newOrderItemList.map((item)=>{
      if(item.children != null){
        if(records.id === item.children[index].id){
          item.children[index].quantity = value;
          item.children[index].totalPrice = item.children[index].unitPrice * value;
        }
      }
      return item;
    })
  setOrderItemList(newOrderItemList);
}

const confirm = (records, index) => {
  let newOrderItemList = [].concat(orderItemList);
  let newCheckedProductCache = structuredClone(checkedProductCache);
  let isError = false;
  records.type ===  'product' ? 
    newOrderItemList.length === 1 ? 
      isError = true : (newOrderItemList.splice(index, 1) && checkedProductCache.splice(index, 1))
    :
    newOrderItemList.map((item, itemIndex)=>{
      item.children != null && item.children.map((i, ind)=>{
        i.id === records.id && (item.children.length === 1 ? item.children = null : 
            (item.children.splice(ind, 1) && newCheckedProductCache[itemIndex].children.splice(ind, 1))
          );
        return i;
      })
      return item;
    })
  setOrderItemList(newOrderItemList);
  initialForm();
  isError ? message.error('请至少添加一项菜品') : message.success('删除成功');
}
const cancel = () => {
  message.warning('取消删除');
}

  useEffect(() => {
    initialForm();
  }, [orderItemList])

  /** 设置此订单的表格数据域 */
  const initialForm = () => {
    form.setFieldsValue({
      table: orderItemList,
    });
  }
  
  /** 提交表单 */
  const onFinish = (orderInfo) => {
    if(requestValid(orderInfo)){
      const requestBody = createRequestBody(orderInfo);
      console.log('结构体:', requestBody);
      creatAxiosRequest(requestBody, USER_INFO);
    }
  }
  
  const requestValid = (orderInfo) => {
    if(orderInfo.shopBusinessNo === undefined){
      message.error('请选择门店');
      return false;
    }
    else if(orderInfo.table.length === 1 && orderInfo.table[0].key === "DEFAULT_ITEM"){
      message.error('请选择至少一项菜品');
      return false;
    }
    else return true;
  }
  /** 创建下单request结构体 */
  const createRequestBody = (orderInfo) => {
    let items = [];
    let totalPrice = 0;
    orderInfo.table.map((item, index)=>{
      let accessoryGroups = [];
      item.children !== null && item.children !== undefined && item.children.map((i, ind)=>{
        accessoryGroups.push(
          {
            productAccessoryId: i.id,
            quantity: i.quantity,
            seqNo: `${index+1}-${ind+1}`
          }
        )
        totalPrice += i.quantity * i.unitPrice; 
      })
      items.push(
        {
          accessories: accessoryGroups,
          productId: item.id,
          productMethodId: item.methodId, 
          quantity: item.quantity,
          seqNo: index+1
        }
      )
      totalPrice += item.quantity * item.unitPrice;
    })
    const bodyObj = {
      items: items,
      comment: orderInfo.comment,
      customerCount: orderInfo.customerCount,
      shopBusinessNo: orderInfo.shopBusinessNo,
      tableNo: orderInfo.tableNo,
      totalPrice: totalPrice
    }
    return bodyObj;
  }

  /** 创建订单request请求 */
  const creatAxiosRequest = (request, headerValues) => {
    const [tenantId, userId] = [headerValues.tenantId, headerValues.userId];
    const instance = axios.create({
      headers:{tenantId: tenantId, userId: userId}
    });
    instance
      .post('/order/catering', request)
      .then(response => {
        const code = response.data.status.code;
        if(code === 0){
          notification['success']({
            message: `订单创建成功`,
            description:
            <Space direction="vertical">
              <div>
                <Tag color='green'>创建者</Tag>
                <span>{userId}</span>
              </div>
              <div>
                <Tag color='blue'>创建时间</Tag>
                <span>{moment().format('YYYY-MM-DD HH:mm:ss')}</span>
              </div>
            </Space>
          });
          if(toOrder){
            navigate('/order');
          }
        }else{
          notification['error']({
            message: `订单创建失败`,
            description:
            <Space direction="vertical">
              <div>
                <Tag color='red'>响应码</Tag>
                <span>{code}</span>
              </div>
              <div>
                <Tag color='yellow'>详细信息</Tag>
                <span>请联系系统管理员</span>
              </div>
            </Space>
          });
        }
        console.log(response);
      }, error => {
        console.log(error);
      })
  }

  /** 保存并返回 */
  const saveToProduct = () =>{
    toOrder = true;
    form.submit();
  }

  const handleShopSelect = () => {
    resetShopSearch();
    setShopModalVisible(true);
  }

  /*****************************************************  门店选择模块 ***************************************************************/


  const shopColumns = [
    {
        title: '门店名',
        dataIndex: 'name',
        key: 'name',
        sorter: (a, b) => a.name.localeCompare(b.name),
    },
    {
        title: '主营业态',
        dataIndex: 'businessType',
        key: 'businessType',
        filters: [
            {
                text: '正餐',
                value: 'DINNER',
            },
            {
                text: '快餐',
                value: 'FAST_FOOT',
            },
            {
                text: '火锅',
                value: 'HOT_POT',
            },
            {
                text: '烧烤',
                value: 'BARBECUE',
            },
            {
                text: '西餐',
                value: 'WESTERN_FOOD',
            },
        ],
        filterSearch: true,
        onFilter: (value, record) => record.businessType.indexOf(value) === 0,
        render: businessType => <>{BUSINESS_TYPE.get(businessType)}</>
    },
    {
        title: '管理类型',
        dataIndex: 'managementType',
        key: 'managementType',
        filterSearch: true,
        filters: [
            {
                text: '直营',
                value: 'DIRECT_SALES',
            },
            {
                text: '加盟',
                value: 'ALLIANCE',
            }
        ],
        onFilter: (value, record) => record.managementType.indexOf(value) === 0,
        render: managementType => <>{MANAGEMENT_TYPE.get(managementType)}</>
    },
    {
        title: '营业时间',
        dataIndex: 'openingHours',
        key: 'openingHours',
        render: openingHours => openingHours.openTime + "~" + openingHours.closeTime,
    },
    {
        title: '营业面积',
        dataIndex: 'businessArea',
        key: 'businessArea',
        sorter: (a, b) => parseFloat(a.businessArea) - parseFloat(b.businessArea),
    }
  ];

  /** 别处触发表单提交 */
  const shopSubmitForm = () => {
    shopForm.submit();
  }

  /** 表单提交 */
  const onShopSearchFinish = searchValues => {
    ShopPageRequest(searchValues);
  }

  /** 手动搜索结果 */
  const onShopSearch = () => {
    resetTime();
    setIsSearchBtn(true);
    setShopPageIndex(DEFUALT_PAGE_INDEX);
    shopSubmitForm();
  }

  /** 重置搜索 */
  const resetShopSearch = () => {
      resetTime();
      setIsSearchBtn(true);
      setShopPageIndex(DEFUALT_PAGE_INDEX);
      initialShopSearchForm();
      shopSubmitForm();
  }

  /** 接口request结构体 */
  const shopRequestBody = (searchValues, pageIndex, pageSize) => {
      const [businessType, managementType, keyword] = [
          searchValues.businessType, 
          searchValues.managementType, 
          searchValues.shopName
      ];
      const requestBody = {
          "condition": {
              "businessTypes": businessType === null ? null : [businessType],
              "enabled": true,
              "keyword": keyword,
              "managementTypes": managementType === null ? null : [managementType]
            },
            "pageIndex": pageIndex,
            "pageSize": pageSize,
            "sortFields": [
              {
                "asc": false,
                "field": "last_modified_at"
              },
              {
                "asc": false,
                "field": "created_at"
              }
            ]
      }
      return requestBody;
  }

  /** 门店搜索request请求 */
  const ShopPageRequest = searchValues => { 
      const request = shopRequestBody(searchValues, shopPageIndex, shopPageSize);
      const instance = axios.create({
          headers: {tenantId: USER_INFO.tenantId, userId: USER_INFO.userId}
      });
      instance
          .post('/shop/search/', request)
          .then(res => {
              const [data, totalPageCount, totalCount] = res.data.data === null ? [[], 1, 0] : 
              [
                  res.data.data.records, 
                  res.data.data.totalPageCount, 
                  res.data.data.totalCount,
              ];
              if(isSearchBtn){
                  openNotification('bottomRight', totalCount);
              }
              setShopTotalPageCount(totalPageCount);
              setTotalCountBtn(totalPageCount);   
              setIsSearchBtn(false);
              setShopList(data);
              
          }, error => {
              console.log(error);
          })
  }

  /** 初始化表单 */
  const initialShopSearchForm = () => {
      shopForm.setFieldsValue({
          enabled: null,
          businessType: null,
          managementType: null,
          shopName: '',
      })
  }

  /** 自定义分页组件 */
  function PaginationBtn(){
      return (
          <>
              <DoubleLeftOutlined onClick={firstPageBtn}/>
              <Button onClick={prePageBtn} type="text"><LeftOutlined/></Button>
              {
                  shopPageBtnGroup.map(index=>{
                      return (
                          <Button type="text" key={index} onClick={pageChange.bind(this, index)}>{index}</Button>
                      )
                  })
              }
              <Button onClick={lastPageBtn} type="text"><RightOutlined/></Button>
              <DoubleRightOutlined onClick={endPageBtn}/>
          </>
      )
  }

  /** 分页函数组 */
  const setTotalCountBtn = totalPageCount => {
      let pageBtn = [];
      
      /* 前4页不进行轮播 */
      if(shopPageIndex < 4 && totalPageCount >=5){
          for(let i=1; i<=5; ++i){
              pageBtn.push(i);
          }
      }
      /* 第5页开始动态暂展示轮播效果 */
      else if(totalPageCount>=5){
          let [pageStart, pageEnd, ] = [shopPageIndex-2, shopPageIndex+2];
          if(pageEnd > totalPageCount){
              pageEnd = totalPageCount;
          }
          for(let i=pageStart; i<=pageEnd; ++i){
              pageBtn.push(i);
          }
      }
      /* 倒数页数不足时展示仅剩余页码 */
      else{
          for(let i=1; i<=totalPageCount; ++i){
              pageBtn.push(i);
          }
      }
      setShopPageBtnGroup(pageBtn);
  }

  const pageChange = (pageIndex) => {
      setShopPageIndex(pageIndex);
      shopSubmitForm();
  }
  const optionChange = btnSize => {
      const totalCountForNow = (shopPageIndex - 1) * shopPageSize;
      /** 
       * 如果当前页数之前的数据总和小于分页条数，当前页码则为1
       * 如果当前页数之前的数据总和等于或者大于分页条数，当前页码则为此页码第一条数据按照分页计算后所在的页码
       */
      if(totalCountForNow < btnSize || shopPageIndex === 1){
          setShopPageIndex(DEFUALT_PAGE_INDEX);
      }
      else if(totalCountForNow === btnSize){
          setShopPageIndex(totalCountForNow / btnSize);
      }else{
          setShopPageIndex(totalCountForNow / btnSize + 1);
      }
      setShopPageSize(btnSize);
      shopSubmitForm();
  }
  const lastPageBtn= () =>{
      if(shopPageIndex!==shopTotalPageCount){
          setShopPageIndex(shopPageIndex+1);
          shopSubmitForm();
      }
  }
  const prePageBtn = () => {
      if(shopPageIndex!==DEFUALT_PAGE_INDEX){
          setShopPageIndex(shopPageIndex-1);
          shopSubmitForm();
      }
  }
  const firstPageBtn = () => {
      if(shopPageIndex!==DEFUALT_PAGE_INDEX){
          setShopPageIndex(DEFUALT_PAGE_INDEX);
          shopSubmitForm();
      }
  }
  const endPageBtn = () => {
      if(shopPageIndex!==shopTotalPageCount){
          setShopPageIndex(shopTotalPageCount);
          shopSubmitForm();
      }
  }

  const handleShopClick = (shopItem, index) => {
    setShopModalVisible(false);
    form.setFieldsValue({
      shopName: shopItem.name,
      shopBusinessNo: shopItem.businessNo,
    });
  };



/*********************************************************菜品选择模块****************************************************************/

/** 菜品取消选择事件 */
const handleProductCancel = () => {
  setProductModalVisible(false);
  cacheManage('restore'); //还原备份的菜品缓存
  setChangeItem(undefined);
  setLastItem(undefined);
}
  
/** 勾选菜品并缓存勾选信息 */
const handleCheckbox = (records, checked) => {
  let isChangeProduct = changeItem !== undefined; //是否为更换菜品请求
  const newProductList = [].concat(productList);
  let newCheckedProductCache = JSON.parse(JSON.stringify(checkedProductCache));

  //避免混淆新增菜品和更换菜品分两次map

  //1.更换菜品时的处理情况
  isChangeProduct && newProductList.map((item, index)=>{

    //勾选菜品的情况
    if(records.type === 'product' && records.id === item.id){ 
      if(records.id === changeItem.id){ //勾选自身的情况
        if(!records.checked){ //如果自身没有被勾选则勾选并添加至缓存(已被勾选则说明还未勾选过其它菜品不作操作)
          if(lastItem !== undefined){ //取消勾选上次选择的菜品
            newProductList.map((i, ind)=>{
              if(lastItem.id === i.id){
                i.checked = false;
                i.children !== undefined && i.children.map((childItem)=>{
                  childItem.checked = false;
                })
              }
            })
          }
          item.checked = checked;
          item.children !== undefined && item.children.map((i, ind)=>{i.checked = checked});
          newCheckedProductCache.push(item);
          setLastItem(item); //设置为最新勾选的菜品
        }
      }else{
        if(records.checked){
          if(lastItem === undefined || lastItem.id !== records.id){
            message.warn('此菜品已经被选择，请选择其它菜品');
          }
        }else{
          if(lastItem !== undefined){ //勾选另一个菜品时取消上次勾选的菜品和配料
            newProductList.map((i, ind)=>{
              if(lastItem.id === i.id){
                i.checked = false;
                i.children !== undefined && i.children.map((childItem)=>{
                  childItem.checked = false;
                })
              }
            })
          }
          item.checked = checked;
          newProductList.map((i, ind)=>{ //勾选另一个菜品时取消勾选原来最开始的菜品和配料
            if(changeItem.id === i.id){
              i.checked = false;
              i.children !== undefined && i.children.map((i, ind)=>{i.checked = false});
            }
          });
          item.children !== undefined && item.children.map((i, ind)=>{i.checked = checked});
          newCheckedProductCache.push(item);
          setLastItem(item); //设置为最新勾选的菜品
        }
      }
    }

    //勾选配料的情况
    else{
      item.children !== undefined && item.children.map((i, ind)=>{
        if(i.id === records.id){ //找到此配料项
          if(checked){
            let notExist = true; 

            let productID = undefined; //此配料的父ID
            newProductList.map((productItem)=>{
              productItem.children !== undefined && productItem.children.map((accessoryItem)=>{
                if(accessoryItem.id === records.id){
                  productID = productItem.id;
                  productItem.checked && (notExist = false);
                }
              })
            })
            newProductList.map((productItem)=>{
              if(productItem.id !== productID && notExist){ //如果不是此配菜则取消勾选
                productItem.checked = false;
                productItem.children !== undefined && productItem.children.map((accessoryItem)=>{
                  accessoryItem.checked = false;
                })
              }
            })

            newCheckedProductCache.map((i, ind)=>{
              i.children !== undefined && i.children.map((cacheItem)=>{
                cacheItem.id === records.id && !notExist && cacheItem.id === changeItem.id && (cacheItem.checked = checked);
              })
            });

            if(notExist){
              i.checked = checked;
              item.checked = checked;
              newCheckedProductCache.push(item);
              setLastItem(item);
            }else{
              message.warn('此菜品已经被选择，请选择其它菜品');
            };
          }else{
            let notExist = true;
            newCheckedProductCache.map((cacheItem, ind)=>{
              cacheItem.children !== undefined && cacheItem.children.map((childItem, cacheIndex)=>{
                if(childItem.id === records.id && cacheItem.id === changeItem.id){
                  notExist = false;
                  i.checked = checked;
                  childItem.checked = checked;
                }
              })
            });
            notExist && message.warn('此菜品已经被选择，请选择其它菜品');
          }
        }
      })
    }
  })

  //2.新增/减少多个菜品时的情况
  !isChangeProduct && newProductList.map((item, index)=>{

    //勾选菜品的情况
    if(records.type === 'product' && records.id === item.id){
      if(checked){
        item.checked = checked;
        item.children !== undefined && item.children.map((i, ind)=>{i.checked = checked});
        newCheckedProductCache.push(item);
      }else{
        //如果是取消勾选则需要先从缓存中删除再改变菜品
        newCheckedProductCache.map((i, index)=>{
          i.id === records.id && newCheckedProductCache.splice(index, 1);
        })

        item.checked = checked;
        item.children !== undefined && item.children.map((i, ind)=>{i.checked = checked});
      }
    }

    //勾选配料的情况
    else{
      item.children !== undefined && item.children.map((i, ind)=>{
        if(records.id === i.id){
          i.checked = checked; // 勾选&取消此配料
          if(checked){
            let notExist = true;
            item.checked = checked;
            newCheckedProductCache.map((i, ind)=>{
              i.children !== undefined && i.children.map((cacheItem, cacheIndex)=>{
                cacheItem.id === records.id && ([cacheItem.checked, notExist] = [checked, false]);
              })
            })
            notExist && newCheckedProductCache.push(item);
          }else{
            newCheckedProductCache.map((i, ind)=>{
              i.children !== undefined && i.children.map((cacheItem, cacheIndex)=>{
                cacheItem.id === records.id && (cacheItem.checked = checked);
              })
            })
          }
        }
      })
    }
  });

  //更换菜品时勾选其它菜品删除被更换菜品的缓存
  if(changeItem !== undefined && lastItem === undefined){ 
    //缓存菜品数量大于1时再删除被更换的菜品 且确认与勾选前缓存的数据是否和勾选后一样(勾选到重复或者不勾选的情况)
    newCheckedProductCache.length > 1 && newCheckedProductCache.toString() !== checkedProductCache.toString() && newCheckedProductCache.map((item, index)=>{
      item.id === changeItem.id && newCheckedProductCache.splice(index, 1);
    })
  };
  //更换菜品时勾选其它菜品删除上个被勾选菜品的缓存
  if(lastItem !== undefined){
    newCheckedProductCache.length > 1 && newCheckedProductCache.toString() !== checkedProductCache.toString() && newCheckedProductCache.map((item, index)=>{
      item.id === lastItem.id && newCheckedProductCache.splice(index, 1);
    })
  };
  setCheckedProductCache(newCheckedProductCache);
};

/** 确认勾选菜品添加至订单 */
const handleProductSave = () => {
  let newCheckedProductCache = JSON.parse(JSON.stringify(checkedProductCache));
  newCheckedProductCache.map((item, index)=>{
    if(methodCache !== undefined && methodCache.has(item.id)){
      const [groupIndex, memberIndex] = [methodCache.get(item.id)[0], methodCache.get(item.id)[1]];
      item.methodGroups[groupIndex].options.map((i, ind)=>{
        i.id === memberIndex && ([item['methodName'], item['methodId']] = [i.name, i.id]);
      });
    }
    item.children !== null && item.children !== undefined && (item.children = (item.children.filter((i)=> i.checked === true)));
  })
  setOrderItemList(newCheckedProductCache);
  setProductModalVisible(false);
  setChangeItem(undefined);
  setLastItem(undefined);
}

/**重组数据(获取菜品页面时) */
const reconfigProductList = (resList) => {
  const newCheckedProductCache = JSON.parse(JSON.stringify(checkedProductCache));
  const newResList = resList.map((item, index)=>{
    let notExist = true;
    newCheckedProductCache.forEach((cacheItem)=>{
      if(cacheItem.id === item.id){
        [item, notExist] = [cacheItem, false] //将目前菜品列表中存在的菜品替换成缓存中的菜品
        item['key'] = item.id+Math.random(); //重新给key赋值，否则换页顺序会出问题
      }
    });
    if(notExist){
      item['quantity'] = 1;
      item['methodId'] = null;
      item['key'] = item.id+Math.random();
      item['type'] = 'product';
      item['checked'] = false;
      item['totalPrice'] = item.unitPrice;
      if(item.accessoryGroups.length > 0){
        item['children'] = item.accessoryGroups[0].options;
        item.children.map((i, ind)=>{
          i['quantity'] = 1;
          i['key'] = i.id+Math.random();
          i['type'] = 'accessory';
          i['checked'] = false;
          i['totalPrice'] = i.unitPrice;
        })
      }
    };
    return item;
  })
  return newResList;
}

/** 选择做法时将做法数据缓存 */
const handleMethodChange = (selectedOptions, productItem) => {
  const newMethodCache = methodCache === undefined ? new Map() : structuredClone(methodCache);
  //取消勾选做法则删除
  selectedOptions === undefined ? newMethodCache.delete(productItem.id) : newMethodCache.set(productItem.id, selectedOptions);
  setMethodCache(newMethodCache);
  console.log(newMethodCache);
}

const productColumns = [
  {
    width: '8%',
    render: (text, records, index) => {
      return (
        <Checkbox onChange={(e)=>handleCheckbox(records, e.target.checked)} checked={records.checked}/>
      )
    }
  },
  {
      title: '菜品名',
      dataIndex: 'name',
      key: 'name',
      sorter: (a, b) => a.name.localeCompare(b.name),
      render: (text, records, index) => {
        return <div style={{paddingLeft: records.type==='accessory'?'10%':0}}>{text}</div>
      }
  },
  {
      title: '菜品单价(元)',
      dataIndex: 'unitPrice',
      key: 'unitPrice',
      sorter: (a, b) => a.unitPrice - b.unitPrice,
  },
  {
      title: '计量单位',
      dataIndex: 'unitOfMeasure',
      key: 'unitOfMeasure',
      filterSearch: true,
      filters: [
          {
              text: '斤',
              value: '元/斤',
          },
          {
              text: '份',
              value: '元/份',
          },
          {
              text: '盘',
              value: '元/盘',
          },
          {
              text: '碗',
              value: '元/碗'
          },
          {
              text: '克',
              value: '元/克',
          },
          {
              text: '公斤',
              value: '元/公斤',
          },
      ],
      onFilter: (value, record) => record.unitOfMeasure.indexOf(value) === 0,
      render: (text, records, index) => (
        text.indexOf('/') !== -1 ? text.split('/')[1] : text
      )
  },
  {
    title: '做法',
    dataIndex: 'methodGroups',
    render: (text, records, index) => 
    {
      if(text !== undefined && text.length > 0){
        const options = text.map((item, index)=>{
          item['label'] = item.name;
          item['value'] = index;
          item['children'] = item.options.map((i)=>{
            i['label'] = i.name;
            i['value'] = i.id;
            return i;
          })
          return item;
        })
        return (
          <Cascader 
          className="product-cascader"
          options={options} 
          placeholder='请选择做法' 
          defaultValue={()=>getDefaultMethod(records)} 
          onChange={(value)=>handleMethodChange(value, records)} 
          expandTrigger='hover'
          />
        )
      }
    }
  }
];

/** 取出做法缓存中存在的做法 */
const getDefaultMethod = (productItem) => {
  let defaultMethod = [];
  console.log(methodCache);
  methodCache !== undefined && methodCache.has(productItem.id) && (defaultMethod = [methodCache.get(productItem.id)[0], methodCache.get(productItem.id)[1]]);
  return defaultMethod;
}
  /** 初始化表单 */
  const initialProductSearchForm = () => {
    productForm.setFieldsValue({
        enabled: null,
        minPrice: '',
        maxPrice: '',
        unitOfMeasure: '',
        productName: '',
    })
  }

  /** 别处触发表单提交 */
  const productSubmitForm = () => {
    productForm.submit();
  }

  /** 表单提交 */
  const onProductSearchFinish = searchValues => {
      productPageRequest(searchValues);
  }

  /** 手动搜索结果 */
  const onProductSearch = () => {
      resetTime();
      setIsSearchBtn(true);
      setProductPageIndex(DEFUALT_PAGE_INDEX);
      productSubmitForm();
  }

  /** 重置搜索 */
  const resetProductSearch = () => {
      resetTime();
      setIsSearchBtn(true);
      setProductPageIndex(DEFUALT_PAGE_INDEX);
      initialProductSearchForm();
      productSubmitForm();
  }

  /** 菜品搜索接口request结构体 */
  const productRequestBody = (searchValues, pageIndex, pageSize) => {
      const [unitOfMeasure, minPrice, maxPrice, name] = [
          searchValues.unitOfMeasure.length === 0 ? "" : CURRENCY_UNIT+'/'+searchValues.unitOfMeasure, 
          searchValues.minPrice, 
          searchValues.maxPrice,
          searchValues.productName
      ];
      const requestBody = {
          "condition": {
              "enabled": true,
              "name": name,
              "unitOfMeasure": unitOfMeasure,
              "unitPrice": {
                  "from": minPrice,
                  "to": maxPrice
              }
            },
            "pageIndex": pageIndex,
            "pageSize": pageSize,
            "sortFields": [
              {
                "asc": false,
                "field": "last_modified_at"
              },
              {
                "asc": false,
                "field": "created_at"
              }
            ]
      }
      return requestBody;
  }

  /** 菜品搜索request请求 */
  const productPageRequest = searchValues => { 
      const request = productRequestBody(searchValues, productPageIndex, productPageSize);
      const instance = axios.create({
          headers: {tenantId: USER_INFO.tenantId, userId: USER_INFO.userId}
      });
      instance
          .post('/product/search', request)
          .then(res => {
            console.log(res);
              const [data, totalPageCount, totalCount] = [
                  reconfigProductList(res.data.data.records), 
                  res.data.data.totalPageCount === 0 ? 1 : res.data.data.totalPageCount, 
                  res.data.data.totalCount,
              ];
              if(isSearchBtn){
                  openNotification('bottomRight', totalCount);
              }
              setProductTotalPageCount(totalPageCount);
              setProductTotalCountBtn(totalPageCount);   
              setIsSearchBtn(false);
              setProductList(data);
          }, error => {
              console.log(error);
          })
  }

  /** 自定义分页组件 */
  function ProductPaginationBtn(){
      return (
          <>
              <DoubleLeftOutlined onClick={productFirstPageBtn}/>
              <Button onClick={productPrePageBtn} type="text"><LeftOutlined/></Button>
              {
                  productPageBtnGroup.map(index=>{
                      return (
                          <Button type="text" key={index} onClick={productPageChange.bind(this, index)}>{index}</Button>
                      )
                  })
              }
              <Button onClick={productLastPageBtn} type="text"><RightOutlined/></Button>
              <DoubleRightOutlined onClick={productEndPageBtn}/>
          </>
      )
  }

  /** 分页函数组 */
  const setProductTotalCountBtn = totalPageCount => {
      let pageBtn = [];

      /* 前4页不进行轮播 */
      if(productPageIndex < 4 && totalPageCount >=5){
          for(let i=1; i<=5; ++i){
              pageBtn.push(i);
          }
      }
      /* 第5页开始动态暂展示轮播效果 */
      else if(totalPageCount>=5){
          let [pageStart, pageEnd, ] = [productPageIndex-2, productPageIndex+2];
          if(pageEnd > totalPageCount){
              pageEnd = totalPageCount;
          }
          for(let i=pageStart; i<=pageEnd; ++i){
              pageBtn.push(i);
          }
      }
      /* 倒数页数不足时展示仅剩余页码 */
      else{
          for(let i=1; i<=totalPageCount; ++i){
              pageBtn.push(i);
          }
      }
      setProductPageBtnGroup(pageBtn);
  }
  const productPageChange = (pageIndex) => {
      setProductPageIndex(pageIndex);
      productSubmitForm();
  }
  const productOptionChange = btnSize => {
      const totalCountForNow = (productPageIndex - 1) * productPageSize;
      /** 
       * 如果当前页数之前的数据总和小于分页条数，当前页码则为1
       * 如果当前页数之前的数据总和等于或者大于分页条数，当前页码则为此页码第一条数据按照分页计算后所在的页码
       */
      if(totalCountForNow < btnSize || productPageSize === 1){
          setProductPageIndex(DEFUALT_PAGE_INDEX);
      }
      else if(totalCountForNow === btnSize){
          setProductPageIndex(totalCountForNow / btnSize);
      }else{
          setProductPageIndex(totalCountForNow / btnSize + 1);
      }
      setProductPageSize(btnSize);
      productSubmitForm();
  }
  const productLastPageBtn = () =>{
      if(productPageIndex!==productTotalPageCount){
          setProductPageIndex(productPageIndex+1);
          productSubmitForm();
      }
  }
  const productPrePageBtn = () => {
      if(productPageIndex!==DEFUALT_PAGE_INDEX){
          setProductPageIndex(productPageIndex-1);
          productSubmitForm();
      }
  }
  const productFirstPageBtn = () => {
      if(productPageIndex!==DEFUALT_PAGE_INDEX){
          setProductPageIndex(DEFUALT_PAGE_INDEX);
          productSubmitForm();
      }
  }
  const productEndPageBtn = () => {
      if(productPageIndex!==productTotalPageCount){
          setProductPageIndex(productTotalPageCount);
          productSubmitForm();
      }
  }
    return (
      <>
        <div className="create-shop-head">
          <Row>
            <Col><LeftOutlined onClick={()=>navigate('/order')}/></Col>
            <Col offset={11}>下单</Col>
            <Col offset={10}>
              <UnorderedListOutlined />
            </Col>
          </Row>
        </div>
        <Form
        form={form}
        style={{margin: 20}}
        onFinish={onFinish}
        colon={false}
        >
        <Row className="create-shop-info">
          <Col>基本信息</Col>
        </Row>
        <Row>
          <Col span={9}>
            <Form.Item label='门店' name='shopName' required style={{pointerEvents: "none"}}>
              <Input 
              style={{
                borderRadius: 0,
                borderRight: 0,
                background: 'white'
              }}
              readOnly
              />
            </Form.Item>
          </Col>
          <Col span={3}>
            <div className="shop-select-btn">
              <Button style={{width: '100%'}} onClick={handleShopSelect}>选择</Button>
            </div>
          </Col>
          <Col span={11} offset={1}>
              <Form.Item
              label='座位号'
              name='tableNo'
              rules={[
                  {
                    required: true,
                  },
                  {
                    pattern: /^[\u4e00-\u9fa5a-zA-Z0-9]{1,10}$/,
                    message: '输入不能含特殊字符，且长度不大于10',
                  },
                ]}
              >
                  <Input/>
              </Form.Item>
          </Col>
        </Row>
        <Row>
            <Col span={12}>
                <Form.Item
                label='用餐人数'
                name='customerCount'
                rules={[
                    {
                    required: true,
                    message: '输入不能为空',
                    },
                    {
                    validator(_, value) {
                        if (!value||(1000 > value && value >= 1)) {
                        return Promise.resolve();
                        }
                        return Promise.reject(new Error('人数在1000人以内!'));
                    },
                    },
                ]}
                >
                    <InputNumber min={1} style={{width: '100%'}}/>
                </Form.Item>
            </Col>
            <Col span={11} offset={1}>
                <Form.Item
                label='备注'
                name='comment'
                rules={[
                    {
                        pattern: /^[\u4e00-\u9fa5a-zA-Z0-9]{1,20}$/,
                        message: '输入不能含特殊字符，且长度不大于20',
                    },
                ]}
                >
                    <Input/>
                </Form.Item>
            </Col>
          </Row>
          <Row className="create-shop-info">
            <Col>菜品信息</Col>
          </Row>
          <Form.Item name="table" valuePropName="dataSource">
            <Table
            columns={columns}
            pagination={false}
            scroll={{y: 320}}
          />
          </Form.Item>
          <Row className="add-orderItem-btn">
            <Col span={24}>
              <Button style={{width: '100%'}} onClick={handleProductSelect}>添加新菜品</Button>
            </Col>
          </Row>
          <Row className="create-shop-footer">
            <Col span={6} offset={6}>
              <Form.Item>
                <Button style={{background: 'white'}} onClick={()=>setInformationModalVisible(true)}>取消</Button>
              </Form.Item> 
            </Col>
            <Col span={6}>
              <Form.Item>
                <Button onClick={saveToProduct}>保存并返回</Button>
              </Form.Item> 
            </Col>
            <Col span={6}>
              <Form.Item>
                <Button htmlType="submit">保存</Button>
              </Form.Item> 
            </Col>
          </Row>

          <Form.Item name='shopBusinessNo' hidden></Form.Item>
        </Form>

        <Modal
        title="选择门店"
        visible={shopModalVisible}
        footer={null}
        onCancel={()=>setShopModalVisible(false)}
        width='80%'
        centered
        forceRender
        closable={false}
        >
          <div>
            <Form
            form={shopForm}
            onFinish={onShopSearchFinish}
            style={{marginTop: 20}}
            >
                <Row>
                    <Col span={12}>
                        <Form.Item label="主营业态" name='businessType'>
                            <Select>
                            <Option value={null}>所有</Option>
                            <Option value="DINNER">正餐</Option>
                            <Option value="FAST_FOOD">快餐</Option>
                            <Option value="HOT_POT">火锅</Option>
                            <Option value="BARBECUE">烧烤</Option>
                            <Option value="WESTERN_FOOD">西餐</Option>
                            </Select>
                        </Form.Item> 
                    </Col>
                    <Col span={11} offset={1}>
                        <Form.Item label="管理类型" name="managementType">
                            <Select>
                            <Option value={null}>所有</Option>
                            <Option value="DIRECT_SALES">直营</Option>
                            <Option value="ALLIANCE">加盟</Option>
                            </Select>
                        </Form.Item> 
                    </Col>
                </Row>
                <Row className="create-shop-btn">
                    <Col span={12}>
                        <Form.Item label="门店名" name="shopName">
                            <Input onPressEnter={onShopSearch}/>
                        </Form.Item>
                    </Col>
                    <Col span={4} offset={1}>
                        <Form.Item>
                            <Button style={{width:'100%'}} onClick={onShopSearch}>搜索</Button>
                        </Form.Item>
                    </Col>
                    <Col span={4} offset={1}>
                        <Form.Item>
                            <Button style={{width: '100%', backgroundColor: 'white'}}  onClick={resetShopSearch}>重置</Button>
                        </Form.Item>
                    </Col>
                </Row>
            </Form>
            <div className="shop-table">
            {/** 骨架屏和表格显示逻辑 */}
            {
              shopList.length === 0?
                loaddingTime >= 1 ?
                  <Table
                  columns={shopColumns}
                  dataSource={shopList}
                  pagination={false}
                  scroll={{y: 320}}
                  />
                  :
                  <Skeleton active paragraph={{ rows: 10 }}/>
                :
                <Table
                columns={shopColumns}
                dataSource={shopList}
                pagination={false}
                scroll={{y: 320}}
                onRow={(records, index)=>{
                  return {
                    onClick: () => handleShopClick(records, index)
                  }
                }}
                />
            }
            </div>
            <Row style={{marginTop: '1%'}}>  
                <Col span={6} offset={10}>
                    <PaginationBtn/>
                </Col>
                <Col span={4} offset={4} >
                    <span>每页</span>
                    <Select
                    className="select_footer"
                    defaultValue={DEFAULT_PAGE_SIZE}
                    style={{width: '30%', margin: '0px 10px'}}
                    onChange={optionChange}
                    >
                        <Option value={3} >3</Option>
                        <Option value={10}>10</Option>
                        <Option value={20}>20</Option>
                        <Option value={30}>30</Option>
                        <Option value={50}>50</Option>
                        <Option value={100}>100</Option>
                    </Select>
                    <span>条记录</span>
                </Col>
            </Row>
        </div>
        </Modal>

        <Modal
        title={productListTitle}
        bodyStyle={{height: 560}}
        visible={productModalVisible}
        footer={
          <Row className="create-shop-footer">
            <Col span={6} offset={12}>
                <Button style={{width: '80%', background: 'white'}} onClick={handleProductCancel}>取消</Button>
            </Col>
            <Col span={6}>
              <Button style={{width: '80%'}} onClick={handleProductSave}>确认</Button>
            </Col>
          </Row>
        }
        width='80%'
        centered
        forceRender
        closable={false}
        >
          <Form
          form={productForm}
          onFinish={onProductSearchFinish}
          style={{marginTop: 20}}
          colon={false}
          >
            <Row>
                <Col span={12} style={{height: 0}}>
                    <Form.Item label="菜品单价">
                        <Input.Group style={{width: '100%'}}>
                            <Row>
                                <Col span={9}>
                                    <Form.Item name='minPrice'>
                                        <InputNumber min={0} precision={0} style={{width: '100%', borderRight: 0, borderRadius: 0}}/>
                                    </Form.Item>
                                </Col>
                                <Col span={2}>
                                    <Input
                                    style={{
                                        borderRadius: 0,
                                        borderLeft: 0,
                                        borderRight: 0,
                                        background: 'white',
                                        pointerEvents: "none",
                                    }}
                                    placeholder='~'
                                    disabled
                                    />
                                </Col>
                                <Col span={9}>
                                    <Form.Item name='maxPrice'>
                                        <InputNumber 
                                        min={0} 
                                        precision={0} 
                                        style={{
                                            width: '100%', 
                                            borderLeft: 0,  
                                            borderRadius: 0,
                                            borderRight: 0,
                                        }}
                                        />
                                    </Form.Item>
                                </Col>
                                <Col span={4}>
                                    <Input
                                    style={{
                                    borderLeft: 0,
                                    background: 'none',
                                    pointerEvents: 'none',
                                    borderRadius: '0 10 10 0',
                                    }}
                                    placeholder='元'
                                    disabled
                                    />
                                </Col>
                            </Row>
                        </Input.Group>
                    </Form.Item> 
                </Col>
                <Col span={11} offset={1}>
                    <Form.Item 
                    label="计量单位" 
                    name="unitOfMeasure"
                    rules={[
                        {
                          pattern: /^[\u4e00-\u9fa5a-zA-Z0-9]{1,5}$/,
                          message: '不能含特殊字符，且长度不大于5',
                        },
                    ]}
                     >
                        <Input/>
                    </Form.Item> 
                </Col>
            </Row>
            <Row className="create-shop-btn">
                <Col span={12}>
                    <Form.Item 
                    label="菜品名" 
                    name="productName"
                    rules={[
                        {
                            type: 'string',
                            whitespace: true,
                            message: '输入不能为空格',
                        },
                        {
                            max: 10,
                            message: '搜索关键字长度最大为10字符',
                        }
                    ]}
                    >
                        <Input onPressEnter={onProductSearch}/>
                    </Form.Item>
                </Col>
                <Col span={4} offset={1}>
                    <Form.Item>
                        <Button style={{width:'100%'}} onClick={onProductSearch} >查询</Button>
                    </Form.Item>
                </Col>
                <Col span={4} offset={1}>
                    <Form.Item>
                        <Button style={{width: '100%', backgroundColor: 'white'}}  onClick={resetProductSearch}>重置</Button>
                    </Form.Item>
                </Col>
            </Row>
        </Form>
        {/** 骨架屏和表格显示逻辑 */}
        {
          productList.length === 0?
            loaddingTime >= 1 ?
              <Table
              columns={productColumns}
              dataSource={productList}
              pagination={false}
              scroll={{y: 320}}
              />
              :
              <Skeleton active paragraph={{ rows: 10 }}/>
            :
            <Table
            columns={productColumns}
            dataSource={productList}
            pagination={false}
            scroll={{y: 320}}
            // rowSelection={{ ...rowSelection, checkStrictly }}
            />
          }
            <Row style={{marginTop: '1%'}}>
              <Col span={7} offset={10}>
                  <ProductPaginationBtn/>
              </Col>
              <Col span={4} offset={3} >
                  <span>每页</span>
                  <Select
                  className="select_footer"
                  defaultValue={DEFAULT_PAGE_SIZE}
                  style={{width: '30%', margin: '0px 10px'}}
                  onChange={productOptionChange}
                  >
                      <Option value={3} >3</Option>
                      <Option value={10}>10</Option>
                      <Option value={20}>20</Option>
                      <Option value={30}>30</Option>
                      <Option value={50}>50</Option>
                      <Option value={100}>100</Option>
                  </Select>
                  <span>条记录</span>
              </Col>
            </Row>
        </Modal>

        <Modal
        title="确认丢失修改的内容"
        visible={informationModalVisible}
        footer={null}
        closable={false}
        >
          <Row style={{marginBottom: 10}}><Col offset={7}><span>所有修改均会丢失，请确认?</span></Col></Row>
          <div className="close-btn">
          <Row>
            <Col span={8} offset={4}>
            <Button onClick={()=>{setInformationModalVisible(false)}}>回到当前页面</Button>
            </Col>
            <Col span={11} offset={1}>
            <Button onClick={()=>{navigate('/order')}} style={{background: 'white'}}>返回主页面</Button>
            </Col>
          </Row>
          </div>
        </Modal>
      </>
    )
}