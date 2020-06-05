package com.guoyw.springcloud.controller;

import com.guoyw.springcloud.entity.OrderInfo;
import com.guoyw.springcloud.entity.ProductInfo;
import com.guoyw.springcloud.mapper.OrderInfoMapper;
import com.guoyw.springcloud.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @program: springcloud-alibaba-demo
 * @description:
 * @author: guoyw
 * @create: 2020-06-05 22:14
 **/

@RestController
public class OrderInfoController {

  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private OrderInfoMapper orderInfoMapper;
  @Autowired
  private DiscoveryClient discoveryClient;

  @RequestMapping("/selectOrderInfoById/{orderNo}")
  public Object selectOrderInfoById(@PathVariable("orderNo") String orderNo) {

    OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderNo);
    if (orderInfo == null)
      return "根据orderNo：" + orderNo + " 查询没有该订单";

    // 从nacos server获取 product-info 的地址
    List<ServiceInstance> serviceInstances = discoveryClient.getInstances("nacosclient-product");
    if (serviceInstances == null)
      return "未找到nacosclient-product实例";

    // 获取第0个实例元素
    String targetUri = serviceInstances.get(0).getUri().toString();
    ResponseEntity<ProductInfo> responseEntity = restTemplate.getForEntity(targetUri + "selectProductInfoById/" + orderInfo.getProductNo(), ProductInfo.class);
    ProductInfo productInfo = responseEntity.getBody();
    if (productInfo == null)
      return "没有对应的商品";

    OrderVo orderVo = new OrderVo();
    orderVo.setOrderNo(orderInfo.getOrderNo());
    orderVo.setUserName(orderInfo.getUserName());
    orderVo.setProductName(productInfo.getProductName());
    orderVo.setProductNum(orderInfo.getProductCount());

    return orderVo;
  }

}
