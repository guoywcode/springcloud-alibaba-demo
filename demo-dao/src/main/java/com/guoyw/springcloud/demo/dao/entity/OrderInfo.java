package com.guoyw.springcloud.demo.dao.entity;

import lombok.Data;

import java.util.Date;


@Data
public class OrderInfo {

    private String orderNo;

    private String userName;

    private Date createDt;

    private String productNo;

    private Integer productCount;


}
