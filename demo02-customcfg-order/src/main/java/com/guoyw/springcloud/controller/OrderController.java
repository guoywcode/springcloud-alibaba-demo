package com.guoyw.springcloud.controller;

import com.guoyw.springcloud.entity.OrderInfo;
import com.guoyw.springcloud.entity.PayInfo;
import com.guoyw.springcloud.entity.ProductInfo;
import com.guoyw.springcloud.mapper.OrderInfoMapper;
import com.guoyw.springcloud.vo.OrderAndPayVo;
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
 * @create: 2020-06-08 21:39
 **/

@RestController
public class OrderController {

  @Autowired
  private OrderInfoMapper orderInfoMapper;
  @Autowired
  private RestTemplate restTemplate;

  @GetMapping("selectOrderInfoById/{orderNo}")
  public Object selectOrderInfoById(@PathVariable("orderNo") String orderNo){

    OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderNo);
    if(null == orderInfo)
      return "没有获取到订单";

   ResponseEntity<ProductInfo> responseEntity = restTemplate.getForEntity("http://ribbon-product/selectProductInfoByproductNo/"+orderInfo.getProductNo(), ProductInfo.class);

   ProductInfo productInfo =  responseEntity.getBody();

   if(productInfo == null)
     return "没有找到该商品";

    OrderVo orderVo = new OrderVo();
    orderVo.setOrderNo(orderInfo.getOrderNo());
    orderVo.setUserName(orderInfo.getUserName());
    orderVo.setProductName(productInfo.getProductName());
    orderVo.setProductNum(orderInfo.getProductCount());

    return orderVo;
  }

  @GetMapping("/getOrderAndPayInfoByOrderNo/{orderNo}")
  public Object getOrderAndPayInfoByOrderNo(@PathVariable("orderNo") String orderNo){
    OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderNo);
    if(orderInfo == null)
      return "没有查询到该订单";
   ResponseEntity<PayInfo> responseEntity = restTemplate.getForEntity("http://ribbon-pay/selectPayinfoByOrderNo/"+orderInfo.getProductNo(), PayInfo.class);
   PayInfo payInfo =responseEntity.getBody();

   if(payInfo == null)
     return "没有对应的支付信息";

    OrderAndPayVo orderAndPayVo = new OrderAndPayVo();
    orderAndPayVo.setOrderNo(orderNo);
    orderAndPayVo.setProductNo(orderInfo.getProductNo());
    orderAndPayVo.setProductCount(orderInfo.getProductCount());
    orderAndPayVo.setPayNo(payInfo.getPayNo());
    orderAndPayVo.setPayTime(payInfo.getPayTime());
    orderAndPayVo.setUserName(orderInfo.getUserName());

    return orderAndPayVo;
  }

}
