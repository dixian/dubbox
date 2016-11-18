package com.telecomjs.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by zark on 16/11/17.
 */
public class EOPResponseResult implements Serializable {
    private EOPResponseHeader TcpCont;
    private Object SvcCont;

    public EOPResponseHeader getTcpCont() {
        return TcpCont;
    }

    public void setTcpCont(EOPResponseHeader tcpCont) {
        TcpCont = tcpCont;
    }

    public Object getSvcCont() {
        return SvcCont;
    }

    public void setSvcCont(Object svcCont) {
        SvcCont = svcCont;
    }


}
