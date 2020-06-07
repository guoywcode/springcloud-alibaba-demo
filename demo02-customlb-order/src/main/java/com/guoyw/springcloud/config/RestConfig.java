package com.guoyw.springcloud.config;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @program: springcloud-alibaba-demo
 * @description:
 * @author: guoyw
 * @create: 2020-06-07 17:40
 **/

@Configuration
public class RestConfig {

  @Bean
  public RestTemplate restTemplate(DiscoveryClient discoveryClient){
    return new GywRestTemplate(discoveryClient);
  }
}
