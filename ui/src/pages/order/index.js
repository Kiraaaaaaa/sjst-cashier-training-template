import { Button, Form, Row, Col, Select, Input, Table, Tag, InputNumber, notification } from "antd";
import { LeftOutlined, RightOutlined, DoubleLeftOutlined, DoubleRightOutlined, UnorderedListOutlined } from "@ant-design/icons"
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
const { Option } = Select;

const DEFUALT_PAGE_INDEX = 1;
const DEFAULT_PAGE_SIZE = 10;
const USER_INFO = {tenantId:500, userId:11000};
const DEFULT_PAGE_TOTALCOUNT = [1, 2, 3, 4, 5];
const ORDER_STATUS = new Map([
    ['PLACED', '已下单'], ['PREPARING', '制作中'],
    ['PREPARED', '已出餐'], ['BILLED', '已结账'], ['CANCELLED', '已取消']
]);

const testData = [
    {
        status: 'PLACED',
        shopName: '门店1',
        tableNo: 'A08',
        customerCount: 2,
        totalPrice: 86.6,
    }
]
export default function Order(){

    let navigate = useNavigate();
    const [form] = Form.useForm();
    const [pageSize, setPageSize] = useState(DEFAULT_PAGE_SIZE);
    const [pageIndex, setPageIndex] = useState(DEFUALT_PAGE_INDEX);
    const [isSearchBtn, setIsSearchBtn] = useState(false);
    const [productList, setProductList] = useState([]);
    const [pageBtnGroup, setPageNum] = useState(DEFULT_PAGE_TOTALCOUNT);
    const [totalPageCount, setTotalPageCount] = useState();
    const columns = [
        {
            title: '状态',
            dataIndex: 'status',
            key: 'status',
            sorter: (a, b) => a.status - b.status,
            render: status => 
            <Tag style={{fontSize: 15}} >{ORDER_STATUS.get(status)}</Tag>,
        },
        {
            title: '门店',
            dataIndex: 'shopName',
            key: 'shopName',
            sorter: (a, b) => a.shopName.localeCompare(b.shopName),
        },
        {
            title: '座位号',
            dataIndex: 'tableNo',
            key: 'tableNo',
            sorter: (a, b) => a.tableNo - b.tableNo,
        },
        {
            title: '就餐人数',
            dataIndex: 'customerCount',
            key: 'customerCount',
            sorter: (a, b) => a.customerCount - b.customerCount,
        },
        {
            title: '订单总价(元)',
            dataIndex: 'totalPrice',
            key: 'totalPrice',
            sorter: (a, b) => a.totalPrice - b.totalPrice,
        },
        {
            title: '操作',
            render: text => <div style={{margin: -15}}>{getColumnOptions(text)}</div>,
        },
    ];
    
    const getColumnOptions = (orderItem) => {
        switch (orderItem.status) {
            case 'PLACED':
                return (
                    <>
                    <Button type="link" onClick={()=>navigate('/prepare', {state: {data: orderItem}, replace: true})}>
                        制作
                    </Button>
                    <span style={{color: 'lightgray'}}>|</span>
                    <Button type="link" onClick={()=>navigate('/adjust', {state: {data: orderItem}, replace: true})}>
                        加退菜
                    </Button>
                    </>
                );
            case 'PREPARING':
                return (
                    <>
                    <Button type="link" onClick={()=>navigate('/prepare', {state: {data: orderItem}, replace: true})}>
                        制作
                    </Button>
                    <span style={{color: 'lightgray'}}>|</span>
                    <Button type="link" onClick={()=>navigate('/produce', {state: {data: orderItem}, replace: true})}>
                        出餐
                    </Button>
                    <span style={{color: 'lightgray'}}>|</span>
                    <Button type="link" onClick={()=>navigate('/adjust', {state: {data: orderItem}, replace: true})}>
                        加退菜
                    </Button>
                    </>
                );
            case 'PREPARED':
                return (
                    <>
                    <Button type="link" onClick={()=>navigate('/adjust', {state: {data: orderItem}, replace: true})}>
                        加退菜
                    </Button>
                    <span style={{color: 'lightgray'}}>|</span>
                    <Button type="link" onClick={()=>navigate('/prepare', {state: {data: orderItem}, replace: true})}>
                        结账
                    </Button>   
                    </>
                );
            case 'BILLED':
                return (
                    <Button type="link" onClick={()=>navigate('/orderCheck', {state: {data: orderItem}, replace: true})}>
                        查看
                    </Button> 
                );
            case 'CANCELLED':
                return (
                    <Button type="link" onClick={()=>navigate('/orderCheck', {state: {data: orderItem}, replace: true})}>
                        查看
                    </Button>
                )
            default:
                return (
                    <span>未知订单状态</span>
                )
        }
    }
    useEffect(() => {
        resetSearch();
    }, []);

     /** 初始化表单 */
     const initialSearchForm = () => {
        form.setFieldsValue({
            status: null,
            minPrice: '',
            maxPrice: '',
        })
    }

    /** 别处触发表单提交 */
    const submitForm = () => {
        form.submit();
    }

    /** 表单提交 */
    const onSearchFinish = searchValues => {
        console.log(pageIndex, pageSize);
        createPageRequest(searchValues);
    }

    /** 手动搜索结果 */
    const onSearch = () => {
        setIsSearchBtn(true);
        setPageIndex(DEFUALT_PAGE_INDEX);
        submitForm();
    }

    /** 重置搜索 */
    const resetSearch = () => {
        setIsSearchBtn(true);
        setPageIndex(DEFUALT_PAGE_INDEX);
        initialSearchForm();
        submitForm();
    }

    /** 接口request结构体 */
    const createRequestBody = (searchValues, pageIndex, pageSize) => {
        const [tableNo, customerCount, minPrice, maxPrice, status] = [
            searchValues.tableNo,
            searchValues.customerCount,
            searchValues.minPrice, 
            searchValues.maxPrice,
            searchValues.status, 
        ];
        const requestBody = {
            "condition": {
                "customerCount": customerCount,
                "status": status,
                "tableNo": tableNo,
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
                  "field": "status"
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
        instance
            .post('/order/catering/search', request)
            .then(res => {
                console.log(res);
                const [data, totalPageCount, totalCount] = res.data.data === null ? [[], DEFULT_PAGE_TOTALCOUNT, 0] : 
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
                setProductList(data);
            }, error => {
                console.log(error);
            })
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
    const lastPageBtn = () =>{
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
                <Col offset={10}>订单管理</Col>
                <Col offset={8} span={3}>
                    <Button onClick={()=>navigate('/createOrder')}>
                        创建新订单
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
        colon={false}
        >
            <Row>
                <Col span={8}>
                    <Form.Item label="状态" name="status">
                        <Select>
                            <Option value={null}>所有</Option>
                            <Option value="PLACED">已下单</Option>
                            <Option value="PREPARING">制作中 </Option>
                            <Option value="PREPARED">已出餐</Option>
                            <Option value="BILLED">已完成</Option>
                            <Option value="CANCELLED">已取消</Option>
                        </Select>
                    </Form.Item> 
                </Col>
                <Col span={8} offset={1}>
                    <Form.Item 
                    label="座位号"
                     name="tableNo"
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
                <Col span={6} offset={1}>
                    <Form.Item 
                    label="就餐人数 " 
                    name="customerCount"
                    rules={[
                        {
                          validator(_, value) {
                            if (!value||(100 > value && value >= 1)) {
                              return Promise.resolve();
                            }
                            return Promise.reject(new Error('最多两位数字!'));
                          },
                        },
                      ]}
                     >
                        <InputNumber min={0} style={{width: '100%'}}/>
                    </Form.Item> 
                </Col>
            </Row>
            <Row className="create-shop-btn">
                <Col span={12} style={{height: 0}}>
                    <Form.Item label="订单总价">
                        <Input.Group style={{width: '100%'}}>
                            <Row>
                                <Col span={9}>
                                    <Form.Item 
                                    name='minPrice'
                                    rules={[
                                        {
                                          validator(_, value) {
                                            if (!value||(1000 >= value && value >= 0)) {
                                              return Promise.resolve();
                                            }
                                            return Promise.reject(new Error('价格区间在0到1000之间!'));
                                          },
                                        },
                                    ]}
                                    >
                                        <InputNumber 
                                        min={0} 
                                        precision={0} 
                                        style={{
                                            width: '100%', 
                                            borderRight: 0, 
                                            borderRadius: 0
                                        }}
                                        />
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
                                    <Form.Item 
                                    name='maxPrice'
                                    rules={[
                                        {
                                          validator(_, value) {
                                            if (!value||(1000 >= value && value >= 0)) {
                                              return Promise.resolve();
                                            }
                                            return Promise.reject(new Error('价格区间在0到1000之间!'));
                                          },
                                        },
                                    ]}
                                    >
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
                <Col span={4} offset={1}>
                    <Form.Item>
                        <Button style={{width:'100%'}} onClick={onSearch}>查询</Button>
                    </Form.Item>
                </Col>
                <Col span={4} offset={1}>
                    <Form.Item>
                        <Button style={{width: '100%', backgroundColor: 'white'}}  onClick={resetSearch}>重置</Button>
                    </Form.Item>
                </Col>
            </Row>
        </Form>
        <Table
            columns={columns}
            dataSource={testData}
            pagination={false}
            scroll={{y: 320}}
            
        />
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
    );
}