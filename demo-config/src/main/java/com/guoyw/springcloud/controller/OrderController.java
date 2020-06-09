package com.guoyw.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springcloud-alibaba-demo
 * @description:
 * @author: guoyw
 * @create: 2020-06-09 20:51
 **/

@Slf4j
@RestController
public class OrderController {

  @Value("${isNewBusi}")
  private Boolean isNewBusi;

  @Autowired
  private DiscoveryClient discoveryClient;

  @GetMapping("/selectOrderInfoById/{orderNo}")
  public Object selectOrderInfoById(@PathVariable("orderNo") String orderNo){
    log.info("是否业务走新逻辑:{}",isNewBusi);
    if(isNewBusi) {
      return "查询订单执行新逻辑->execute new busi : "+orderNo;
    }
    return "查询订单执行老逻辑->execute old busi : "+orderNo;
  }
}
