package com.guoyw.springcloud.controller;

import com.guoyw.springcloud.entity.ProductInfo;
import com.guoyw.springcloud.mapper.ProductInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: springcloud-alibaba-demo
 * @description:
 * @author: guoyw
 * @create: 2020-06-05 22:08
 **/

@RestController
public class ProductInfoController {

  @Autowired
  private ProductInfoMapper productInfoMapper;

  @RequestMapping("/selectProductInfoById/{productNo}")
  public Object selectProductInfoById(@PathVariable("productNo") String productNo) {

    ProductInfo productInfo = productInfoMapper.selectProductInfoById(productNo);
    return productInfo;
  }

  @RequestMapping("/getProduct/{productId}")
  ProductInfo qryProductInfo(@PathVariable String productId) {
    ProductInfo productInfo = productInfoMapper.selectProductInfoById(productId);
    return productInfo;
  }

  @RequestMapping(value = "/qryProductInfo",method = {RequestMethod.GET})
  ProductInfo qryProductInfo2(@RequestParam("productId") String productId){
    ProductInfo productInfo = productInfoMapper.selectProductInfoById(productId);
    return productInfo;
  }

  @RequestMapping(value = "/qryProductInfo2",method = {RequestMethod.POST})
  ProductInfo qryProductInfo3(@RequestBody ProductInfo productInfo){
    return productInfo;
  }

}
