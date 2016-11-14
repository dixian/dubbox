package com.telecomjs.service.intf;

import com.telecomjs.beans.OfferInstBean;

import java.util.List;

/**
 * Created by zark on 16/11/13.
 */
public interface OfferService {
    public List<OfferInstBean> queryOfferInstByProdId(String prodId);
}
