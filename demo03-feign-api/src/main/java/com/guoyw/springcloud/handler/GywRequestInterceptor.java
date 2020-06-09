package com.guoyw.springcloud.handler;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

/**
 * author: guoyw
 * create: 2020-06-09 17:53
 **/

public class GywRequestInterceptor implements RequestInterceptor{
  @Override
  public void apply(RequestTemplate requestTemplate){
   ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request =  attributes.getRequest();
    requestTemplate.header("Token",request.getHeader("Token"));
  }
}
