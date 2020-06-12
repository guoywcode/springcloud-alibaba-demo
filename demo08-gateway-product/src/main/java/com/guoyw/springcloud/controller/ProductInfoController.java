package com.guoyw.springcloud.controller;

import com.guoyw.springcloud.entity.ProductInfo;
import com.guoyw.springcloud.mapper.ProductInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductInfoController{


    @Autowired
    private ProductInfoMapper productInfoMapper;

    @RequestMapping("/selectProductInfoById/{productNo}")
    public Object selectProductInfoById(@PathVariable("productNo") String productNo) {

        ProductInfo productInfo = productInfoMapper.selectProductInfoById(productNo);
        return productInfo;
    }

    @RequestMapping("/gateWay4Header")
    public Object gateWay4Header(@RequestHeader("X-Request-Company") String company) {

        return "gateWay拿到请求头"+company;
    }

    @RequestMapping("/gateWay4RequestParam")
    public Object gateWay4RequestParam(@RequestParam(value = "company",defaultValue = "hehe") String company) {

        return "gateWay拿到请求参数"+company;
    }

    @RequestMapping("/testLimit")
    public String testLimit()  {

        return "testLimit";
    }


}
