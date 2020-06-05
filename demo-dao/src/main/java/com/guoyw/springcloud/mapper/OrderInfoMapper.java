package com.guoyw.springcloud.mapper;


import com.guoyw.springcloud.entity.OrderInfo;

public interface OrderInfoMapper {

    OrderInfo selectOrderInfoById(String orderNo);
}
