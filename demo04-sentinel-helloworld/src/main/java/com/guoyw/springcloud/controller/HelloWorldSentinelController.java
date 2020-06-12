package com.guoyw.springcloud.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author: guoyw
 * create: 2020-06-10 11:11
 **/
@Slf4j
@RestController
public class HelloWorldSentinelController{
  
 /* *//**
   * 初始化流控规则
   *//*
  @PostConstruct
  public void init() {
    
    List<FlowRule> flowRules = new ArrayList<>();
    
    *//**
     * 定义 helloSentinelV1 受保护的资源的规则
     *//*
    //创建流控规则对象
    FlowRule flowRule = new FlowRule();
    //设置流控规则 QPS
    flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
    //设置受保护的资源
    flowRule.setResource("helloSentinelV1");
    //设置受保护的资源的阈值
    flowRule.setCount(1);
    
    
    
    *//**
     * 定义 helloSentinelV2 受保护的资源的规则
     *//*
    //创建流控规则对象
    FlowRule flowRule2 = new FlowRule();
    //设置流控规则 QPS
    flowRule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
    //设置受保护的资源
    flowRule2.setResource("helloSentinelV2");
    //设置受保护的资源的阈值
    flowRule2.setCount(1);
    
    
    *//**
     * 定义 helloSentinelV3 受保护的资源的规则
     *//*
    //创建流控规则对象
    FlowRule flowRule3 = new FlowRule();
    //设置流控规则 QPS
    flowRule3.setGrade(RuleConstant.FLOW_GRADE_QPS);
    //设置受保护的资源
    flowRule3.setResource("helloSentinelV3");
    //设置受保护的资源的阈值
    flowRule3.setCount(1);
    
    
    
    flowRules.add(flowRule);
    flowRules.add(flowRule2);
    flowRules.add(flowRule3);
    
    //加载配置好的规则
    FlowRuleManager.loadRules(flowRules);
    
    DegradeRule degradeRule = new DegradeRule();
    degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
    degradeRule.setCount(1);
    degradeRule.setTimeWindow(10);
    degradeRule.setResource("testRt");
    
    DegradeRuleManager.loadRules(Arrays.asList(degradeRule));
  }
  */
}
