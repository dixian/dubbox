package com.telecomjs.vo;

import java.io.Serializable;

/**
 * Created by zark on 16/11/17.
 */
public class EOPResponseRoot implements Serializable {
    private EOPResponseResult ContractRoot;

    public EOPResponseRoot() {
    }

    public EOPResponseRoot(EOPResponseResult contractRoot) {
        ContractRoot = contractRoot;
    }

    public EOPResponseResult getContractRoot() {
        return ContractRoot;
    }

    public void setContractRoot(EOPResponseResult contractRoot) {
        ContractRoot = contractRoot;
    }


    public EOPResponseRoot ok(EOPResponseHeader header,Object content) {
        EOPResponseResult result = new EOPResponseResult();
        result.setTcpCont(header);
        result.setSvcCont(content);
        return new EOPResponseRoot(result);
    }
}
