package com.guoyw.ribbonconfig;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PayCenterRibbonConfig {

    @Bean
    public IRule roundRobinRule() {
        return new RoundRobinRule();
    }
}
