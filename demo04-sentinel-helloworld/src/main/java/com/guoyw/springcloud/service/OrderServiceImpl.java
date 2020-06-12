package com.guoyw.springcloud.service;

import org.springframework.stereotype.Service;

/**
 * Created by smlz on 2020/2/13.
 */
@Service
public class OrderServiceImpl{

//    @SentinelResource("common")
    public String common() {
        return "common";
    }
}
