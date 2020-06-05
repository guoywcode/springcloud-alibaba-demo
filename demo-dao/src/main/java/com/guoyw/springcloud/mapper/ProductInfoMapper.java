package com.guoyw.springcloud.mapper;


import com.guoyw.springcloud.entity.ProductInfo;

public interface ProductInfoMapper {

    ProductInfo selectProductInfoById(String productNo);
}
