package com.meituan.catering.management;

import com.meituan.catering.management.product.dao.mapper.ProductMapper;
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
@MapperScan(basePackageClasses = ProductMapper.class)
public class CateringManagementProductApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(CateringManagementProductApplication.class, args);
        Environment env = application.getEnvironment();
        String port = env.getProperty("server.port");
        log.info("\n----------------------------------------------------------\n\t" +
                "Application Declare is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + "/\n\t" +
                "External: \thttps://" + "localhost" + ":" + port + "/\n\t" +
                "Swagger-UI: \t\thttp://" + "localhost" + ":" + port + "/doc.html#/\n" +
                "----------------------------------------------------------");
    }

}
