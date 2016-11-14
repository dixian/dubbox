package com.telecomjs.service.impl;

import com.mchange.v2.beans.BeansUtils;
import com.telecomjs.beans.OfferInstBean;
import com.telecomjs.beans.ProdInstBean;
import com.telecomjs.entities.OfferInst;
import com.telecomjs.entities.ProdInst;
import com.telecomjs.mapper.OfferInstMapper;
import com.telecomjs.mapper.OfferMapper;
import com.telecomjs.mapper.ProdInstMapper;
import com.telecomjs.service.intf.OfferService;
import com.telecomjs.service.intf.ProductService;
import com.telecomjs.vo.ProductInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zark on 16/11/9.
 */
@Service
public class ProductServiceImpl implements ProductService {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ProdInstMapper prodInstMapper;
    @Autowired
    private OfferService offerService;


    public ProductInfo getProductByNbr(String accNbr) {
        logger.debug("getProductByNbr : "+accNbr);
        ProductInfo info = null;
        try {
            ProdInst prodInst =  prodInstMapper.selectByAccNbr(accNbr);
            info = new ProductInfo();
            ProdInstBean bean = new ProdInstBean();
            BeanUtils.copyProperties(prodInst,bean);
            info.setProdInst(bean);
            //查询产品套餐信息
            if (prodInst != null){
                info.setOfferList(offerService.queryOfferInstByProdId(prodInst.getProdId()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            //info = null;
        }
        return info;
    }

    @Override
    public List<ProdInstBean> queryProductByPartyId(String partyId) {
        List<ProdInstBean> beans = new LinkedList<ProdInstBean>();
        try {
            List<ProdInst> prodInstList =  prodInstMapper.queryByPartyId(partyId);
            for (ProdInst inst : prodInstList){
                ProdInstBean bean = new ProdInstBean();
                BeanUtils.copyProperties(inst,bean);
                beans.add(bean);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            //info = null;
        }
        return beans;
    }


}
