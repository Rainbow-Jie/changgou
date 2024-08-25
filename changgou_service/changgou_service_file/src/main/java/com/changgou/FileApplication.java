package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
//声明当前的工程是eureka客户端
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class,args);
    }
}
