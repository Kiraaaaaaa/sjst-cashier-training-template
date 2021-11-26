package com.meituan.catering.management.product.api.thrift.config;

import com.facebook.nifty.core.NettyServerConfig;
import com.facebook.nifty.core.ThriftServerDef;
import com.facebook.swift.codec.ThriftCodecManager;
import com.facebook.swift.service.ThriftServer;
import com.facebook.swift.service.ThriftServiceProcessor;
import com.meituan.catering.management.product.api.thrift.service.ProductThriftService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;

@Configuration
public class ThriftServicePublisher {

    public static final NettyServerConfig NETTY_SERVER_CONFIG = NettyServerConfig.newBuilder().build();

    @Resource
    private ProductThriftService productThriftService;

    @Bean
    public ThriftServer productThriftServiceBean() {
        return new ThriftServer(
                NETTY_SERVER_CONFIG,
                ThriftServerDef.newBuilder()
                        .listen(ProductThriftService.PORT)
                        .withProcessor(new ThriftServiceProcessor(
                                new ThriftCodecManager(),
                                new ArrayList<>(),
                                productThriftService
                        ))
                        .build()
        ).start();
    }

}
