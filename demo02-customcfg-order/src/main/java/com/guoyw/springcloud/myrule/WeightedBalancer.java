package com.guoyw.springcloud.myrule;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.Balancer;

import java.util.List;

/**
 * author: guoyw
 * create: 2020-06-09 10:21
 * 根据权重选着随机选着一个
 **/

public class WeightedBalancer extends Balancer{
  
  public static Instance chooseInstanceByRandomWeight(List<Instance> hosts){
    return getHostByRandomWeight(hosts);
  }
  
}
