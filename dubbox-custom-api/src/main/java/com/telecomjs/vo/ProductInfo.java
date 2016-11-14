package com.telecomjs.vo;

import com.telecomjs.beans.OfferInstBean;
import com.telecomjs.beans.ProdInstBean;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by zark on 16/11/9.
 */

@XmlRootElement
public class ProductInfo implements Serializable {

    private ProdInstBean prodInst;
    private List<OfferInstBean> offerList;

    public ProductInfo() {
    }


    public ProdInstBean getProdInst() {
        return prodInst;
    }

    public void setProdInst(ProdInstBean prodInst) {
        this.prodInst = prodInst;
    }

    public List<OfferInstBean> getOfferList() {
        return offerList;
    }

    public void setOfferList(List<OfferInstBean> offerList) {
        this.offerList = offerList;
    }
}
