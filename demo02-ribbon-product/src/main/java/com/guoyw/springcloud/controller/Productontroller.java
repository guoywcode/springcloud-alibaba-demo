package com.guoyw.springcloud.controller;

import com.guoyw.springcloud.entity.ProductInfo;
import com.guoyw.springcloud.mapper.ProductInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springcloud-alibaba-demo
 * @description:
 * @author: guoyw
 * @create: 2020-06-08 20:53
 **/

@RestController
public class Productontroller {

  @Autowired
  private ProductInfoMapper productInfoMapper;

  @GetMapping("/selectProductInfoByproductNo/{productNo}")
  public Object  selectProductInfoByProductNo(@PathVariable("productNo") String productNo){

    ProductInfo productInfo = productInfoMapper.selectProductInfoById(productNo);

    if(productInfo == null)
      return "未找到该商品";

    return productInfo;
  }
}
