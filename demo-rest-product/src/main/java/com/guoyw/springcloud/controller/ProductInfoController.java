package com.guoyw.springcloud.controller;

import com.guoyw.springcloud.entity.ProductInfo;
import com.guoyw.springcloud.mapper.ProductInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springcloud-alibaba-demo
 * @description:
 * @author: guoyw
 * @create: 2020-06-05 20:23
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

}
