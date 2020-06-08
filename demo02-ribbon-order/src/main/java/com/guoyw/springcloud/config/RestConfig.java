package com.guoyw.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @program: springcloud-alibaba-demo
 * @description:
 * @author: guoyw
 * @create: 2020-06-08 20:30
 **/

@Configuration
public class RestConfig {

  @LoadBalanced
  @Bean
  public RestTemplate restTemplate(){
    return new RestTemplate();
  }
}
