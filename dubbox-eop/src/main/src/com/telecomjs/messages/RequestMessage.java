package com.telecomjs.messages;

import javax.ws.rs.container.AsyncResponse;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by zark on 16/11/18.
 */
public class RequestMessage implements Serializable  {
    private String callMethod;
    private String requestSequence;
    private short paramType;
    private Map params;
    private String param;

    public RequestMessage(String callMethod, Map params, String seq,short type,String value) {
        this.callMethod = callMethod;
        this.params = params;
        this.requestSequence = seq;
        this.param = value;
        this.paramType = type;
    }



    public RequestMessage() {
    }

    public String getCallMethod() {
        return callMethod;
    }

    public void setCallMethod(String callMethod) {
        this.callMethod = callMethod;
    }

    public String getRequestSequence() {
        return requestSequence;
    }

    public void setRequestSequence(String requestSequence) {
        this.requestSequence = requestSequence;
    }

    public short getParamType() {
        return paramType;
    }

    public void setParamType(short paramType) {
        this.paramType = paramType;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
