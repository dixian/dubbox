package com.telecomjs.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by zark on 16/11/17.
 */
public class EOPResponseHeader implements Serializable {
    private String TransactionID;
    private String ActionCode;
    private String RspTime;
    private EOPResponseStatus Response;


    public String getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(String transactionID) {
        this.TransactionID = transactionID;
    }

    public String getActionCode() {
        return ActionCode;
    }

    public void setActionCode(String actionCode) {
        ActionCode = actionCode;
    }

    public String getRspTime() {
        return RspTime;
    }

    public void setRspTime(String rspTime) {
        RspTime = rspTime;
    }

    public EOPResponseStatus getResponse() {
        return Response;
    }

    public void setResponse(EOPResponseStatus response) {
        Response = response;
    }
    public static EOPResponseHeader ok() {
        EOPResponseHeader header = new EOPResponseHeader();
        header.setTransactionID("1001000101201602021234567890");
        header.setActionCode("1");
        header.setRspTime("20160202145959245");
        header.setResponse(EOPResponseStatus.ok());
        return  header;
    }

    public static EOPResponseHeader err() {
        EOPResponseHeader header = new EOPResponseHeader();
        header.setTransactionID("1001000101201602021234567890");
        header.setActionCode("1");
        header.setRspTime("20160202145959245");
        header.setResponse(EOPResponseStatus.err());
        return  header;
    }
}
