const componentType = [
  {
    id: "UDP",
    name: "UDP"
  },
  {
    id: "HTTP_CLIENT",
    name: "HTTP客户端"
  },
  {
    id: "TCP_SERVER",
    name: "TCP服务"
  },
  {
    id: "WEB_SOCKET_SERVER",
    name: "WebSocket服务"
  },
  {
    id: "MQTT_CLIENT",
    name: "MQTT客户端"
  },
  {
    id: "COAP_CLIENT",
    name: "CoAP客户端"
  },
  {
    id: "HTTP_SERVER",
    name: "HTTP服务"
  },
  {
    id: "MQTT_SERVER",
    name: "MQTT服务"
  },
  {
    id: "WEB_SOCKET_CLIENT",
    name: "WebSocket客户端"
  },
  {
    id: "COAP_SERVER",
    name: "CoAP服务"
  },
  {
    id: "TCP_CLIENT",
    name: "TCP客户端"
  }
];

const componentList = [
  {
    id: "1435128873398411264",
    name: "mqtt-9112",
    type: "MQTT_SERVER",
    state: {
      text: "已启动",
      value: "enabled"
    },
    creatorId: "1199596756811550720",
    createTime: 1630996361479,
    configuration: {
      host: "0.0.0.0",
      port: "9112",
      maxMessageSize: 8096
    },
    shareCluster: true,
    typeObject: {
      name: "MQTT服务",
      text: "MQTT服务",
      value: "MQTT_SERVER"
    }
  },
  {
    id: "1430819314472525824",
    name: "HTTP组件转发",
    type: "HTTP_CLIENT",
    state: {
      text: "已启动",
      value: "enabled"
    },
    creatorId: "1199596756811550720",
    createTime: 1629968882563,
    configuration: {
      baseUrl: "http://192-168-22-65-8082.proxy.jetlinks.cn:5083/"
    },
    shareCluster: true,
    typeObject: {
      name: "HTTP客户端",
      text: "HTTP客户端",
      value: "HTTP_CLIENT"
    }
  },
  {
    id: "1428631239709368320",
    name: "coap-server-test-9120",
    type: "COAP_SERVER",
    state: {
      text: "已启动",
      value: "enabled"
    },
    creatorId: "1199596756811550720",
    createTime: 1629447204888,
    configuration: {
      address: "0.0.0.0",
      port: "9120",
      enableDtls: false
    },
    shareCluster: true,
    typeObject: {
      name: "CoAP服务",
      text: "CoAP服务",
      value: "COAP_SERVER"
    }
  },
  {
    id: "1428620514026790912",
    name: "1",
    type: "HTTP_CLIENT",
    state: {
      text: "已停止",
      value: "disabled"
    },
    creatorId: "1224291557238583296",
    createTime: 1629444647685,
    configuration: {
      ssl: true,
      verifyHost: true
    },
    shareCluster: true,
    typeObject: {
      name: "HTTP客户端",
      text: "HTTP客户端",
      value: "HTTP_CLIENT"
    }
  },
  {
    id: "1427437931511197696",
    name: "海康-tcp7667",
    type: "TCP_SERVER",
    state: {
      text: "已停止",
      value: "disabled"
    },
    creatorId: "1199596756811550720",
    createTime: 1629162698042,
    configuration: {
      host: "0.0.0.0",
      port: "7667",
      parserType: "DIRECT"
    },
    shareCluster: true,
    typeObject: {
      name: "TCP服务",
      text: "TCP服务",
      value: "TCP_SERVER"
    }
  },
  {
    id: "1427436533159604224",
    name: "海康-tcp7666",
    type: "TCP_SERVER",
    state: {
      text: "已停止",
      value: "disabled"
    },
    creatorId: "1199596756811550720",
    createTime: 1629162364649,
    configuration: {
      host: "0.0.0.0",
      port: "7666",
      parserType: "DIRECT"
    },
    shareCluster: true,
    typeObject: {
      name: "TCP服务",
      text: "TCP服务",
      value: "TCP_SERVER"
    }
  },
  {
    id: "1427436299457179648",
    name: "海康-7667",
    type: "UDP",
    state: {
      text: "已停止",
      value: "disabled"
    },
    creatorId: "1199596756811550720",
    createTime: 1629162308931,
    configuration: {
      localAddress: "0.0.0.0",
      localPort: "7667"
    },
    shareCluster: true,
    typeObject: {
      name: "UDP",
      text: "UDP",
      value: "UDP"
    }
  },
  {
    id: "1427436145366839296",
    name: "海康-7666",
    type: "UDP",
    state: {
      text: "已停止",
      value: "disabled"
    },
    creatorId: "1199596756811550720",
    createTime: 1629162272191,
    configuration: {
      localAddress: "0.0.0.0",
      localPort: "7666"
    },
    shareCluster: true,
    typeObject: {
      name: "UDP",
      text: "UDP",
      value: "UDP"
    }
  },
  {
    id: "1425296651823116288",
    name: "停车场设备接入",
    type: "HTTP_SERVER",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      port: "9111"
    },
    shareCluster: true,
    typeObject: {
      name: "HTTP服务",
      text: "HTTP服务",
      value: "HTTP_SERVER"
    }
  },
  {
    id: "1419588228622950400",
    name: "建发人脸对比-8083",
    type: "HTTP_SERVER",
    state: {
      text: "已停止",
      value: "disabled"
    },
    configuration: {
      port: "8083"
    },
    shareCluster: true,
    typeObject: {
      name: "HTTP服务",
      text: "HTTP服务",
      value: "HTTP_SERVER"
    }
  },
  {
    id: "1418508801445044224",
    name: "西亿达大喇叭-9130（勿动）",
    type: "TCP_SERVER",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      host: "0.0.0.0",
      port: "9130",
      parserType: "DIRECT"
    },
    shareCluster: true,
    typeObject: {
      name: "TCP服务",
      text: "TCP服务",
      value: "TCP_SERVER"
    }
  },
  {
    id: "1416936713994981376",
    name: "新增吉利独立配置",
    type: "MQTT_SERVER",
    state: {
      text: "已停止",
      value: "disabled"
    },
    shareCluster: false,
    cluster: [
      {
        serverId: "jetlinks-platform:8844",
        configuration: {
          instance: 1,
          host: "0.0.0.0",
          port: "8080",
          ssl: false,
          certId: "1411995153910575104",
          maxMessageSize: 8096
        }
      }
    ],
    typeObject: {
      name: "MQTT服务",
      text: "MQTT服务",
      value: "MQTT_SERVER"
    }
  },
  {
    id: "1413102080898441216",
    name: "onenet-mqtt设备属性上传",
    type: "MQTT_CLIENT",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      clientId: "client",
      host: "183.230.40.96",
      port: "8883",
      ssl: true,
      certId: "1412707284901539840",
      username: "znjj",
      password:
        "version=2018-10-31&res=mqs%2Fznjj&et=1739257633&method=md5&sign=6lA%2BZ21LNGtE7ibaZQxPvQ%3D%3D",
      maxMessageSize: 8096
    },
    shareCluster: true,
    typeObject: {
      name: "MQTT客户端",
      text: "MQTT客户端",
      value: "MQTT_CLIENT"
    }
  },
  {
    id: "1413015002426224640",
    name: "onenet设备api对接-8082",
    type: "HTTP_SERVER",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      port: "8082"
    },
    shareCluster: true,
    typeObject: {
      name: "HTTP服务",
      text: "HTTP服务",
      value: "HTTP_SERVER"
    }
  },
  {
    id: "1412705577073225728",
    name: "新增吉利测试",
    description: "11111",
    type: "MQTT_CLIENT",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      clientId: "8888",
      host: "127.0.0.1",
      port: "1883",
      ssl: true,
      certId: "1411995153910575104",
      maxMessageSize: 8096
    },
    shareCluster: false,
    cluster: [
      {
        serverId: "jetlinks-platform:8844",
        configuration: {
          clientId: "8888",
          host: "0.0.0.0",
          port: "8888",
          ssl: false,
          certId: "1411995153910575104",
          username: "admin",
          password: "123456",
          maxMessageSize: 8096
        }
      },
      {
        serverId: "123",
        configuration: {
          clientId: "",
          host: "0.0.0.0",
          port: "",
          ssl: false,
          certId: "",
          username: "",
          password: "",
          maxMessageSize: 8096
        }
      }
    ],
    typeObject: {
      name: "MQTT客户端",
      text: "MQTT客户端",
      value: "MQTT_CLIENT"
    }
  },
  {
    id: "1412618854817443840",
    name: "123",
    type: "MQTT_CLIENT",
    state: {
      text: "已停止",
      value: "disabled"
    },
    shareCluster: false,
    cluster: [
      {
        serverId: "jetlinks-platform:8844",
        configuration: {
          clientId: "",
          host: "0.0.0.0",
          port: "",
          ssl: false,
          certId: "",
          username: "",
          password: "",
          maxMessageSize: 8096
        }
      },
      {
        serverId: "000",
        configuration: {
          clientId: "",
          host: "0.0.0.0",
          port: "",
          ssl: false,
          certId: "",
          username: "",
          password: "",
          maxMessageSize: 8096
        }
      }
    ],
    typeObject: {
      name: "MQTT客户端",
      text: "MQTT客户端",
      value: "MQTT_CLIENT"
    }
  },
  {
    id: "1412298484696592384",
    name: "智能家居转发onenet",
    type: "MQTT_CLIENT",
    state: {
      text: "已停止",
      value: "disabled"
    },
    configuration: {
      clientId: "mqtt-test",
      host: "183.230.40.96",
      port: "1883",
      ssl: false,
      username: "446564",
      password:
        "version=2018-10-31&res=products%2F446564%2Fdevices%2Fmqtt-test&et=1725999215&method=md5&sign=O6E8p3I4ZRPs3NLe1T8Yng%3D%3D",
      maxMessageSize: 8096
    },
    shareCluster: true,
    typeObject: {
      name: "MQTT客户端",
      text: "MQTT客户端",
      value: "MQTT_CLIENT"
    }
  },
  {
    id: "1403007330723151872",
    name: "泰易电表数据转发",
    type: "MQTT_CLIENT",
    state: {
      text: "已停止",
      value: "disabled"
    },
    configuration: {
      clientId: "ty-db",
      host: "st.jetlinks.cn",
      port: "8108",
      username: "admin",
      password: "admin",
      maxMessageSize: 8096
    },
    shareCluster: true,
    typeObject: {
      name: "MQTT客户端",
      text: "MQTT客户端",
      value: "MQTT_CLIENT"
    }
  },
  {
    id: "1390870534277734400",
    name: "tpson用户信息传输-9129",
    type: "TCP_SERVER",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      host: "0.0.0.0",
      port: "9129",
      parserType: "delimited",
      parserConfiguration: {
        delimited: "##"
      }
    },
    shareCluster: true,
    typeObject: {
      name: "TCP服务",
      text: "TCP服务",
      value: "TCP_SERVER"
    }
  },
  {
    id: "1387688277417304064",
    name: "静力水准仪tcp-9128",
    type: "TCP_SERVER",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      host: "0.0.0.0",
      port: "9128",
      parserType: "DIRECT"
    },
    shareCluster: true,
    typeObject: {
      name: "TCP服务",
      text: "TCP服务",
      value: "TCP_SERVER"
    }
  },
  {
    id: "1387687945035489280",
    name: "测斜仪tcp-9127",
    type: "TCP_SERVER",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      host: "0.0.0.0",
      port: "9127",
      parserType: "DIRECT"
    },
    shareCluster: true,
    typeObject: {
      name: "TCP服务",
      text: "TCP服务",
      value: "TCP_SERVER"
    }
  },
  {
    id: "1387685457637056512",
    name: "测缝仪-9126",
    type: "TCP_SERVER",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      host: "0.0.0.0",
      port: "9126",
      parserType: "DIRECT"
    },
    shareCluster: true,
    typeObject: {
      name: "TCP服务",
      text: "TCP服务",
      value: "TCP_SERVER"
    }
  },
  {
    id: "1384700346509860864",
    name: "西亿达平台对接-8081",
    type: "HTTP_SERVER",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      port: "8081"
    },
    shareCluster: true,
    typeObject: {
      name: "HTTP服务",
      text: "HTTP服务",
      value: "HTTP_SERVER"
    }
  },
  {
    id: "1380340230073028608",
    name: "泰易新电表接入TCP服务-9123",
    type: "TCP_SERVER",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      host: "0.0.0.0",
      port: "9123",
      parserType: "DIRECT"
    },
    shareCluster: true,
    typeObject: {
      name: "TCP服务",
      text: "TCP服务",
      value: "TCP_SERVER"
    }
  },
  {
    id: "1379961784765796352",
    name: "lora设备接入tcp服务-9125",
    type: "TCP_SERVER",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      host: "0.0.0.0",
      port: "9125",
      parserType: "DIRECT"
    },
    shareCluster: true,
    typeObject: {
      name: "TCP服务",
      text: "TCP服务",
      value: "TCP_SERVER"
    }
  },
  {
    id: "1377092520614506496",
    name: "工讯tcp-9124",
    type: "TCP_SERVER",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      host: "0.0.0.0",
      port: "9124",
      parserType: "DIRECT"
    },
    shareCluster: true,
    typeObject: {
      name: "TCP服务",
      text: "TCP服务",
      value: "TCP_SERVER"
    }
  },
  {
    id: "1376366490694135808",
    name: "tpson烟感UDP-9121",
    type: "UDP",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      localAddress: "0.0.0.0",
      localPort: "9121"
    },
    shareCluster: true,
    typeObject: {
      name: "UDP",
      text: "UDP",
      value: "UDP"
    }
  },
  {
    id: "1371410806284836864",
    name: "tpson水相关UDP-9122",
    type: "UDP",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      localAddress: "0.0.0.0",
      localPort: "9122"
    },
    shareCluster: true,
    typeObject: {
      name: "UDP",
      text: "UDP",
      value: "UDP"
    }
  },
  {
    id: "1369515152473858048",
    name: "tpson设备接入TCP服务-9120",
    type: "TCP_SERVER",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      host: "0.0.0.0",
      port: "9120",
      parserType: "DIRECT"
    },
    shareCluster: true,
    typeObject: {
      name: "TCP服务",
      text: "TCP服务",
      value: "TCP_SERVER"
    }
  },
  {
    id: "1353633449874321408",
    name: "ADAS测试-9003",
    type: "HTTP_SERVER",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      port: "9003"
    },
    shareCluster: true,
    typeObject: {
      name: "HTTP服务",
      text: "HTTP服务",
      value: "HTTP_SERVER"
    }
  },
  {
    id: "1333379016070365184",
    name: "TCP服务（勿动）-9112",
    type: "TCP_SERVER",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      ssl: false,
      host: "0.0.0.0",
      port: "9112",
      parserType: "DIRECT"
    },
    shareCluster: true,
    typeObject: {
      name: "TCP服务",
      text: "TCP服务",
      value: "TCP_SERVER"
    }
  },
  {
    id: "1323199762019618816",
    name: "集贤MQTT服务测试-1884",
    type: "MQTT_SERVER",
    state: {
      text: "已停止",
      value: "disabled"
    },
    configuration: {
      instance: 4,
      host: "0.0.0.0",
      port: "1884",
      maxMessageSize: 8096
    },
    shareCluster: true,
    typeObject: {
      name: "MQTT服务",
      text: "MQTT服务",
      value: "MQTT_SERVER"
    }
  },
  {
    id: "1316936412521058304",
    name: "模具状态监控接入-TCP-9115",
    type: "TCP_SERVER",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      host: "0.0.0.0",
      port: "9115",
      parserType: "DIRECT"
    },
    shareCluster: true,
    typeObject: {
      name: "TCP服务",
      text: "TCP服务",
      value: "TCP_SERVER"
    }
  },
  {
    id: "1283650594273501184",
    name: "MQTT-Broker",
    type: "MQTT_CLIENT",
    state: {
      text: "已停止",
      value: "disabled"
    },
    configuration: {
      clientId: "jetlinks-mqtt-client",
      host: "test.mosquitto.org",
      port: "1883",
      ssl: false,
      maxMessageSize: 8096
    },
    typeObject: {
      name: "MQTT客户端",
      text: "MQTT客户端",
      value: "MQTT_CLIENT"
    }
  },
  {
    id: "1280380104309391360",
    name: "测试HTTP2-9114",
    type: "HTTP_SERVER",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      port: "9114",
      ssl: false
    },
    shareCluster: true,
    typeObject: {
      name: "HTTP服务",
      text: "HTTP服务",
      value: "HTTP_SERVER"
    }
  },
  {
    id: "1247849882493902848",
    name: "消防设备对接-9113",
    type: "HTTP_SERVER",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      port: "9113",
      ssl: false
    },
    shareCluster: true,
    typeObject: {
      name: "HTTP服务",
      text: "HTTP服务",
      value: "HTTP_SERVER"
    }
  },
  {
    id: "1247816228199849984",
    name: "http-8080",
    type: "HTTP_SERVER",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      port: "8080",
      certId: "1214366803375415296",
      ssl: false
    },
    shareCluster: true,
    typeObject: {
      name: "HTTP服务",
      text: "HTTP服务",
      value: "HTTP_SERVER"
    }
  },
  {
    id: "1211175386358579200",
    name: "测试MQTT(勿动)-9110",
    description: "",
    type: "MQTT_SERVER",
    state: {
      text: "已启动",
      value: "enabled"
    },
    configuration: {
      instance: 9,
      host: "0.0.0.0",
      port: "9110",
      ssl: false,
      certId: "1214366701554491392",
      maxMessageSize: 20000
    },
    shareCluster: true,
    typeObject: {
      name: "MQTT服务",
      text: "MQTT服务",
      value: "MQTT_SERVER"
    }
  }
];

export { componentType, componentList };
