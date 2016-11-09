package com.telecomjs.vo;

import java.io.Serializable;

/**
 * Created by zark on 16/11/9.
 */

public class ProductInfo implements Serializable {

    private String prodInstId;

    private String account;

    private String accNum;

    private String accProdInstId;

    private String ownCustId;

    public String getProdInstId() {
        return prodInstId;
    }

    public void setProdInstId(String prodInstId) {
        this.prodInstId = prodInstId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccNum() {
        return accNum;
    }

    public void setAccNum(String accNum) {
        this.accNum = accNum;
    }

    public String getAccProdInstId() {
        return accProdInstId;
    }

    public void setAccProdInstId(String accProdInstId) {
        this.accProdInstId = accProdInstId;
    }

    public String getOwnCustId() {
        return ownCustId;
    }

    public void setOwnCustId(String ownCustId) {
        this.ownCustId = ownCustId;
    }
}
