package com.meituan.catering.management.shop.api.thrift.config;

import com.facebook.nifty.core.NettyServerConfig;
import com.facebook.nifty.core.ThriftServerDef;
import com.facebook.swift.codec.ThriftCodecManager;
import com.facebook.swift.service.ThriftServer;
import com.facebook.swift.service.ThriftServiceProcessor;
import com.meituan.catering.management.shop.api.thrift.service.ShopThriftService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;

@Configuration
public class ThriftServicePublisher {

    public static final NettyServerConfig NETTY_SERVER_CONFIG = NettyServerConfig.newBuilder().build();

    @Resource
    private ShopThriftService shopThriftService;

    @Bean
    public ThriftServer shopThriftServiceBean() {
        return new ThriftServer(
                NETTY_SERVER_CONFIG,
                ThriftServerDef.newBuilder()
                        .listen(ShopThriftService.PORT)
                        .withProcessor(new ThriftServiceProcessor(
                                new ThriftCodecManager(),
                                new ArrayList<>(),
                                shopThriftService
                        ))
                        .build()
        ).start();
    }

}
