package com.guoyw.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * author: guoyw
 * create: 2020-06-12 14:08
 **/
@Configuration
public class RestConfig{
  @LoadBalanced
  @Bean
  public RestTemplate restTemplate( ) {
    return new RestTemplate();
  }
}
