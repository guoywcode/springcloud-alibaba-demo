package com.guoyw.springcloud.demo.dao.entity;

import lombok.Data;

@Data
public class ProductInfo {

    private String productNo;

    private String productName;

    private String productStore;

    private double productPrice;
}
