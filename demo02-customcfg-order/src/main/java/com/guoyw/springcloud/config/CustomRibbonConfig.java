package com.guoyw.springcloud.config;

import com.guoyw.ribbonconfig.GlobalRibbonConfig;
import org.apache.commons.configuration.tree.DefaultConfigurationKey;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

/**
 * @program: springcloud-alibaba-demo
 * @description:
 * @author: guoyw
 * @create: 2020-06-08 22:47
 **/

@RibbonClients(defaultConfiguration = GlobalRibbonConfig.class)
public class CustomRibbonConfig {
}
