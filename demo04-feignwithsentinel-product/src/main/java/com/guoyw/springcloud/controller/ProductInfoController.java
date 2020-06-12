package com.guoyw.springcloud.controller;

import com.guoyw.springcloud.entity.ProductInfo;
import com.guoyw.springcloud.feignapi.sentinel.ProductCenterFeignApiWithSentinel;
import com.guoyw.springcloud.mapper.ProductInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class ProductInfoController implements ProductCenterFeignApiWithSentinel{


    @Autowired
    private ProductInfoMapper productInfoMapper;

    @RequestMapping("/selectProductInfoById/{productNo}")
    public ProductInfo selectProductInfoById(@PathVariable("productNo") String productNo)  {

        ProductInfo productInfo = productInfoMapper.selectProductInfoById(productNo);
        return productInfo;
    }

    @RequestMapping("/getToken4Header")
    public String getToken4Header(@RequestHeader("token") String token)  {
        log.info("token:{}",token);
        return token;
    }
}
