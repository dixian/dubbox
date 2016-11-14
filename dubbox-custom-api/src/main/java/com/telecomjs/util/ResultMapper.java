package com.telecomjs.util;

import com.sun.tools.javac.code.Attribute;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by zark on 16/11/9.
 */
@XmlRootElement
public class ResultMapper implements Serializable {
    public static final String CODE_OK  = "100";
    public static final String CODE_ERR  = "200";
    private String message;
    private Object obj;
    private String code;

    public ResultMapper() {
    }

    public ResultMapper(String message, Object obj, String code) {
        this.message = message;
        this.obj = obj;
        this.code = code;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
