package com.guoyw.springcloud.handler;

import com.guoyw.springcloud.entity.ProductInfo;
import com.guoyw.springcloud.feignapi.sentinel.ProductCenterFeignApiWithSentinel;
import org.springframework.stereotype.Component;

/**
 * Created by smlz on 2019/11/28.
 */
@Component
public class ProductCenterFeignApiWithSentinelFallback implements ProductCenterFeignApiWithSentinel{
    @Override
    public ProductInfo selectProductInfoById(String productNo) {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductName("默认商品");
        return productInfo;
    }
}
