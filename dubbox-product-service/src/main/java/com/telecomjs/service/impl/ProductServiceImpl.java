package com.telecomjs.service.impl;

import com.telecomjs.entities.ProdInst;
import com.telecomjs.mapper.ProdInstMapper;
import com.telecomjs.service.intf.ProductService;
import com.telecomjs.vo.ProductInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zark on 16/11/9.
 */
@Service
public class ProductServiceImpl implements ProductService {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ProdInstMapper prodInstMapper;

    public ProductInfo getProductByNbr(String accNbr) {
        logger.debug("getProductByNbr : "+accNbr);
        ProductInfo info = null;
        try {
            ProdInst pd =  prodInstMapper.selectByAccNbr(accNbr);
            info = new ProductInfo();
            info.setAccNum(pd.getAccNum());
            info.setAccount(pd.getAccount());
            info.setAccProdInstId(pd.getAccProdInstId());
            info.setOwnCustId(pd.getOwnerCustId());
            info.setProdInstId(pd.getProdInstId());
        }
        catch (Exception e) {
            e.printStackTrace();
            info = null;
        }
        return info;
    }
}
