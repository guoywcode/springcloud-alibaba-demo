package com.guoyw.springcloud.controller;

import com.guoyw.springcloud.entity.ProductInfo;
import com.guoyw.springcloud.feignapi.productcenter.ProductCenterFeignApi;
import com.guoyw.springcloud.mapper.ProductInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: guoyw
 * create: 2020-06-09 14:32
 **/

@Slf4j
@RestController
public class ProductController implements ProductCenterFeignApi{
  
  @Autowired
  private ProductInfoMapper productInfoMapper;
  
  @GetMapping("/selectProductInfoById/{productNo}")
  public ProductInfo selectProductInfoByIn(@PathVariable("productNo") String productNo){
    log.info("我被调用了...");
    return productInfoMapper.selectProductInfoById(productNo);
  }
  
  @GetMapping("/getToken")
  public String getToken(@RequestHeader("token") String token){
    log.info("token: {}", token);
    return token;
  }
}
