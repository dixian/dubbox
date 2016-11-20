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

    public static EOPResponseRoot defaultResponse(EOPResponseHeader header,Object content) {
        EOPResponseResult result = new EOPResponseResult();
        result.setTcpCont(header);
        result.setSvcCont(content);
        return new EOPResponseRoot(result);
    }

    public static EOPResponseRoot ok(EOPResponseHeader header,Object content) {
        return EOPResponseRoot.defaultResponse(header, content);
    }


    public static EOPResponseRoot err(){
        return  EOPResponseRoot.ok(EOPResponseHeader.err(),null );
    }
    public static EOPResponseRoot err(Object content){
        return  EOPResponseRoot.ok(EOPResponseHeader.err(),content );
    }

    /**
     *
     * ContractRoot 是一个 {##EOPResponseResult## }对象
     ContractRoot : {
        //TcpCont 是 {##EOPResponseHeader##} 对象
         "TcpCont":{
             "TransactionID":"1001000101201602021234567890",
             "ActionCode":"1",
             "RspTime":"20160202145959245",
            // Response 是一个 {##EOPResponseStatus##} 对象
             "Response":{ //
                 "RspType":"0",
                 "RspCode":"0000",
                 "RspDesc":"操作成功"
             }
         },
         "SvcCont":{ data object }

     }
     */
}
