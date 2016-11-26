package com.telecomjs.handlers;

import com.alibaba.dubbo.common.store.support.SimpleDataStore;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.telecomjs.beans.EopAppAccessBean;
import com.telecomjs.beans.EopAppOnlineBean;
import com.telecomjs.beans.EopResourceAccessLimitBean;
import com.telecomjs.beans.ProdInstBean;
import com.telecomjs.constants.EOPConstants;
import com.telecomjs.exceptions.*;
import com.telecomjs.service.intf.AuthService;
import com.telecomjs.service.intf.CustomService;
import com.telecomjs.service.intf.ProductService;
import com.telecomjs.vo.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedProperties;
import org.springframework.stereotype.Component;

import javax.ws.rs.PathParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.GenericEntity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zark on 16/11/17.
 */
public class ProductHandler {
    Logger logger = Logger.getLogger(ProductHandler.class);

    private ProductService productService;
    private CustomService customService;
    private AuthService authService;


    public ProductHandler(Object[] services, AsyncResponse asyncResponse) {
        for (Object svc : services){
            //产品服务
            if (svc instanceof ProductService){
                this.productService = (ProductService) svc;
            }
            //客户服务
            if (svc instanceof CustomService) {
                this.customService = (CustomService) svc;
            }
            //验证服务
            if (svc instanceof AuthService) {
                this.authService = (AuthService) svc;
            }
        }
        this.asyncResponse = asyncResponse;
    }

    private  AsyncResponse asyncResponse;

    /*public void authorize(Map args) {
        if (asyncResponse == null)
            asyncResponse.resume(new GenericEntity<EOPResponseRoot>(EOPResponseRoot.err(EopExceptionText.ASYNC_RESPONSE_IS_INVALID)){});
        try {
            this.asyncResponse.resume(authUserAndApp(args) );
        } catch (Exception e) {
            if (e instanceof EopAppException){
                asyncResponse.resume(EOPResponseRoot.err(((EopAppException) e).getText()));
            }
            else {
                e.printStackTrace();
                asyncResponse.resume(new GenericEntity<EOPResponseRoot>(EOPResponseRoot.err(EopExceptionText.HANDLER_ERROR_MESSAGE)){});
            }
        }
    }*/


    public void resumeService(String methodName, Map args){

        logger.debug("resumeService parameters : "+methodName+'#'+ JSON.toJSONString(args));
        if (asyncResponse == null)
            throw new EopResponseIsInvalidException();
        if (methodName == null )
            asyncResponse.resume(new GenericEntity<EOPResponseRoot>(EOPResponseRoot.err(EopExceptionText.PARAMETER_IS_INVALID)){});
        if (!(args instanceof Map))
            asyncResponse.resume(new GenericEntity<EOPResponseRoot>(EOPResponseRoot.err("无有效的服务参数!")){});

        try {
            this.asyncResponse.resume(callService(methodName, args));
        } catch (Exception e) {
            if (e instanceof EopAppException){
                asyncResponse.resume(EOPResponseRoot.err(((EopAppException) e).getText()));
            }
            else {
                e.printStackTrace();
                asyncResponse.resume(new GenericEntity<EOPResponseRoot>(EOPResponseRoot.err(EopExceptionText.HANDLER_ERROR_MESSAGE)){});
            }
        }
    }
    private GenericEntity callService(String methodName, Map args){
        long start = System.currentTimeMillis();
        /*if (!(args instanceof Map)) {
            return  new GenericEntity<EOPResponseRoot>(EOPResponseRoot.err("无有效的服务参数!")){};
        }*/
        Map<String,String> map = ((Map)args);
        logger.debug("callService parameters : "+ JSON.toJSONString(args));
        //登录方法不需要验证token和次数
        if (!(EOPConstants.M_CALL_AUTHORIZE_APP.equals(methodName))) {

            String token = map.get(EOPConstants.M_CALL_AUTHORIZE_TOKEN);
            if (token == null) {
                //验证失败，提示错误，直接退出
                //return new GenericEntity<EOPResponseRoot>(EOPResponseRoot.err("无效的token!")) {};
                throw  new EopTokenIsInvalidException();
            }
            //首先验证token是否有效
            EopAppOnlineBean bean = authService.authAndEndToken(token, String.valueOf(start));
            if (bean == null) {
                //验证失败，提示错误，直接退出
                //return new GenericEntity<EOPResponseRoot>(EOPResponseRoot.err("token失效,验证失败!")) {};
                throw  new EopTokenIsExpiredException();
            }
            //验证流量是否超过次数
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDD");
            String curr  = sdf.format(new Date());
            EopResourceAccessLimitBean limitBean = authService.verifyAccessLimit(token,null,methodName,curr);
            logger.debug("callService verifylimit  : "+ JSON.toJSONString(limitBean));
            if (limitBean == null){
                //超过调用次数
                //return  new GenericEntity<EOPResponseRoot>(EOPResponseRoot.err("超过调用次数!")){};
                throw new  EopServiceCallOverflowException();
            }
        }


        try {
            GenericEntity  result = null;
            if (methodName.equals(EOPConstants.M_CALL_QRY_CUST_CUSTINFO)) {

                EOPResponseRoot res = getCustomer(map.get(EOPConstants.M_CALL_QRY_CUST_CUSTINFO_PARAM1) );
                result = new GenericEntity<EOPResponseRoot>(res){};
            }
            else if (methodName.equals(EOPConstants.M_CALL_QRY_CUST_CUSTINFO_BYNBR)){
                result =  new GenericEntity<EOPResponseRoot>(getCustomerByAccNbr(map.get(EOPConstants.M_CALL_QRY_CUST_CUSTINFO_BYNBR_PARAM1))){};
            }
            else if (methodName.equals(EOPConstants.M_CALL_QRY_CUST_PRODUCT_BYNBR)){
                result =  new GenericEntity<EOPResponseRoot>(getProductByNbr(map.get(EOPConstants.M_CALL_QRY_CUST_PRODUCT_BYNBR_PARAM1))){};
            }
            else if (methodName.equals(EOPConstants.M_CALL_AUTHORIZE_APP)){
                result =  new GenericEntity<EOPResponseRoot>(authUserAndApp(map)){};
            }
            long end = System.currentTimeMillis();
            logger.debug(this.getClass().getName()+".callService durations :" + String.valueOf(end-start));
            return result;
        } catch (RpcException e){
            throw  new EopServiceIsErrorException(e);
        } catch (Exception e){
            throw  new EopAppException(EopExceptionText.HANDLER_ERROR_MESSAGE,e);
        }
    }

