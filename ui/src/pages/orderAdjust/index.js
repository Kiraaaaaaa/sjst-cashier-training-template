import { Form, Row, Col, Input, InputNumber, Tag, Table, Skeleton, Button, Modal, message, Select, Cascader, notification, Checkbox } from "antd";
import {LeftOutlined, UnorderedListOutlined, PlusOutlined, MinusOutlined, DoubleLeftOutlined, RightOutlined, DoubleRightOutlined } from "@ant-design/icons";
import "../../css/createAndEditOrder.css";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useLocation } from "react-router-dom";
const { Option } = Select;
const CURRENCY_UNIT = '元'; //统一价格单位;
const DEFUALT_PAGE_INDEX = 1;
const DEFAULT_PAGE_SIZE = 10;
const DEFULT_PAGE_TOTALCOUNT = [1, 2, 3, 4, 5];

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
export default function OrderAdjust() {
    
    let navigate = useNavigate();
    let location = useLocation();
    const [form] = Form.useForm();
    const [loaddingTime, resetTime] = useCounter();
    const [orderItemList, setOrderItemList] = useState();
    const [informationModalVisible, setInformationModalVisible] = useState(false);
    const [productModalVisible, setProductModalVisible] = useState();
    const [baseOrderItemList, setBaseOrderItemList] = useState();

    /** 菜品选择state */
    const [productForm] = Form.useForm();
    const [lastItem, setLastItem] = useState();
    const [changeItem, setChangeItem] = useState();
    const [productList, setProductList] = useState([]);
    const [isSearchBtn, setIsSearchBtn] = useState(false);
    const [productPageSize, setProductPageSize] = useState(DEFAULT_PAGE_SIZE);
    const [productPageIndex, setProductPageIndex] = useState(DEFUALT_PAGE_INDEX);
    const [productPageBtnGroup, setProductPageBtnGroup] = useState(DEFULT_PAGE_TOTALCOUNT);
    const [productTotalPageCount, setProductTotalPageCount] = useState();
    const [checkedProductCache, setCheckedProductCache] = useState(new Set());
    const [productListTitle, setProductListTitle] =useState();
    const [orderItem, tenantId, userId, id] = [
      location.state.data,
      location.state.data.tenantId,
      location.state.data.auditing.createdBy,
      location.state.data.id,
    ];

    useEffect(() => {
      queryOrder(tenantId, userId, id);
    }, []);

    const columns = [
      {
        title: '序号',
        render: (text, records, index)=> 
        <Tag style={{fontSize: 15}}>{getOrderItemNo(records, index)}</Tag>,
      },
      {
        title: '菜品名',
        dataIndex: 'name',
        width: '30%',
        render: (text, records, index) => (
          records.isAddProduct === undefined ?
            <span style={{color: 'gray', marginLeft: (records.type!=='product'?"5%":""),
            width: (records.type!=='product'?"90%":"")}}>
              {
                records.type === 'product' ?
                records.methodName ? 
                  `${text}-${records.methodName}` : text
                :
                ' - '+records.name
              }
            </span>
            :
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
        title: '当前数量',
        dataIndex: 'latest',
        key: 'latest',
        sorter: (a, b) => a.latest - b.latest,
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
        title: '已出餐',
        dataIndex: 'onProduce',
      },
      {
        title: '调整数量',
        width: '15%',
        render: (text, records, index) => (
          <div className="order-quantity">
            <Button shape="circle" icon={<PlusOutlined />}  onClick={()=>handleQuantityChange(records, true)}/>
            <Input value={records.quantityOnAdjustment} style={{width: '52%', borderRadius: 0}}/>
            <Button shape="circle" icon={<MinusOutlined />}  onClick={()=>handleQuantityChange(records, false)} />
          </div>
        )
      },
      {
        title: '调整后数量',
        render: (text, records, index) => (
          records.latest + records.quantityOnAdjustment
        )
      }
  ];
  const changeProduct = (records) => {
    setProductListTitle('更换菜品');
    resetProductSearch();
    setProductModalVisible(true);
    setChangeItem(records);
  }
  const handleQuantityChange = (records, isIncrease) =>{
    const newOrderItemList = [].concat(orderItemList);
    newOrderItemList.map((item, index)=>{
      const maxDecreaseNum = records.latest - records.onProduce;
      if(records.type === 'product' && records.id === item.id){
        const decreaseNum = Math.abs(item.quantityOnAdjustment - 1);
        if(isIncrease){
          item.quantityOnAdjustment += 1;
        }else{
          if(decreaseNum <= maxDecreaseNum ||                                  //输入框在未出餐范围内的数量
            item.quantityOnAdjustment > item.latest ||                         //输入框数量高于当前菜品数量时输入框的数量
            (item.onProduce === item.latest && item.quantityOnAdjustment > 0)) //所有菜品已出餐完时输入框的数量
            {
              item.quantityOnAdjustment -= 1;
            }
        }
      }else{
        item.children !=null && item.children.map((i, ind)=>{
          if(records.productAccessoryId === i.productAccessoryId){
            const decreaseNum = Math.abs(i.quantityOnAdjustment - 1);
            if(isIncrease){
              i.quantityOnAdjustment += 1;
            }else{
              if(decreaseNum <= maxDecreaseNum || 
              i.quantityOnAdjustment > i.latest || 
              (i.onProduce === i.latest && i.quantityOnAdjustment > 0)) 
              {
                i.quantityOnAdjustment -= 1;
              }
            }
          }
        })
      }
      return item;
    })
    setOrderItemList(newOrderItemList);
  }

  /** 查询订单请求 */
  function queryOrder(tenantId, userId, id){
    const instance = axios.create({
      headers:{tenantId:tenantId, userId:userId}
    });
    instance
      .get(`/order/catering/${id}`)
      .then((res)=>{
        const data = res.data.data;
        const baseList = configData(data.items)
        initialForm(data);
        setOrderItemList(baseList);
        setBaseOrderItemList(baseList);
        console.log(res);
      })
  }
  /** 加退菜请求 */
  function orderAdjustRequest(request, formValues){
    const [tenantId, userId, id] = [formValues.tenantId, formValues.userId, formValues.id];
    const instance = axios.create({
      headers:{tenantId: tenantId, userId:userId}
    });
    instance
      .post(`/order/catering/${id}/produce`, request)
      .then(response=>{
        const code = response.data.status.code;
        if(code===0){
          message.success('操作成功');
          navigate('/order');
        }else{
          message.error(`操作失败，响应码${code}`);
        };
        console.log(response);
      }, error => {
        message.error(`操作失败，响应码${error.response.status}`);
        console.log(error);
      })
  };

  /** 重组订单信息 */
  const configData = (orderValues) => {
    let newOrderItemList = orderValues;
    newOrderItemList.map((item, index)=>{
      let children = [];
      item.accessories.length > 0 && item.accessories.map((i, ind)=>{
        children.push(
          {
            key: ind,
            type: 'accessory',
            productAccessoryId: i.id,
            name: i.productAccessorySnapshotOnPlace.name,
            unitOfMeasure: i.productAccessorySnapshotOnPlace.unitOfMeasure,
            latest: i.quantity.latest,
            onProduce: i.quantity.onProduce,
            version: i.version,
            status: i.status,
            quantityOnAdjustment: 0,
          }
        )
        children[ind].latest > children[ind].onProduce && ([children[ind]['quantityOnProduce'], children[ind]['maxOnProduce']] = [
          children[ind].latest - children[ind].onProduce, children[ind].latest - children[ind].onProduce
        ])
      })
      item['methodName'] = item.productMethodSnapshotOnPlace.name;
      item['latest'] = item.quantity.latest;
      item['onProduce'] = item.quantity.onProduce;
      item['unitOfMeasure'] = item.productSnapshotOnPlace.unitOfMeasure;
      item['name'] = item.productSnapshotOnPlace.name;
      item['type'] = 'product';
      item['children'] = children.length>1?children:null;
      item['quantityOnAdjustment'] = 0;
      return item;
    })
    return newOrderItemList;
  };

  /** 初始化表单 */
  const initialForm = (orderValues) => {
    form.setFieldsValue({
      shopName: orderValues.shopSnapshotOnPlace.name,
      tableNo: orderValues.tableNo,
      customerCount: orderValues.customerCount,
      comment: orderValues.comment,
      id: orderValues.id,
      version: orderValues.version,
      tenantId: orderValues.tenantId,
      userId: orderValues.auditing.createdBy
    })
  }

  /** 生成订单项的序号 */
  const getOrderItemNo = (orderItem, index) => {
    let itemNo = null;
    if(orderItem.type === 'product'){
      itemNo = index + 1;
    }else{
      orderItemList.map((item, index)=>{
        item.children != null && item.children.map((i, ind)=>{
          if(orderItem.productAccessoryId === i.productAccessoryId){
            itemNo = `${index+1}-${ind+1}`;
          }
        })
      })
    }
    return itemNo;
  }

  const onFinish = (formValues) => {
    const requestBody = createRequestBody(formValues);
    console.log('结构体:', requestBody);
    orderAdjustRequest(requestBody, formValues);
  }

  /** 加退菜结构体 */
  const createRequestBody = (formValues) => {
    console.log(orderItemList);
    let newOrderItemList = [];
    orderItemList.map((item, index)=>{
      if(item.status === 'PREPARING'){
        let accessories = [];
        if(item.children !== null){
          item.children.map((i, ind)=>{
            if(i.status === 'PREPARING'){
              accessories.push(
                {
                  productAccessoryId: i.productAccessoryId,
                  quantityOnProduce: i.quantityOnProduce,
                  seqNo: `${index+1}-${ind+1}`,
                  version: i.version,
                }
              )
            }
          })
        }
        item.quantityOnProduce !== undefined && newOrderItemList.push(
          { 
            accessories: accessories,
            seqNo: index+1,
            version: item.version,
            quantityOnProduce: item.quantityOnProduce,
          }
        );
      }
      
    });
    const bodyObj = {
      items: newOrderItemList,
      version: formValues.version
    }
    return bodyObj;
  }
  
  /** 选择菜品(支持多选) */
  const handleProductSelect = (index) => {
    setProductListTitle('新增菜品(可多选/取消菜品)');
    resetProductSearch();
    // setBackCache([].concat(productCache)); //备份取消前的菜品选择缓存
    setProductModalVisible(true);
  }

  /**********************************************************菜品模块******************************************************/

  const handleProductCancel = () => {
    setProductModalVisible(false);
    // setProductCache(backCache);
  }

  /** 确认勾选菜品添加至订单 */
  const handleProductSave = () => {
    let cacheArray = [...checkedProductCache]; //set转数组
    let newCheckedProductCache = JSON.parse(JSON.stringify(cacheArray)) //深拷贝(多层)
    newCheckedProductCache.map((item, index)=>{
      item.children !== null && item.children !== undefined && (item.children = (item.children.filter((i)=> i.checked === true)));
    })
    setOrderItemList([...baseOrderItemList, ...newCheckedProductCache]);
    setProductModalVisible(false);
    setChangeItem(undefined);
    setLastItem(undefined);
  }

  /**重组数据(获取菜品页面时) */
  const reconfigProductList = (resList) => {
    const newCheckedProductCache = new Set(checkedProductCache);
    console.log('当前缓存:', newCheckedProductCache)
    const newBaseList = [].concat(baseOrderItemList);
    resList.map((item, index)=>{
      newBaseList.map((i, ind)=>{
        i.productSnapshotOnPlace.id === item.id && (resList.splice(index, 1));
      })
    })
    const newResList = resList.map((item, index)=>{
      let notExist = true;
      newCheckedProductCache.forEach((cacheItem)=>{
        if(cacheItem.id === item.id){
          [item, notExist] = [cacheItem, false] //将目前菜品列表中存在的菜品替换成缓存中的菜品
          item['key'] = index; //重新给key赋值，否则换页顺序会出问题
        }
      })
      if(notExist){
        item['latest'] = 0;
        item['onProduce'] = 0;
        item['quantityOnAdjustment'] = 1;
        item['key'] = index;
        item['type'] = 'product';
        item['checked'] = false;
        item['isAddProduct'] = true;
        if(item.accessoryGroups.length > 0){
          item['children'] = item.accessoryGroups[0].options;
          item.children.map((i, ind)=>{
            i['latest'] = 0;
            i['onProduce'] = 0;
            i['quantityOnAdjustment'] = 1;
            i['key'] = i.id;
            i['type'] = 'accessory';
            i['checked'] = false;
            item['isAddProduct'] = true;
          })
        }
      }
      return item;
    })
    return newResList;
  }
  
  /** 勾选菜品并缓存勾选信息 */
  const handleCheckbox = (records, checked) => {
    let isChangeProduct = changeItem !== undefined; //是否为更换菜品请求
    const newProductList = [].concat(productList);
    const cacheArray = [...checkedProductCache];
    let newCheckedProductCache = new Set(JSON.parse(JSON.stringify(cacheArray)));

    //避免混淆新增菜品和更换菜品分两次map
    //1.更换菜品时的处理情况
    isChangeProduct && newProductList.map((item, index)=>{
      if(records.type === 'product' && records.id === item.id){
        if(records.id === changeItem.id){
          /*什么都不做*/
        }else{
          if(records.checked){
            if(lastItem === undefined || lastItem.id !== records.id){
              message.error('此菜品已经被选择，请选择其它菜品');
            }
          }else{
            if(lastItem !== undefined){
              newProductList.map((i, ind)=>{
                lastItem.id === i.id && (i.checked = false);
              })
            }
            item.checked = checked;
            newProductList.map((i, ind)=>{
              if(changeItem.id === i.id){
                i.checked = false;
                item.children !== undefined && item.children.map((i, ind)=>{i.checked = false});
              }
            })
            item.children !== undefined && item.children.map((i, ind)=>{i.checked = checked});
            newCheckedProductCache.add(item);
            setLastItem(item);
          }
        }
      }
    })

    //2.新增菜品时的处理情况
    !isChangeProduct && newProductList.map((item, index)=>{
      if(records.type === 'product' && records.id === item.id){
        if(checked){
          item.checked = checked;
          item.children !== undefined && item.children.map((i, ind)=>{i.checked = checked});
          newCheckedProductCache.add(item)
        }else{
          newCheckedProductCache = [...newCheckedProductCache]; //如果是取消勾选则需要先从缓存中删除再改变菜品
          newCheckedProductCache.map((item, index)=>{
            item.id === records.id && newCheckedProductCache.splice(index, 1);
          })
          newCheckedProductCache = new Set(newCheckedProductCache);

          item.checked = checked;
          item.children !== undefined && item.children.map((i, ind)=>{i.checked = checked});
        }
      }else{
        item.children !== undefined && item.children.map((i, ind)=>{
          if(records.id === i.id){
            i.checked = checked; // 勾选&取消此配料
            if(checked){
              item.checked = checked;
              newCheckedProductCache.add(item);
            }else{
              const checkedList = item.children.filter((cacheItem)=>cacheItem.checked === true);
              if(checkedList.length === 0){
                item.checked = checked;
                newCheckedProductCache.delete(item); 
              }
            }
          }
        })
      }
    });

    if(changeItem !== undefined){ //更换菜品时勾选其它菜品删除被更换菜品的缓存
      newCheckedProductCache = [...newCheckedProductCache];
      newCheckedProductCache.map((item, index)=>{
        item.id === changeItem.id && newCheckedProductCache.splice(index, 1);
      })
      newCheckedProductCache = new Set(newCheckedProductCache);
    }
    if(lastItem !== undefined){ //更换菜品时勾选其它菜品删除上个被勾选菜品的缓存
      newCheckedProductCache.delete(lastItem);
    }
    console.log(newCheckedProductCache);
    setCheckedProductCache(newCheckedProductCache);
  }

  /** 菜品模块列 */
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
          return <Cascader options={options} placeholder='请选择做法' onChange={(value)=>handleMethodChange(value, records.key)}/>
        }
      }
    }
  ];
  
  const handleMethodChange = () => {
    
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
            headers: {tenantId: tenantId, userId: userId}
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
            <Col span={23}>加退菜：{form.getFieldValue('id')}</Col>
            <Col>
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
          <Col span={12}>
            <Form.Item label='门店' name='shopName' style={{pointerEvents: "none"}}>
              <Input style={{borderRadius: 0}} readOnly/>
            </Form.Item>
          </Col>
          <Col span={11} offset={1}>
            <Form.Item label='座位号' name='tableNo' style={{pointerEvents: "none"}}>
              <Input readOnly/>
            </Form.Item>
          </Col>
        </Row>
        <Row>
            <Col span={12}>
                <Form.Item label='用餐人数' name='customerCount' style={{pointerEvents: "none"}}>
                  <InputNumber style={{width: '100%'}} readOnly/>
                </Form.Item>
            </Col>
            <Col span={11} offset={1}>
                <Form.Item label='备注' name='comment' style={{pointerEvents: "none"}}>
                  <Input readOnly/>
                </Form.Item>
            </Col>
          </Row>
          <Row className="create-shop-info">
            <Col>菜品信息</Col>
          </Row>
          {/** 骨架屏和表格显示逻辑 */}
          {
            orderItemList!==undefined && orderItemList.length === 0?
                loaddingTime >= 1 ?
                  <Table
                  columns={columns}
                  dataSource={orderItemList}
                  pagination={false}
                  scroll={{y: 320}}
                  />
                  :
                  <Skeleton active paragraph={{ rows: 10 }}/>
                :
                <Table
                columns={columns}
                dataSource={orderItemList}
                pagination={false}
                scroll={{y: 320}}
                />
          }
          <Row className="add-orderItem-btn">
            <Col span={24}>
              <Button style={{width: '100%'}} onClick={handleProductSelect}>添加新菜品</Button>
            </Col>
          </Row>
          <Row className="create-shop-footer">
            <Col span={6} offset={12}>
              <Form.Item>
                <Button style={{background: 'white'}} onClick={()=>setInformationModalVisible(true)}>取消</Button>
              </Form.Item> 
            </Col>
            <Col span={6}>
              <Form.Item>
                <Button htmlType="submit">确认</Button>
              </Form.Item> 
            </Col>
          </Row>

          <Form.Item name='id' hidden></Form.Item>
          <Form.Item name='userId'hidden></Form.Item>
          <Form.Item name='version' hidden></Form.Item>
          <Form.Item name='tenantId' hidden></Form.Item>
          </Form>

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
          title='确认丢失修改的内容'
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