package com.meituan.catering.management;

import com.meituan.catering.management.shop.dao.mapper.ShopMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 */
@SpringBootApplication
@MapperScan(basePackageClasses = ShopMapper.class)
public class CateringManagementShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(CateringManagementShopApplication.class, args);
    }

}
