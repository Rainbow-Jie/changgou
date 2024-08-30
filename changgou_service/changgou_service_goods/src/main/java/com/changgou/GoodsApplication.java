package com.changgou;

import com.changgou.common.util.IdWorker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableEurekaClient
//声明当前的工程是eureka客户端
public class GoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class,args);
    }

    @Value("${workerId}")
    private Integer workerId;

    @Value("${datacenterId}")
    private Integer datacenterId;

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(workerId,datacenterId);
    }
}
