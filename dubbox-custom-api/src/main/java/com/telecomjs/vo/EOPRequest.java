package com.telecomjs.vo;

import java.io.Serializable;

/**
 * Created by zark on 16/11/17.
 */
public class EOPRequest implements Serializable {
    private String AppKey;
    private String Method;
    private String TransactionID;
    private String ReqTime;
    private String Sign;
    private String Version;

    public String getAppKey() {
        return AppKey;
    }

    public void setAppKey(String appKey) {
        AppKey = appKey;
    }

    public String getMethod() {
        return Method;
    }

    public void setMethod(String method) {
        Method = method;
    }

    public String getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(String transactionID) {
        TransactionID = transactionID;
    }

    public String getReqTime() {
        return ReqTime;
    }

    public void setReqTime(String reqTime) {
        ReqTime = reqTime;
    }

    public String getSign() {
        return Sign;
    }

    public void setSign(String sign) {
        Sign = sign;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }


}
