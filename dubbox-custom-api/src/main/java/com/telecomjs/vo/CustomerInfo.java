package com.telecomjs.vo;

import java.io.Serializable;

/**
 * Created by zark on 16/11/7.
 */
public class CustomerInfo implements Serializable {
    private String name;
    private String id;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
