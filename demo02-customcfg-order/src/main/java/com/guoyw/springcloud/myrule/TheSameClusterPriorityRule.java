package com.guoyw.springcloud.myrule;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author: guoyw
 * create: 2020-06-09 09:43
 **/

@Slf4j
public class TheSameClusterPriorityRule extends AbstractLoadBalancerRule{
  
  @Autowired
  private NacosDiscoveryProperties nacosDiscoveryProperties;
  
  @Override
  public void initWithNiwsConfig(IClientConfig iClientConfig){
  
  }
  
  @Override
  public Server choose(Object o){
    try{
      //第一步：获取服务器集群
      String currentClusterName = nacosDiscoveryProperties.getClusterName();
      
      //第二步：获取一个负载均衡对象
      BaseLoadBalancer loadBalancer = (BaseLoadBalancer) getLoadBalancer();
      
      //第三步：获取当前服务的名称
      String invokedServiceName = loadBalancer.getName();
      
      //第四步： 获取nacos clinet的服务发现组件api
      NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
      
      //第五步：获取所有的服务实例
      List<Instance> instanceList = namingService.getAllInstances(invokedServiceName);
      
      List<Instance> theSameClusterNameInstList = new ArrayList<>();
      
      //第六步：过滤筛选同集群下的所有实例
      for(Instance instance : instanceList){
        if(StringUtils.endsWithIgnoreCase(instance.getClusterName(),currentClusterName)){
          theSameClusterNameInstList.add(instance);
        }
      }
  
     
      Instance toBeChooseInstance ;
      //第七步：选着一个合适的实例调用
      if(theSameClusterNameInstList.isEmpty()){
        toBeChooseInstance = WeightedBalancer.chooseInstanceByRandomWeight(instanceList);
        log.info("发生跨集群调用-->当前服务所在集群:{},被调用服务所在集群:{},host:{},port:{}",currentClusterName,toBeChooseInstance.getClusterName(),toBeChooseInstance.getIp(),toBeChooseInstance.getPort());
      }else {
        toBeChooseInstance = WeightedBalancer.chooseInstanceByRandomWeight(theSameClusterNameInstList);
        log.info("发生同集群调用-->当前服务所在集群:{},被调用服务所在集群:{},host:{},port:{}",currentClusterName,toBeChooseInstance.getClusterName(),toBeChooseInstance.getIp(),toBeChooseInstance.getPort());
      }
      return new NacosServer(toBeChooseInstance);
    }catch(NacosException e){
      log.error("同集群优先权重负载均衡算法选择异常:{}",e);
    }
    
    return null;
  }
}
