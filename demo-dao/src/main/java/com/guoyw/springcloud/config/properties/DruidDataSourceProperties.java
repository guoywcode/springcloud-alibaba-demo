package com.guoyw.springcloud.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: springcloud-alibaba-demo
 * @description:
 * @author: guoyw
 * @create: 2020-06-04 21:58
 **/

@Data
@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DruidDataSourceProperties {
  private String username;

  private String password;

  private String jdbcUrl;

  private String driverClassName;

  private Integer initialSize;

  private Integer maxActive;

  private Integer minIdle;

  private long maxWait;

  private boolean poolPreparedStatements;
}
