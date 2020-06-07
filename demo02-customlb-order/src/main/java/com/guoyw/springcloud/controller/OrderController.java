package com.guoyw.springcloud.controller;

import com.guoyw.springcloud.entity.OrderInfo;
import com.guoyw.springcloud.entity.ProductInfo;
import com.guoyw.springcloud.mapper.OrderInfoMapper;
import com.guoyw.springcloud.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @program: springcloud-alibaba-demo
 * @description:
 * @author: guoyw
 * @create: 2020-06-07 17:44
 **/

@RestController
public class OrderController {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private OrderInfoMapper orderInfoMapper;


  @GetMapping("/selectOrderById/{orderNo}")
  public Object selectOrderById(@PathVariable("orderNo") String orderNo){
    OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderNo);

    if(null == orderInfo)
      return "没有该订单。";

    ResponseEntity<ProductInfo> responseEntity = restTemplate.getForEntity("http://customlb-proud/selectProductInfoById/"+orderInfo.getProductNo(), ProductInfo.class);
    ProductInfo productInfo = responseEntity.getBody();

    if(productInfo == null)
      return "没有该对象";

    OrderVo orderVo = new OrderVo();
    orderVo.setOrderNo(orderInfo.getOrderNo());
    orderVo.setUserName(orderInfo.getUserName());
    orderVo.setProductName(productInfo.getProductName());
    orderVo.setProductNum(orderInfo.getProductCount());

    return orderVo;
  }

}
