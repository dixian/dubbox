package com.telecomjs.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by zark on 16/11/17.
 */
public class EOPRequestHeader implements Serializable {
    private EOPRequest TcpCont;
    private Map  SvcCont;

    public EOPRequest getTcpCont() {
        return TcpCont;
    }

    public void setTcpCont(EOPRequest tcpCont) {
        TcpCont = tcpCont;
    }

    public Map getSvcCont() {
        return SvcCont;
    }

    public void setSvcCont(Map svcCont) {
        SvcCont = svcCont;
    }
}
