package com.guoyw.springcloud.demo.dao.entity;

import lombok.Data;

import java.util.Date;

@Data
public class PayInfo {

    private String payNo;

    private String orderNo;

    private String userName;

    private Date payTime;
}
