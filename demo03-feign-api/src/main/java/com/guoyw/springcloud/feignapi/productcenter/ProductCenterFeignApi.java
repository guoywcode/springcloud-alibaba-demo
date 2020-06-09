package com.guoyw.springcloud.feignapi.productcenter;

import com.guoyw.springcloud.config.ProductCenterFeignConfig;
import com.guoyw.springcloud.entity.ProductInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * author: guoyw
 * create: 2020-06-09 14:45
 **/

//@FeignClient("product-center")
@FeignClient(name = "product-center",configuration = ProductCenterFeignConfig.class)
public interface ProductCenterFeignApi{
  
  @GetMapping("/selectProductInfoById/{productNo}")
  ProductInfo selectProductInfoByIn(@PathVariable("productNo") String productNo);
  
  @GetMapping("/getToken")
  String getToken(@RequestHeader("token") String token);
}
