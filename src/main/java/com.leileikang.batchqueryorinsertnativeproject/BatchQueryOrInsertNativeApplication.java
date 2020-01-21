package com.leileikang.batchqueryorinsertnativeproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author kangleilei
 * @描述 服务启动类
 * @date 2020/1/21
 */
@EnableAsync
@EnableScheduling
@ComponentScan("com.leileikang.batchqueryorinsertnativeproject")
@SpringBootApplication
public class BatchQueryOrInsertNativeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchQueryOrInsertNativeApplication.class, args);
    }

}
