package com.meituan.catering.management;

import com.meituan.catering.management.order.dao.mapper.BaseMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 */
@SpringBootApplication
@MapperScan(basePackageClasses = BaseMapper.class)
public class CateringManagementOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CateringManagementOrderApplication.class, args);
    }

}
