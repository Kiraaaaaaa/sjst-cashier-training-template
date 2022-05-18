package com.meituan.catering.management;

import com.meituan.catering.management.order.dao.mapper.BaseMapper;
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
@MapperScan(basePackageClasses = BaseMapper.class)
public class CateringManagementOrderApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(CateringManagementOrderApplication.class, args);
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
