package com.telecomjs.service.intf;

import com.telecomjs.util.ResultMapper;
import com.telecomjs.vo.ProductInfo;

/**
 * Created by zark on 16/11/9.
 */
public interface ProductRestService {
    public ResultMapper getProductByNbr(String accNbr);
}
