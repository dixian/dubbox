package com.telecomjs.handlers;

import com.alibaba.dubbo.rpc.RpcException;
import com.telecomjs.beans.ProdInstBean;
import com.telecomjs.constants.EOPConstants;
import com.telecomjs.service.intf.CustomService;
import com.telecomjs.service.intf.ProductService;
import com.telecomjs.vo.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.PathParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.GenericEntity;
import java.util.List;

/**
 * Created by zark on 16/11/17.
 */
public class ProductHandler {
    Logger logger = Logger.getLogger(ProductHandler.class);

    private ProductService productService;
    private CustomService customService;


    public ProductHandler(Object[] services, AsyncResponse asyncResponse) {
        for (Object svc : services){
            if (svc instanceof ProductService){
                this.productService = (ProductService) svc;
            }
            if (svc instanceof CustomService) {
                this.customService = (CustomService) svc;
            }
        }
        this.asyncResponse = asyncResponse;
    }

    private  AsyncResponse asyncResponse;

    public void setAsyncResponse(AsyncResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
    }

    public void resumeService(String methodName, Object args){
        if (asyncResponse != null)
            this.asyncResponse.resume(callService(methodName, args));
    }
    public GenericEntity callService(String methodName, Object args){
        long start = System.currentTimeMillis();
        GenericEntity  result = null;
        if (methodName.equals(EOPConstants.M_CALL_QRY_CUST_CUSTINFO)) {

            EOPResponseRoot res = getCustomer((String) args );
            result = new GenericEntity<EOPResponseRoot>(res){};
        }
        else if (methodName.equals(EOPConstants.M_CALL_QRY_CUST_CUSTINFO_BYNBR)){
            result =  new GenericEntity<EOPResponseRoot>(getCustomerByAccNbr((String)args)){};
        }
        else if (methodName.equals(EOPConstants.M_CALL_QRY_CUST_PRODUCT_BYNBR)){
            result =  new GenericEntity<EOPResponseRoot>(getProductByNbr((String)args)){};
        }
        long end = System.currentTimeMillis();
        logger.debug(this.getClass().getName()+".callService durations :" + String.valueOf(end-start));
        return result;
    }

    public EOPResponseRoot getCustomer(String partyId) {
        if (customService == null ) return null;
        CustomerInfo content =  customService.getCustom(partyId);
        return new EOPResponseRoot().ok(EOPResponseHeader.ok(),content);
    }

    public EOPResponseRoot getCustomerByAccNbr(String accNbr) {

        ProductInfo pi = productService.getProductByNbr(accNbr);
        CustomerInfo result = customService.getCustom(pi.getProdInst().getOwnerCustId());
        /*List<ProdInstBean> list = productService.queryProductByPartyId(result.getCustomer().getPartyId());
        if (list != null){
            result.setProdlist(list);
        }*/
        return new EOPResponseRoot().ok(EOPResponseHeader.ok(),result);
    }

    public EOPResponseRoot getProductByNbr(String accNbr){

        try {
            ProductInfo result = productService.getProductByNbr(accNbr);
            return new EOPResponseRoot().ok(EOPResponseHeader.ok(), result);
        } catch (RpcException e) {
            return new EOPResponseRoot().err("服务调用错误.");
        }
    }
}
