package com.meituan.catering.management;

import com.meituan.catering.management.product.dao.mapper.ProductMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 */
@SpringBootApplication
@MapperScan(basePackageClasses = ProductMapper.class)
public class CateringManagementProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(CateringManagementProductApplication.class, args);
    }

}
