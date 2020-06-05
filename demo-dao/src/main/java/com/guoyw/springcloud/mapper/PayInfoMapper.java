package com.guoyw.springcloud.mapper;

import com.guoyw.springcloud.entity.PayInfo;

public interface PayInfoMapper {

    PayInfo selectPayInfoByOrderNo(String orderNo);
}