    private EOPResponseRoot getCustomer(String partyId) {
        if (customService == null )
            throw new EopServiceConnectionException();
        CustomerInfo content = customService.getCustom(partyId);
        return new EOPResponseRoot().ok(EOPResponseHeader.ok(), content);

    }

    private EOPResponseRoot getCustomerByAccNbr(String accNbr) {
        if (productService == null )
            throw new EopServiceConnectionException();
        ProductInfo pi = productService.getProductByNbr(accNbr);
        if (pi == null) {
            return EOPResponseRoot.err("未查询到相关产品号码!");
        }
        else {
            CustomerInfo result = customService.getCustom(pi.getProdInst().getOwnerCustId());
            return new EOPResponseRoot().ok(EOPResponseHeader.ok(),result);
        }
        /*List<ProdInstBean> list = productService.queryProductByPartyId(result.getCustomer().getPartyId());
        if (list != null){
            result.setProdlist(list);
        }*/

    }

    private EOPResponseRoot getProductByNbr(String accNbr){
        if (productService == null)
            throw  new EopServiceConnectionException();

        ProductInfo result = productService.getProductByNbr(accNbr);
        return new EOPResponseRoot().ok(EOPResponseHeader.ok(), result);

    }

    private EOPResponseRoot authUserAndApp(Map args) {
        if (authService == null) //认证服务如果不可用，抛出认证失败的异常。
            throw new EopServiceConnectionException();
        final String username = (String) args.get("username");
        final String password = (String) args.get("password");
        final String apikey = (String) args.get("appkey");
        final String secret = (String) args.get("secret");
        EOPResponseRoot result = null;

        if (username == null)
            throw new EopParameterIsInvalidException("无有效的参数 username !");
        if (password == null)
            throw new EopParameterIsInvalidException("无有效的参数 password !");
        if (apikey == null)
            throw new EopParameterIsInvalidException("无有效的参数 appkey !");
        if (secret == null)
            throw new EopParameterIsInvalidException("无有效的参数 secret !");


        EopAppOnlineBean bean = authService.authUserAndApp(username, password, apikey, secret);
        if (bean == null) {
            result = EOPResponseRoot.err("用户名密码验证失败!");
        }
        else {
            bean.setAppSecret(null);
            result = EOPResponseRoot.ok(EOPResponseHeader.ok(),bean);
        }

        return result;
    }

}
