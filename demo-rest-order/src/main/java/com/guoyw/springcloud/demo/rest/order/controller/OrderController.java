package com.guoyw.springcloud.demo.rest.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @program: springcloud-alibaba-demo
 * @description: 订单Controller
 * @author: guoyw
 * @create: 2020-06-04 21:42
 **/
@RestController
public class OrderController {

  @Autowired
  private RestTemplate restTemplate;

  public static final String uri = "http://localhost:8081/selectProductInfoById/";

  @RequestMapping("/selectOrderInfoById/{orderNo}")
  public Object selectOrderInfoById(@PathVariable("orderNo") String orderNo) {

    OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderNo);
    if(null == orderInfo) {
      return "根据orderNo:"+orderNo+"查询没有该订单";
    }
    ResponseEntity<ProductInfo> responseEntity= restTemplate.getForEntity(uri+orderInfo.getProductNo(), ProductInfo.class);

    ProductInfo productInfo = responseEntity.getBody();

    if(productInfo == null) {
      return "没有对应的商品";
    }

    OrderVo orderVo = new OrderVo();
    orderVo.setOrderNo(orderInfo.getOrderNo());
    orderVo.setUserName(orderInfo.getUserName());
    orderVo.setProductName(productInfo.getProductName());
    orderVo.setProductNum(orderInfo.getProductCount());

    return orderVo;
  }


}
