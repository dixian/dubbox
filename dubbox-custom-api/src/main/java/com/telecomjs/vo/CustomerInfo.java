package com.telecomjs.vo;

import com.telecomjs.beans.CustomerBean;
import com.telecomjs.beans.ProdInstBean;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by zark on 16/11/7.
 */
@XmlRootElement
public class CustomerInfo implements Serializable {
    private CustomerBean customer;
    private List<ProdInstBean> prodlist;

    public CustomerInfo() {
    }

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

    public List<ProdInstBean> getProdlist() {
        return prodlist;
    }

    public void setProdlist(List<ProdInstBean> prodlist) {
        this.prodlist = prodlist;
    }
}
