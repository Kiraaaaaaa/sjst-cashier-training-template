package com.meituan.catering.management;

import com.meituan.catering.management.shop.dao.mapper.ShopMapper;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 启动类
 */
@Slf4j
@SpringBootApplication
@MapperScan(basePackageClasses = ShopMapper.class)
public class CateringManagementShopApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(CateringManagementShopApplication.class, args);
        Environment env = application.getEnvironment();
        String port = env.getProperty("server.port");
        log.info("\n----------------------------------------------------------\n\t" +
                "Application Declare is running! Access URLs:\n\t" +
                "Swagger-UI: \t\thttp://" + "localhost" + ":" + port + "/doc.html#/\n" +
                "----------------------------------------------------------");
    }
}

