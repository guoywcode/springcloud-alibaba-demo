package com.guoyw.springcloud.controller;

import com.guoyw.springcloud.entity.OrderInfo;
import com.guoyw.springcloud.entity.ProductInfo;
import com.guoyw.springcloud.feignapi.sentinel.ProductCenterFeignApiWithSentinel;
import com.guoyw.springcloud.mapper.OrderInfoMapper;
import com.guoyw.springcloud.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OrderInfoController{

    @Autowired
    private ProductCenterFeignApiWithSentinel productCenterFeignApiWithSentinel;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @RequestMapping("/selectOrderInfoById/{orderNo}")
    public Object selectOrderInfoById(@PathVariable("orderNo") String orderNo) {

        OrderInfo orderInfo = orderInfoMapper.selectOrderInfoById(orderNo);
        if(null == orderInfo) {
            return "根据orderNo:"+orderNo+"查询没有该订单";
        }

        ProductInfo productInfo = productCenterFeignApiWithSentinel.selectProductInfoById(orderNo);

        if(productInfo == null) {
            return "没有对应的商品";
        }

        OrderVo orderVo = new OrderVo();
        orderVo.setOrderNo(orderInfo.getOrderNo());
        orderVo.setUserName(orderInfo.getUserName());
        orderVo.setProductName(productInfo.getProductName());
        orderVo.setProductNum(orderInfo.getProductCount());

        return orderVo;
    }


}
