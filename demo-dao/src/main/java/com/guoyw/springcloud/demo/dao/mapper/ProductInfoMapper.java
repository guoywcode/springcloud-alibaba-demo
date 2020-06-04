package com.guoyw.springcloud.demo.dao.mapper;


import com.guoyw.springcloud.demo.dao.entity.ProductInfo;

public interface ProductInfoMapper {

    ProductInfo selectProductInfoById(String productNo);
}
