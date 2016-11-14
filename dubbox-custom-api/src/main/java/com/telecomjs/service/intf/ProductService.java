package com.telecomjs.service.intf;

import com.telecomjs.beans.ProdInstBean;
import com.telecomjs.vo.ProductInfo;

import java.util.List;

/**
 * Created by zark on 16/11/9.
 */
public interface ProductService {
    public ProductInfo getProductByNbr(String accNbr);
    public List<ProdInstBean> queryProductByPartyId(String partyId);
}
