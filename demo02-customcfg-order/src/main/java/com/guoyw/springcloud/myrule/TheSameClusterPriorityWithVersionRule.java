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
 * create: 2020-06-09 11:07
 **/

@Slf4j
public class TheSameClusterPriorityWithVersionRule extends AbstractLoadBalancerRule{
  
  @Autowired
  private NacosDiscoveryProperties nacosDiscoveryProperties;
  
  @Override
  public void initWithNiwsConfig(IClientConfig iClientConfig){
  
  }
  
  @Override
  public Server choose(Object o){
    try{
      
      // 获取当前服务的集群名称
      String currentClusterName = nacosDiscoveryProperties.getClusterName();
      
      //去nacos上获取和本地 相同集群   相同版本的所有实例信息
      List<Instance> theSameClusterNameAndTheSameVersionInstList = getTheSameClusterAndTheSameVersionInstances(nacosDiscoveryProperties);
      //声明被调用的实例
      Instance toBeChooseInstance;
      
      //判断同集群同版本号的微服务实例是否为空
      if(theSameClusterNameAndTheSameVersionInstList.isEmpty()){
        //跨集群调用相同的版本
        toBeChooseInstance = crossClusterAndTheSameVersionInovke(nacosDiscoveryProperties);
      }else{
        //具有同集群  同版本号的实例
        toBeChooseInstance = WeightedBalancer.chooseInstanceByRandomWeight(theSameClusterNameAndTheSameVersionInstList);
        log.info("同集群同版本调用--->当前微服务所在集群:{},被调用微服务所在集群:{},当前微服务的版本:{},被调用微服务版本:{},Host:{},Port:{}",
          currentClusterName, toBeChooseInstance.getClusterName(), nacosDiscoveryProperties.getMetadata().get("current-version"),
          toBeChooseInstance.getMetadata().get("current-version"), toBeChooseInstance.getIp(), toBeChooseInstance.getPort());
      }
      
      return new NacosServer(toBeChooseInstance);
    }catch(Exception e){
      log.error("同集群优先权重负载均衡算法选择异常:{}", e);
    }
    return null;
  }
  
  // 获取相同集群下,相同版本的 所有实例
  private List<Instance> getTheSameClusterAndTheSameVersionInstances(NacosDiscoveryProperties discoveryProperties) throws NacosException{
    // 获取当前集群的名称
    String currentClusterName = discoveryProperties.getClusterName();
    // 获取版本号
    String currentVersion = discoveryProperties.getMetadata().get("version");
    // 获取所有集群
    List<Instance> allInstance = getAllInstances(discoveryProperties);
    List<Instance> theSameClusterNameAndTheSameVersionInstList = new ArrayList<>();
    
    // 过滤相同集群
    for(Instance instance : allInstance){
      if(StringUtils.endsWithIgnoreCase(instance.getClusterName(), currentClusterName) &&
        StringUtils.endsWithIgnoreCase(instance.getMetadata().get("version"), currentVersion)){
        theSameClusterNameAndTheSameVersionInstList.add(instance);
      }
    }
    return theSameClusterNameAndTheSameVersionInstList;
  }
  
  // 获取被调用服务的所有实例
  private List<Instance> getAllInstances(NacosDiscoveryProperties discoveryProperties) throws NacosException{
    //获取一个负载均衡器对象
    BaseLoadBalancer loadBalancer = (BaseLoadBalancer) getLoadBalancer();
    
    // 获取当前调用的服务的名称
    String invokedServiceName = loadBalancer.getName();
    
    // 获取nacos clinet的服务注册发现组件api
    NamingService namingService = discoveryProperties.namingServiceInstance();
    
    // 获取所有服务实例
    List<Instance> allInstance = namingService.getAllInstances(invokedServiceName);
    
    return allInstance;
  }
  
  /**
   * 方法实现说明:跨集群环境下 相同版本的
   * @author:smlz
   * @param discoveryProperties
   * @return: List<Instance>
   * @exception: NacosException
   * @date:2019/11/21 17:11
   */
  private List<Instance> getCrossClusterAndTheSameVersionInstList(NacosDiscoveryProperties discoveryProperties) throws NacosException {
    
    //版本号
    String currentVersion = discoveryProperties.getMetadata().get("current-version");
    
    //被调用的所有实例
    List<Instance> allInstance = getAllInstances(discoveryProperties);
    
    List<Instance>  crossClusterAndTheSameVersionInstList = new ArrayList<>();
    
    //过滤相同版本
    for(Instance instance : allInstance) {
      if(StringUtils.endsWithIgnoreCase(instance.getMetadata().get("current-version"),currentVersion)) {
        
        crossClusterAndTheSameVersionInstList.add(instance);
      }
    }
    
    return crossClusterAndTheSameVersionInstList;
  }
  
  private Instance crossClusterAndTheSameVersionInovke(NacosDiscoveryProperties discoveryProperties) throws NacosException {
    
    //获取所有集群下相同版本的实例信息
    List<Instance>  crossClusterAndTheSameVersionInstList = getCrossClusterAndTheSameVersionInstList(discoveryProperties);
    //当前微服务的版本号
    String currentVersion = discoveryProperties.getMetadata().get("current-version");
    //当前微服务的集群名称
    String currentClusterName = discoveryProperties.getClusterName();
    
    //声明被调用的实例
    Instance toBeChooseInstance = null ;
    
    //没有对应相同版本的实例
    if(crossClusterAndTheSameVersionInstList.isEmpty()) {
      log.info("跨集群调用找不到对应合适的版本当前版本为:currentVersion:{}",currentVersion);
      throw new RuntimeException("找不到相同版本的微服务实例");
    }else {
      toBeChooseInstance = WeightedBalancer.chooseInstanceByRandomWeight(crossClusterAndTheSameVersionInstList);
      
      log.info("跨集群同版本调用--->当前微服务所在集群:{},被调用微服务所在集群:{},当前微服务的版本:{},被调用微服务版本:{},Host:{},Port:{}",
        currentClusterName,toBeChooseInstance.getClusterName(),discoveryProperties.getMetadata().get("current-version"),
        toBeChooseInstance.getMetadata().get("current-version"),toBeChooseInstance.getIp(),toBeChooseInstance.getPort());
    }
    
    return toBeChooseInstance;
  }
}
