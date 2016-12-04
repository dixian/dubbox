package com.telecomjs.service.impl;

import com.telecomjs.beans.OfferInstBean;
import com.telecomjs.entities.OfferInst;
import com.telecomjs.mapper.OfferInstMapper;
import com.telecomjs.mapper.OfferMapper;
import com.telecomjs.service.intf.OfferService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zark on 16/11/13.
 */
@Service
public class OfferServiceImpl implements OfferService {
    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private OfferInstMapper offerInstMapper;
    @Autowired
    private OfferMapper offerMapper;

    @Override
    public List<OfferInstBean> queryOfferInstByProdId(String prodId){
        if (prodId == null) return null;
        List<OfferInst> list =offerInstMapper.queryByProdId(prodId);
        List<OfferInstBean> beans = new LinkedList<OfferInstBean>();
        for (OfferInst inst : list){
            OfferInstBean bean = new OfferInstBean();
            BeanUtils.copyProperties(inst, bean);
            beans.add(bean);
        }
        return beans;
    }
}
