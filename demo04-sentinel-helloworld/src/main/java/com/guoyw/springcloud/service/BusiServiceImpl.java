package com.guoyw.springcloud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@Slf4j
public class BusiServiceImpl{

    public void doBusi() {
        log.info("执行业务方法:执行时机:{}",new Date());
    }
}
