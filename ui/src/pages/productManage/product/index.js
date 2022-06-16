import { Button, Form, Row, Col, Select, Input, Table, Tag, message, Popconfirm, InputNumber, notification, Skeleton } from "antd";
import { LeftOutlined, RightOutlined, DoubleLeftOutlined, DoubleRightOutlined, UnorderedListOutlined } from "@ant-design/icons"
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
const { Option } = Select;

const DEFUALT_PAGE_INDEX = 1;
const DEFAULT_PAGE_SIZE = 10;
const USER_INFO = {tenantId:500, userId:11000};
const DEFULT_PAGE_TOTALCOUNT = [1, 2, 3, 4, 5];
const ENABLED = new Map([[true, '已上架'], [false, '已下架']]);
const PRODUCT_STATUS = new Map([[true, '下架'], [false, '上架']]);
const CURRENCY_UNIT = '元'; //统一价格单位;


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

export default function Product(){

    let navigate = useNavigate();
    const [form] = Form.useForm();
    const [pageSize, setPageSize] = useState(DEFAULT_PAGE_SIZE);
    const [pageIndex, setPageIndex] = useState(DEFUALT_PAGE_INDEX);
    const [loaddingTime, resetTime] = useCounter();
    const [isSearchBtn, setIsSearchBtn] = useState(false);
    const [productList, setProductList] = useState([]);
    const [pageBtnGroup, setPageNum] = useState(DEFULT_PAGE_TOTALCOUNT);
    const [totalPageCount, setTotalPageCount] = useState();

    const columns = [
        {
            title: '状态',
            dataIndex: 'enabled',
            key: 'enabled',
            sorter: (a, b) => a.enabled - b.enabled,
            filterSearch: true,
            filters: [
                {
                    text: '已上架',
                    value: true,
                },
                {
                    text: '已下架',
                    value: false,
                }
            ],
            onFilter: (value, record) => record.enabled === value,
            render: enabled => 
            <Tag style={{fontSize: 15}} color={enabled ? 'green': 'red'}>{ENABLED.get(enabled)}</Tag>,
        },
        {
            title: '菜品名',
            dataIndex: 'name',
            key: 'name',
            sorter: (a, b) => a.name.localeCompare(b.name),
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
            render: unitOfMeasure => (unitOfMeasure.split('/')[1])
        },
        {
            title: '操作',
            render: text => (
                <div style={{margin: -15}}>
                    <Button type="link" onClick={()=>navigate('/updateProduct', {state: {data: text}, replace: true})}>
                        编辑
                    </Button>
                    <span style={{color: 'lightgray'}}>|</span>
                    <Popconfirm
                    title={`确定${PRODUCT_STATUS.get(text.enabled)}此菜品?`}
                    onConfirm={confirm.bind(this, text)}
                    onCancel={cancel.bind(this, text)}
                    okText="确定"
                    cancelText="取消"
                    >
                    <Button type="link">
                        {PRODUCT_STATUS.get(text.enabled)}
                    </Button>
                    </Popconfirm>                 
                </div>
            ),
        },
    ];

    useEffect(() => {
        resetSearch();
    }, []);

     /** 初始化表单 */
     const initialSearchForm = () => {
        form.setFieldsValue({
            enabled: null,
            minPrice: '',
            maxPrice: '',
            unitOfMeasure: '',
            name: '',
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
        const [unitOfMeasure, minPrice, maxPrice, enabled, name] = [
            searchValues.unitOfMeasure.length === 0 ? "" : CURRENCY_UNIT+'/'+searchValues.unitOfMeasure, 
            searchValues.minPrice, 
            searchValues.maxPrice,
            searchValues.enabled, 
            searchValues.name
        ];
        const requestBody = {
            "condition": {
                "enabled": enabled,
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
        instance
            .post('/product/search', request)
            .then(res => {
                console.log(res);
                const [data, totalPageCount, totalCount] = [
                    res.data.data.records, 
                    res.data.data.totalPageCount === 0 ? 1 : res.data.data.totalPageCount, 
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

    /** 菜品状态更新 */
    const updateStatusState = productItem =>{
        const newProductList = productList.map(item => {
            if(item.id === productItem.id){
                item.enabled = item.enabled?0:1;
                item.version += 1;
            }
            return item;
        })
        setProductList(newProductList);
        submitForm();
        message.success(`${productItem.enabled?'上架':'下架'}菜品成功`);
    }

    /** 创建菜品上/下架request请求 */
    const creatAxiosRequest = (productItem, headerValues) => {
        const [id, status, version] = [productItem.id, productItem.enabled, {version: productItem.version}];
        const instance = axios.create({
          headers:{tenantId: headerValues.tenantId, userId: headerValues.userId}
        });
        instance
          .post(`/product/${id}/${status?'disable':'enable'}`, version)
          .then(response => {
              const code = response.data.status.code;
              code===0?updateStatusState(productItem):message.error(`${status?'下架':'上架'}菜品失败，响应码${code}`);
              submitForm();
              console.log(response);
          }, error => {
            console.log(error);
          })
      }

    const confirm = productItem =>{
        creatAxiosRequest(productItem, USER_INFO);
    }

    const cancel = productItem =>{
        message.error(`取消${productItem.enabled?'下架':'上架'}菜品`);
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
                <Col offset={10}>菜品管理</Col>
                <Col offset={8} span={3}>
                    <Button onClick={()=>navigate('/createProduct')}>
                        创建新菜品
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
                    <Form.Item label="状态" name="enabled">
                        <Select>
                            <Option value={null}>所有</Option>
                            <Option value={1}>已上架</Option>
                            <Option value={0}>已下架 </Option>
                        </Select>
                    </Form.Item> 
                </Col>
                <Col span={8} offset={1} style={{height: 0}}>
                    <Form.Item label="菜品单价">
                        <Input.Group style={{width: '100%'}}>
                            <Row>
                                <Col span={7}>
                                    <Form.Item name='minPrice'>
                                        <InputNumber min={0} precision={0} style={{width: '100%', borderRight: 0, borderRadius: 0}}/>
                                    </Form.Item>
                                </Col>
                                <Col span={4}>
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
                                <Col span={7}>
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
                                <Col span={6}>
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
                <Col span={6} offset={1}>
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
                    name="name"
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
                        <Input onPressEnter={onSearch}/>
                    </Form.Item>
                </Col>
                <Col span={4} offset={1}>
                    <Form.Item>
                        <Button style={{width:'100%'}} onClick={onSearch} >查询</Button>
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
            productList.length === 0 ?
                loaddingTime >= 1 ? 
                    <Table
                    columns={columns}
                    dataSource={productList}
                    pagination={false}
                    scroll={{y: 320}}
                    /> 
                    :
                    <Skeleton active paragraph={{ rows: 10 }}/>
                :
                <Table
                columns={columns}
                dataSource={productList}
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
    );
}