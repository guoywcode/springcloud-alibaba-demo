package com.guoyw.springcloud.demo.dao.mapper;


import com.guoyw.springcloud.demo.dao.entity.OrderInfo;

public interface OrderInfoMapper {

    OrderInfo selectOrderInfoById(String orderNo);
}
