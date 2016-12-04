package com.telecomjs.service.impl;

import com.telecomjs.beans.ProdInstBean;
import com.telecomjs.entities.ProdInst;
import com.telecomjs.mapper.ProdInstMapper;
import com.telecomjs.service.intf.OfferService;
import com.telecomjs.service.intf.ProductService;
import com.telecomjs.vo.ProductInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    @Override
    //@Cacheable(value = "product2" )
    public ProductInfo getProductByNbr(String accNbr) {
        logger.debug("getProductByNbr be invoked. " +String.format("Parameters(accNbr=%s).",accNbr));
        ProductInfo info = null;
        try {
            ProdInst prodInst =  prodInstMapper.selectByAccNbr(accNbr);
            info = new ProductInfo();
            ProdInstBean bean = new ProdInstBean();
            BeanUtils.copyProperties(prodInst,bean);
            info.setProdInst(bean);
            //查询产品套餐信息
            if (prodInst != null){
                logger.debug("getProductOfferListByNbr : "+accNbr);
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
        logger.debug("queryProductByPartyId be invoked. " +String.format("Parameters(partyId=%s).",partyId));
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
