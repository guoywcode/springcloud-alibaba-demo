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
 * @create: 2020-06-08 20:26
 **/

@RestController
public class OrderController {

  @Autowired
  private OrderInfoMapper orderInfoMapper;
  @Autowired
  private RestTemplate restTemplate;

  @GetMapping("/selectOrderInfoById/{orderNo}")
  public Object selectOrderInfoByOrderNo(@PathVariable("orderNo") String orderNo){
   OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderNo);
   if(orderInfo == null)
     return "没有找到该订单";
    ProductInfo productInfo =null;
    try {
    ResponseEntity<ProductInfo> responseEntity = restTemplate.getForEntity("http://ribbon-product/selectProductInfoByproductNo/"+orderInfo.getProductNo(), ProductInfo.class);
      productInfo = responseEntity.getBody();
    }catch (Exception exceptione){
     System.out.println("请求商品服务异常");
   }

    if(productInfo == null)
      return "未找到对应的商品";

    OrderVo orderVo = new OrderVo();
    orderVo.setOrderNo(orderInfo.getOrderNo());
    orderVo.setUserName(orderInfo.getUserName());
    orderVo.setProductName(productInfo.getProductName());
    orderVo.setProductNum(orderInfo.getProductCount());

    return orderVo;
  }

}
