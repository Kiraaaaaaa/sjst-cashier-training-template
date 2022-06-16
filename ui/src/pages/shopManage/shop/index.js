import { Button, Form, Row, Col, Select, Input, Table, Tag, message, Popconfirm, notification, Skeleton } from "antd";
import { LeftOutlined, RightOutlined, DoubleLeftOutlined, DoubleRightOutlined, UnorderedListOutlined} from "@ant-design/icons";
import "../../../css/shop.css";
import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const { Option } = Select

const DEFUALT_PAGE_INDEX = 1;
const DEFAULT_PAGE_SIZE = 10;
const USER_INFO = {tenantId:500, userId:11000};
const DEFULT_PAGE_TOTALCOUNT = [1, 2, 3, 4, 5];
const BUSINESS_TYPE = new Map([
    ['DINNER', '正餐'], ['FAST_FOOD', '快餐'], 
    ['HOT_POT', '火锅'], ['BARBECUE', '烧烤'], 
    ['WESTERN_FOOD', '西餐'],
]);
const MANAGEMENT_TYPE = new Map([['DIRECT_SALES', '直营'], ['ALLIANCE', '加盟']]);

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
export default function Shop(){

    let navigate = useNavigate();
    const [form] = Form.useForm();
    const [pageSize, setPageSize] = useState(DEFAULT_PAGE_SIZE);
    const [shopList, setShopList] = useState([]);
    const [isSearchBtn, setIsSearchBtn] = useState(false);
    const [pageIndex, setPageIndex] = useState(DEFUALT_PAGE_INDEX);
    const [loaddingTime, resetTime] = useCounter();
    const [pageBtnGroup, setPageNum] = useState(DEFULT_PAGE_TOTALCOUNT);
    const [totalPageCount, setTotalPageCount] = useState();

    const columns = [
        {
            title: '营业状态',
            dataIndex: 'enabled',
            key: 'enabled',
            sorter: (a, b) => a.enabled - b.enabled,
            filterSearch: true,
            filters: [
                {
                    text: '营业中',
                    value: true,
                },
                {
                    text: '停业中',
                    value: false,
                }
            ],
            onFilter: (value, record) => record.enabled === value,
            render: enabled => <Tag style={{fontSize: 15}} color={enabled ? 'green': 'red'}>{enabled ? "营业中" : "停业中"}</Tag>,
        },
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
        },
        {
            title: '操作',
            dataIndex: '',
            key: 'action',
            render: text => (
                <div style={{margin: -15}}>
                    <Button type="link" onClick={()=>navigate('/updateShop', {state: {data: text}, replace: true})}>
                        编辑
                    </Button>
                    <span style={{color: 'lightgray'}}>|</span>
                    <Popconfirm
                    title={`确定${text.enabled ? '停用' : '启用'}此门店?`}
                    onConfirm={confirm.bind(this, text)}
                    onCancel={cancel.bind(this, text)}
                    okText="确定"
                    cancelText="取消"
                    >
                    <Button type="link">
                        {text.enabled ? '停用' : '启用'}
                    </Button>
                    </Popconfirm>                 
                </div>
            ),
        },
    ];

    useEffect(() => {
        resetSearch();
    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    /** 别处触发表单提交 */
    const submitForm = () => {
        form.submit();
    }

    /** 表单提交 */
    const onSearchFinish = searchValues => {
        createPageRequest(searchValues);
    }

    /** 手动搜索结果 */
    const onSearch = () => {
        resetTime();
        setIsSearchBtn(true);
        setPageIndex(DEFUALT_PAGE_INDEX);
        submitForm();
    }
    
    /** 重置搜索 */
    const resetSearch = () => {
        resetTime();
        setIsSearchBtn(true);
        setPageIndex(DEFUALT_PAGE_INDEX);
        initialSearchForm();
        submitForm();
    }

    /** 接口request结构体 */
    const createRequestBody = (searchValues, pageIndex, pageSize) => {
        const [businessType, managementType, enabled, keyword] = [
            searchValues.businessType, 
            searchValues.managementType, 
            searchValues.enabled, 
            searchValues.name
        ];
        const requestBody = {
            "condition": {
                "businessTypes": businessType === null ? null : [businessType],
                "enabled": enabled,
                "keyword": keyword,
                "managementTypes": managementType === null ? null : [managementType]
              },
              "pageIndex": pageIndex,
              "pageSize": pageSize,
              "sortFields": [
                {
                  "asc": false,
                  "field": "enabled"
                },
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

    /** 搜索结果提示 */
    const openNotification = (placement, totalpageCount) => {
        notification.info({
          message: `一共${totalpageCount}条结果`,
          placement,
          duration: 2,
        });
    };
    
    /** 创建搜索request请求 */
    const createPageRequest = searchValues => { 
        const request = createRequestBody(searchValues, pageIndex, pageSize);
        const instance = axios.create({
            headers: {tenantId: USER_INFO.tenantId, userId: USER_INFO.userId}
        });
        console.log(request);
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
                setTotalPageCount(totalPageCount);
                setTotalCountBtn(totalPageCount);   
                setIsSearchBtn(false);
                setShopList(data);
                
            }, error => {
                console.log(error);
            })
    }

    /** 初始化表单 */
    const initialSearchForm = () => {
        form.setFieldsValue({
            enabled: null,
            businessType: null,
            managementType: null,
            name: '',
        })
    }

    /** 门店营业状态更新 */
    const updateStatusState = shopItem =>{
        const newShopList = shopList.map(item => {
            if(item.id === shopItem.id){
                item.enabled = item.enabled?0:1;
                item.version += 1;
            }
            return item;
        })
        setShopList(newShopList);
        submitForm();
        message.success(`${shopItem.enabled?'启用':'停用'}门店成功`);
    }

    /** 创建门店停/启用request请求 */
    const creatAxiosRequest = (shopItem, headerValues) => {
        const [businessNo, status, version] = [shopItem.businessNo, shopItem.enabled, {version: shopItem.version}];
        const instance = axios.create({
          headers:{tenantId: headerValues.tenantId, userId: headerValues.userId}
        });
        instance
          .post(`/shop/${businessNo}/${status?'close':'open'}`, version)
          .then(response => {
              const code = response.data.status.code;
              code===0?updateStatusState(shopItem):message.error(`${status?'停用':'启用'}门店失败，响应码${code}`);
              submitForm();
              console.log(response);
          }, error => {
            console.log(error);
          })
      }

    const confirm = shopItem =>{
        creatAxiosRequest(shopItem, USER_INFO);
    }

    const cancel = shopItem =>{
        message.error(`取消${shopItem.enabled?'停用':'启用'}门店`);
    }
    

    /** 自定义分页组件 */
    function PaginationBtn(){
        return (
            <>
                <DoubleLeftOutlined onClick={firstPageBtn}/>
                <Button onClick={prePageBtn} type="text"><LeftOutlined/></Button>
                {
                    pageBtnGroup.map(index=>{
                        return (
                            <Button type={pageIndex === index ? "primary" : "text"} key={index} onClick={pageChange.bind(this, index)}>{index}</Button>
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
        if(pageIndex < 4 && totalPageCount >=5){
            for(let i=1; i<=5; ++i){
                pageBtn.push(i);
            }
        }
        /* 第5页开始动态暂展示轮播效果 */
        else if(totalPageCount>=5){
            let [pageStart, pageEnd, ] = [pageIndex-2, pageIndex+2];
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
        setPageNum(pageBtn);
    }

    const pageChange = (pageIndex) => {
        setPageIndex(pageIndex);
        submitForm();
    }
    const optionChange = btnSize => {
        const totalCountForNow = (pageIndex - 1) * pageSize;
        /** 
         * 如果当前页数之前的数据总和小于分页条数，当前页码则为1
         * 如果当前页数之前的数据总和等于或者大于分页条数，当前页码则为此页码第一条数据按照分页计算后所在的页码
         */
        if(totalCountForNow < btnSize || pageIndex === 1){
            setPageIndex(DEFUALT_PAGE_INDEX);
        }
        else if(totalCountForNow === btnSize){
            setPageIndex(totalCountForNow / btnSize);
        }else{
            setPageIndex(totalCountForNow / btnSize + 1);
        }
        setPageSize(btnSize);
        submitForm();
    }
    const lastPageBtn= () =>{
        if(pageIndex!==totalPageCount){
            setPageIndex(pageIndex+1);
            submitForm();
        }
    }
    const prePageBtn = () => {
        if(pageIndex!==DEFUALT_PAGE_INDEX){
            setPageIndex(pageIndex-1);
            submitForm();
        }
    }
    const firstPageBtn = () => {
        if(pageIndex!==DEFUALT_PAGE_INDEX){
            setPageIndex(DEFUALT_PAGE_INDEX);
            submitForm();
        }
    }
    const endPageBtn = () => {
        if(pageIndex!==totalPageCount){
            setPageIndex(totalPageCount);
            submitForm();
        }
    }
    /** */

    return (
        <div>
            <div className="shop-head">
                <Row className="create-shop-btn">
                    <Col><LeftOutlined onClick={()=>navigate('/')}/></Col>
                    <Col offset={10}>门店管理</Col>
                    <Col offset={8} span={3}>
                        <Button onClick={()=>navigate('/createShop')}>
                            创建新门店
                        </Button>
                    </Col>
                    <Col>
                        <UnorderedListOutlined />
                    </Col>
                </Row>
            </div>
            <Form
            form={form}
            onFinish={onSearchFinish}
            style={{marginTop: 20}}
            >
                <Row>
                    <Col span={7}>
                        <Form.Item label="营业状态" name="enabled">
                            <Select>
                                <Option value={null}>所有</Option>
                                <Option value={1}>营业中</Option>
                                <Option value={0}>停业中 </Option>
                            </Select>
                        </Form.Item> 
                    </Col>
                    <Col span={7} offset={1}>
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
                    <Col span={8} offset={1}>
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
                        <Form.Item label="门店名" name={"name"}>
                            <Input onPressEnter={onSearch}/>
                        </Form.Item>
                    </Col>
                    <Col span={4} offset={1}>
                        <Form.Item>
                            <Button style={{width:'100%'}} onClick={onSearch}>搜索</Button>
                        </Form.Item>
                    </Col>
                    <Col span={4} offset={1}>
                        <Form.Item>
                            <Button style={{width: '100%', backgroundColor: 'white'}}  onClick={resetSearch}>重置</Button>
                        </Form.Item>
                    </Col>
                </Row>
            </Form>

            {/** 骨架屏和表格显示逻辑 */}
            {
                shopList.length === 0?
                    loaddingTime >= 1 ?
                        <Table
                        columns={columns}
                        dataSource={shopList}
                        pagination={false}
                        scroll={{y: 320}}
                        />
                        :
                        <Skeleton active paragraph={{ rows: 10 }}/>
                    :
                    <Table
                    columns={columns}
                    dataSource={shopList}
                    pagination={false}
                    scroll={{y: 320}}
                    />
            }
            <Row style={{marginTop: '1%'}}>  
                <Col span={8} offset={8}>
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
    )
}