package com.telecomjs.vo;

import com.telecomjs.constants.EOPConstants;

import java.io.Serializable;

/**
 * Created by zark on 16/11/17.
 */
public class EOPResponseStatus implements Serializable {
    private  int RspType ;
    private  String  RspCode;
    private  String RspDesc;

    public int getRspType() {
        return RspType;
    }

    public void setRspType(int rspType) {
        RspType = rspType;
    }

    public String getRspCode() {
        return RspCode;
    }

    public void setRspCode(String rspCode) {
        RspCode = rspCode;
    }

    public String getRspDesc() {
        return RspDesc;
    }

    public void setRspDesc(String rspDesc) {
        RspDesc = rspDesc;
    }

    public static  EOPResponseStatus  ok() {
        EOPResponseStatus res = new EOPResponseStatus();
        res.setRspType(EOPConstants.TCPCONTENT_OK_RSPTYPE);
        res.setRspCode(EOPConstants.TCPCONTENT_OK_RSPCODE);
        res.setRspDesc(EOPConstants.TCPCONTENT_OK_RSPDESC);
        return res;
    }

    public static  EOPResponseStatus err() {
        EOPResponseStatus res = new EOPResponseStatus();
        res.setRspType(EOPConstants.TCPCONTENT_ERROR_RSPTYPE);
        res.setRspCode(EOPConstants.TCPCONTENT_ERROR_RSPCODE);
        res.setRspDesc(EOPConstants.TCPCONTENT_ERROR_RSPDESC);
        return res;
    }
}
