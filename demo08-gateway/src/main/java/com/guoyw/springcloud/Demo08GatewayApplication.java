package com.guoyw.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient

public class Demo08GatewayApplication{

  public static void main(String[] args){
    SpringApplication.run(Demo08GatewayApplication.class, args);
  }

}
