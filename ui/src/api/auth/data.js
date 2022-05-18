export const auths = [
  {
    id: 'device-simulator',
    name: 'dsad',
    describe: '',
    status: 0,
    actions: [
      {
        action: 'stop',
        name: '停止',
        describe: '停止',
        properties: {
          supportDataAccessTypes: [],
        },
      },
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
      {
        action: 'start',
        name: '启动',
        describe: '启动',
        properties: {
          supportDataAccessTypes: [],
        },
      },
      {
        action: 'save',
        name: '保存',
        describe: '保存',
        properties: {
          supportDataAccessTypes: [],
        },
      },
      {
        action: 'delete',
        name: '删除',
        describe: '删除',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
    optionalFields: [
      {
        name: '测试',
        describe: '点都德',
      },
      {
        name: 'cli',
        describe: '收到收到收到',
      },
    ],
    parents: [
      {
        permission: 'device-simulator',
        preActions: ['query', 'save', 'delete'],
        actions: ['query', 'save'],
      },
    ],
    properties: {
      type: ['system', 'default'],
    },
  },
  {
    id: 'vis-configuration',
    name: '组态管理',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询列表',
        describe: '查询列表',
      },
      {
        action: 'get',
        name: '查询明细',
        describe: '查询明细',
      },
    ],
    optionalFields: [],
    parents: [],
    properties: {},
  },
  {
    id: 'message-gateway',
    name: '消息网关',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'subscribe',
        name: '订阅消息',
        describe: '订阅消息',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
  },
  {
    id: 'ceshi',
    name: '测试',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询列表',
        describe: '查询列表',
      },
      {
        action: 'get',
        name: '查询明细',
        describe: '查询明细',
      },
      {
        action: 'add',
        name: '新增',
        describe: '新增',
      },
      {
        action: 'update',
        name: '修改',
        describe: '修改',
      },
      {
        action: 'delete',
        name: '删除',
        describe: '删除',
      },
      {
        action: 'import',
        name: '导入',
        describe: '导入',
      },
      {
        action: 'export',
        name: '导出',
        describe: '导出',
      },
    ],
    optionalFields: [],
    parents: [],
    properties: {
      type: ['api'],
    },
  },
  {
    id: 'protocol-supports',
    name: '协议管理',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询列表',
        describe: '查询列表',
      },
    ],
    optionalFields: [],
    parents: [],
    properties: {},
  },
  {
    id: 'network-simulator',
    name: '网络模拟器',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
  },
  {
    id: 'certificate',
    name: '证书管理',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
    optionalFields: [
      {
        name: 'description',
        describe: '描述',
      },
      {
        name: 'name',
        describe: '名称',
      },
      {
        name: 'instance',
        describe: '类型',
      },
      {
        name: 'configs',
        describe: '证书详情',
      },
    ],
  },
  {
    id: 'dueros-product',
    name: '小度产品管理',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
  },
  {
    id: 'device-product',
    name: 'device-product',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询列表',
        describe: '查询列表',
      },
    ],
    optionalFields: [],
    parents: [],
    properties: {
      type: ['business'],
    },
  },
  {
    id: 'device-group',
    name: '设备分组',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
    parents: [],
    properties: {},
  },
  {
    id: 'onenet-product',
    name: 'OneNet产品管理',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'enable',
        name: '启用',
        describe: '启用',
        properties: {
          supportDataAccessTypes: [],
        },
      },
      {
        action: 'disable',
        name: '禁用',
        describe: '禁用',
        properties: {
          supportDataAccessTypes: [],
        },
      },
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
      {
        action: 'save',
        name: '保存',
        describe: '保存',
        properties: {
          supportDataAccessTypes: [],
        },
      },
      {
        action: 'delete',
        name: '删除',
        describe: '删除',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
  },
  {
    id: 'rule-model',
    name: '规则引擎-模型',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
    optionalFields: [
      {
        name: 'description',
      },
      {
        name: 'type',
      },
      {
        name: 'name',
      },
      {
        name: 'modelType',
      },
      {
        name: 'version',
      },
      {
        name: 'modifyTime',
      },
      {
        name: 'modifierId',
      },
      {
        name: 'createTime',
      },
      {
        name: 'modelMeta',
      },
      {
        name: 'creatorId',
      },
    ],
  },
  {
    id: 'device-firmware-manager',
    name: '设备固件信息管理',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
    optionalFields: [
      {
        name: 'deviceName',
      },
      {
        name: 'properties',
      },
      {
        name: 'version',
      },
      {
        name: 'createTime',
      },
      {
        name: 'productId',
      },
      {
        name: 'updateTime',
      },
    ],
    parents: [],
    properties: {},
  },
  {
    id: 'device-alarm',
    name: '设备告警',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
    optionalFields: [
      {
        name: 'deviceId',
        describe: '设备ID',
      },
      {
        name: 'deviceName',
        describe: '设备名称',
      },
      {
        name: 'alarmTime',
        describe: '告警时间',
      },
      {
        name: 'productName',
        describe: '产品名称',
      },
      {
        name: 'alarmName',
        describe: '告警名称',
      },
      {
        name: 'description',
        describe: '说明',
      },
      {
        name: 'alarmId',
        describe: '告警ID',
      },
      {
        name: 'alarmData',
        describe: '告警数据',
      },
      {
        name: 'state',
        describe: '状态',
      },
      {
        name: 'productId',
        describe: '产品ID',
      },
      {
        name: 'updateTime',
        describe: '修改时间',
      },
    ],
    parents: [],
    properties: {
      type: ['tenant'],
    },
  },
  {
    id: 'edge-product',
    name: '边缘网关产品管理',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
      {
        action: 'save',
        name: '保存',
        describe: '保存',
        properties: {
          supportDataAccessTypes: [],
        },
      },
      {
        action: 'delete',
        name: '删除',
        describe: '删除',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
    optionalFields: [
      {
        name: 'manufacturer',
        describe: '厂家',
      },
      {
        name: 'description',
        describe: '说明',
      },
      {
        name: 'classifiedId',
        describe: '所属品类ID',
      },
      {
        name: 'photoUrl',
        describe: '图标地址',
      },
      {
        name: 'name',
        describe: '名称',
      },
      {
        name: 'classifiedName',
        describe: '所属品类名称',
      },
      {
        name: 'version',
        describe: '版本',
      },
      {
        name: 'model',
        describe: '型号',
      },
      {
        name: 'createTime',
        describe: '创建时间',
      },
    ],
  },
  {
    id: 'menu',
    name: '菜单管理',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
    optionalFields: [
      {
        name: 'status',
        describe: '状态',
      },
      {
        name: 'path',
        describe: '树路径',
      },
      {
        name: 'name',
        describe: '菜单名称',
      },
      {
        name: 'icon',
        describe: '图标',
      },
      {
        name: 'describe',
        describe: '描述',
      },
      {
        name: 'url',
        describe: '菜单对应的url',
      },
      {
        name: 'parentId',
        describe: '父级ID',
      },
      {
        name: 'sortIndex',
        describe: '排序序号',
      },
      {
        name: 'level',
        describe: '树层级',
      },
      {
        name: 'permissionExpression',
        describe: '权限表达式',
      },
    ],
  },
  {
    id: 'opc-point',
    name: 'OPC-UA设备点位',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
      {
        action: 'save',
        name: '保存',
        describe: '保存',
        properties: {
          supportDataAccessTypes: [],
        },
      },
      {
        action: 'delete',
        name: '删除',
        describe: '删除',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
  },
  {
    id: 'firmware-manager',
    name: '固件管理',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
    optionalFields: [
      {
        name: 'versionOrder',
        describe: '版本序号',
      },
      {
        name: 'productName',
        describe: '产品名称',
      },
      {
        name: 'description',
        describe: '说明',
      },
      {
        name: 'properties',
        describe: '其他拓展信息',
      },
      {
        name: 'name',
        describe: '固件名称',
      },
      {
        name: 'version',
        describe: '版本号',
      },
      {
        name: 'createTime',
        describe: '创建时间(只读)',
      },
      {
        name: 'signMethod',
        describe: '固件文件签名方式,如:MD5,SHA256',
      },
      {
        name: 'size',
        describe: '固件文件大小',
      },
      {
        name: 'productId',
        describe: '产品ID',
      },
      {
        name: 'url',
        describe: '固件文件地址',
      },
      {
        name: 'sign',
        describe: '固件文件签名',
      },
    ],
  },
  {
    id: 'permission',
    name: '权限管理',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
      {
        action: 'save',
        name: '保存',
        describe: '保存',
        properties: {
          supportDataAccessTypes: [],
        },
      },
      {
        action: 'grant',
        name: '赋权',
        describe: '赋权',
        properties: {
          supportDataAccessTypes: [],
        },
      },
      {
        action: 'delete',
        name: '删除',
        describe: '删除',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
    optionalFields: [
      {
        name: 'status',
        describe: '状态',
      },
      {
        name: 'optionalFields',
        describe: '可操作的字段',
      },
      {
        name: 'parents',
        describe: '关联权限',
      },
      {
        name: 'properties',
        describe: '其他配置',
      },
      {
        name: 'name',
        describe: '权限名称',
      },
      {
        name: 'describe',
        describe: '说明',
      },
      {
        name: 'actions',
        describe: '可选操作',
      },
    ],
    parents: [],
    properties: {
      type: ['tenant', 'api'],
    },
  },
  {
    id: 'device-opt-api',
    name: '设备操作API',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'read-property',
        name: '读取属性',
        describe: '读取属性',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
    parents: [],
    properties: {
      type: ['api'],
    },
  },
  {
    id: 'device-gateway',
    name: '设备网关',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
    optionalFields: [
      {
        name: 'configuration',
        describe: '其他配置',
      },
      {
        name: 'name',
        describe: '名称',
      },
      {
        name: 'state',
      },
      {
        name: 'describe',
        describe: '描述',
      },
      {
        name: 'networkId',
        describe: '网络组件id',
      },
      {
        name: 'provider',
        describe: '类型',
      },
    ],
  },
  {
    id: 'big-screen',
    name: '大屏管理',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询',
        describe: '查询',
      },
    ],
    optionalFields: [],
    parents: [
      {
        permission: 'visualization',
        preActions: ['query'],
        actions: ['query'],
      },
      {
        permission: 'visualization',
        preActions: ['save'],
        actions: ['save'],
      },
      {
        permission: 'visualization',
        preActions: ['delete'],
        actions: ['delete'],
      },
    ],
    properties: {
      type: ['api'],
    },
  },
  {
    id: 'open-api',
    name: 'openApi客户端',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
    optionalFields: [
      {
        name: 'status',
      },
      {
        name: 'clientName',
      },
      {
        name: 'description',
      },
      {
        name: 'clientId',
      },
      {
        name: 'ipWhiteList',
      },
      {
        name: 'username',
      },
      {
        name: 'createTime',
      },
      {
        name: 'secureKey',
      },
      {
        name: 'userId',
      },
      {
        name: 'creatorId',
      },
      {
        name: 'signature',
      },
    ],
  },
  {
    id: 'datasource-config',
    name: '数据源配置',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
      {
        action: 'save',
        name: '保存',
        describe: '保存',
        properties: {
          supportDataAccessTypes: [],
        },
      },
      {
        action: 'delete',
        name: '删除',
        describe: '删除',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
    optionalFields: [
      {
        name: 'description',
        describe: '说明',
      },
      {
        name: 'name',
        describe: '名称',
      },
      {
        name: 'state',
        describe: '配置状态',
      },
      {
        name: 'createTime',
        describe: '创建时间(只读)',
      },
      {
        name: 'shareCluster',
        describe: '集群是否共用配置',
      },
      {
        name: 'shareConfig',
        describe: '集群共用的配置',
      },
      {
        name: 'creatorId',
        describe: '创建者ID(只读)',
      },
      {
        name: 'creatorName',
        describe: '创建者名称(只读)',
      },
      {
        name: 'clusterConfigs',
        describe: '集群独立配置信息',
      },
      {
        name: 'typeId',
        describe: '类型ID',
      },
    ],
  },
  {
    id: 'system-config',
    name: '系统配置',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
  },
  {
    id: 'rule-instance',
    name: '规则引擎-实例',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
    optionalFields: [
      {
        name: 'description',
      },
      {
        name: 'modelVersion',
      },
      {
        name: 'name',
      },
      {
        name: 'modelType',
      },
      {
        name: 'state',
      },
      {
        name: 'createTime',
      },
      {
        name: 'instanceDetailJson',
      },
      {
        name: 'modelMeta',
      },
      {
        name: 'creatorId',
      },
      {
        name: 'modelId',
      },
    ],
  },
  {
    id: 'media-channel',
    name: '流媒体通道',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
    parents: [],
    properties: {
      type: ['api'],
    },
  },
  {
    id: 'dictionary',
    name: '数据字典',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
    optionalFields: [
      {
        name: 'status',
      },
      {
        name: 'name',
      },
      {
        name: 'describe',
      },
      {
        name: 'classified',
      },
      {
        name: 'createTime',
      },
      {
        name: 'creatorId',
      },
    ],
  },
  {
    id: 'report-config',
    name: '报表开发',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
    optionalFields: [
      {
        name: 'datasourceConfiguration',
        describe: '数据源配置',
      },
      {
        name: 'status',
        describe: '状态',
      },
      {
        name: 'description',
        describe: '说明',
      },
      {
        name: 'configuration',
        describe: '报表配置',
      },
      {
        name: 'datasource',
        describe: '数据源',
      },
      {
        name: 'name',
        describe: '名称',
      },
      {
        name: 'version',
        describe: '版本',
      },
      {
        name: 'createTime',
        describe: '创建时间',
      },
      {
        name: 'classify',
        describe: '分类',
      },
      {
        name: 'creatorId',
        describe: '创建者ID',
      },
      {
        name: 'permission',
        describe: '权限限制',
      },
    ],
  },
  {
    id: 'ctwing-product',
    name: 'Ctwing产品管理',
    describe: '',
    status: 1,
    actions: [
      {
        action: 'enable',
        name: '启用',
        describe: '启用',
        properties: {
          supportDataAccessTypes: [],
        },
      },
      {
        action: 'disable',
        name: '禁用',
        describe: '禁用',
        properties: {
          supportDataAccessTypes: [],
        },
      },
      {
        action: 'query',
        name: '查询',
        describe: '查询',
        properties: {
          supportDataAccessTypes: [],
        },
      },
      {
        action: 'save',
        name: '保存',
        describe: '保存',
        properties: {
          supportDataAccessTypes: [],
        },
      },
      {
        action: 'delete',
        name: '删除',
        describe: '删除',
        properties: {
          supportDataAccessTypes: [],
        },
      },
    ],
  },
];
