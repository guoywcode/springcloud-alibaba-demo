package com.guoyw.ribbonconfig;

import com.guoyw.springcloud.myrule.WeightedRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: springcloud-alibaba-demo
 * @description:
 * @author: guoyw
 * @create: 2020-06-08 22:49
 **/
@Configuration
public class GlobalRibbonConfig {
  @Bean
  public IRule theSameClusterPriorityRule() {
    return new WeightedRule();
    //return new TheSameClusterPriorityRule();
    //return new TheSameClusterPriorityWithVersionRule();
  }
}
