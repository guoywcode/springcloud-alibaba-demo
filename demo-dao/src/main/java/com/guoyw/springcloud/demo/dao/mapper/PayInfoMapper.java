package com.guoyw.springcloud.demo.dao.mapper;

import com.guoyw.springcloud.demo.dao.entity.PayInfo;

public interface PayInfoMapper {

    PayInfo selectPayInfoByOrderNo(String orderNo);
}
