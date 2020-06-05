package com.guoyw.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @program: springcloud-alibaba-demo
 * @description:
 * @author: guoyw
 * @create: 2020-06-05 22:29
 **/

@Configuration
public class RestConfig {

  @Bean
  public RestTemplate restTemplate(){
    return new RestTemplate();
  }

}
