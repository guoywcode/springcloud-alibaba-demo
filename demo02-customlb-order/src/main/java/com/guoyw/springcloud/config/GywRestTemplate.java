package com.guoyw.springcloud.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.client.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

/**
 * @program: springcloud-alibaba-demo
 * @description:
 * @author: guoyw
 * @create: 2020-06-07 17:02
 **/

@Slf4j
public class GywRestTemplate extends RestTemplate {

  private DiscoveryClient discoveryClient;

  public  GywRestTemplate(DiscoveryClient discoveryClient){
    this.discoveryClient = discoveryClient;
  }


  protected <T> T doExecute(URI url, @Nullable HttpMethod method, @Nullable RequestCallback requestCallback, @Nullable ResponseExtractor<T> responseExtractor) throws RestClientException {
    Assert.notNull(url, "URI is required");
    Assert.notNull(method, "HttpMethod is required");
    ClientHttpResponse response = null;

    T obj;
    try {

      log.info("请求url：{}",url);
       url = replaceurl(url);
      log.info("替换后的路径:{}",url);

      ClientHttpRequest request = this.createRequest(url, method);
      if (requestCallback != null) {
        requestCallback.doWithRequest(request);
      }

      response = request.execute();
      this.handleResponse(url, method, response);
      obj = responseExtractor != null ? responseExtractor.extractData(response) : null;
    } catch (IOException var12) {
      String resource = url.toString();
      String query = url.getRawQuery();
      resource = query != null ? resource.substring(0, resource.indexOf(63)) : resource;
      throw new ResourceAccessException("I/O error on " + method.name() + " request for \"" + resource + "\": " + var12.getMessage(), var12);
    } finally {
      if (response != null) {
        response.close();
      }

    }

    return obj;
  }

  // 根据微服务的名称 去注册中心拉去对应ip进行调用
  private URI replaceurl(URI url){
    //1、从URI中解析调用serviceName=product-center
    String serviceName = url.getHost();
    log.info("调用的微服务名称：{}",serviceName);

    //2、解析我们的请求路径， reqPath = /selectProductInfoById/1
    String reqPath = url.getPath();
    log.info("请求parh：{}",reqPath);

    // 通过微服务的名称去nacos服务端获取 对应的实例列表
    List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances(serviceName);
    if(serviceInstanceList== null || serviceInstanceList.isEmpty())
      throw new RuntimeException("没有可用的微服务实例列表："+serviceName);

    String serviceIp = chooseTargetIp(serviceInstanceList);

    String source =serviceIp + reqPath;
    try {
      return new URI(source);
    } catch (URISyntaxException e) {
      log.error("根据source:{}构建URI异常",source);
    }

    return null;
  }


  // 从微服务列表总 最近选举一个ip
  private String chooseTargetIp(List<ServiceInstance> serviceInstanceList){
    // 采取随机的获取一个
    Random random = new Random();
    Integer randomIndex = random.nextInt(serviceInstanceList.size());
    String ServiceIp = serviceInstanceList.get(randomIndex).getUri().toString();
    log.info("选取的微服务的Ip：{}",ServiceIp);
    return ServiceIp;
  }
}

