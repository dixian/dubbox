package com.telecomjs.service.intf;

import com.telecomjs.vo.ProductInfo;

/**
 * Created by zark on 16/11/9.
 */
public interface ProductService {
    public ProductInfo getProductByNbr(String accNbr);
}
