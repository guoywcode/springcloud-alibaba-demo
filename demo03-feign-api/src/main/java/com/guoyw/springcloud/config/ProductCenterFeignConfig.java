package com.guoyw.springcloud.config;

import com.guoyw.springcloud.handler.GywRequestInterceptor;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * 这个类上千万不要添加@Configuration,不然会被作为全局配置文件共享
 * author: guoyw
 * create: 2020-06-09 17:31
 **/

public class ProductCenterFeignConfig{
  
  @Bean
  public Logger.Level level(){
    //return Logger.Level.FULL;
    //return Logger.Level.HEADERS;
    return Logger.Level.BASIC;
    //return Logger.Level.NONE;
  }
  
  @Bean
  public RequestInterceptor requestInterceptor(){
    return new GywRequestInterceptor();
  }
}
