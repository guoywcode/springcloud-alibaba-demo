package com.guoyw.springcloud.controller;

import com.guoyw.springcloud.entity.OrderInfo;
import com.guoyw.springcloud.entity.ProductInfo;
import com.guoyw.springcloud.feignapi.productcenter.ProductCenterFeignApi;
import com.guoyw.springcloud.mapper.OrderInfoMapper;
import com.guoyw.springcloud.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: guoyw
 * create: 2020-06-09 14:38
 **/
@RestController
public class OrderController{

  @Autowired
  private OrderInfoMapper orderInfoMapper;
  @Autowired
  private ProductCenterFeignApi productCenterFeignApi;
  
  @GetMapping("/selectOrderInfoById/{orderNo}")
  public Object selectOrderInfoById(@PathVariable("orderNo") String orderNo ){
    OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderNo);
    if(orderInfo == null)
      return "查询订单不存在";
  
    ProductInfo productInfo = productCenterFeignApi.selectProductInfoByIn(orderInfo.getProductNo());
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
  
  @RequestMapping("/testFeignInterceptor")
  public String testFeignInterceptor() {
    return productCenterFeignApi.getToken();
  }
}
