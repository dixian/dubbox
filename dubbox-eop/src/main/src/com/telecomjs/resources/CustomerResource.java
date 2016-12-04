package com.telecomjs.resources;

import com.alibaba.fastjson.JSON;
import com.telecomjs.beans.ProdInstBean;
import com.telecomjs.constants.EOPConstants;
import com.telecomjs.handlers.*;
import com.telecomjs.messages.RequestMessage;
import com.telecomjs.service.intf.CustomService;
import com.telecomjs.service.intf.ProductService;
import com.telecomjs.vo.CustomerInfo;
import com.telecomjs.vo.EOPResponseHeader;
import com.telecomjs.vo.EOPResponseRoot;
import com.telecomjs.vo.ProductInfo;
import org.apache.log4j.Logger;
import org.apache.log4j.net.SyslogAppender;
import org.jboss.netty.handler.timeout.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.AbstractComponentDefinition;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.container.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zark on 16/11/14.
 */
@Component
@Path("/cust")
public class CustomerResource extends AbstractCommonResource  {

    Logger logger = Logger.getLogger(this.getClass());
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private CustomService customService;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ProductService productService;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private MessageSender messageSender;


    // ----------------- 以下是测试代码 ---------------
    @POST
    @Produces("text/plain")
    @Path("as/{id : \\d+}")
    public String testAsync() throws InterruptedException
    {
        Thread.sleep(6000);
        return "async";
    }


    @Path("/direct/{id : \\w+}")
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response getCustomerForCompare(  final @PathParam("id")String customId){
        return Response.status(
                Response.Status.OK)
                .entity(new EOPResponseRoot().ok(EOPResponseHeader.ok(),customService.getCustom(customId))).build();
    }

    //----------  测试代码结束  -------------

    /**
     * 根据客户编号查询客户信息
     * @param asyncResponse
     * @param customId
     */
    @Path("{id : \\w+}")
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public void getCustomerForQueue(@Suspended final AsyncResponse asyncResponse
            , final @PathParam("id")String customId,@HeaderParam("token") String token){
        logger.info("getCustomerForQueue be invoked . "+String.format("accNbr(customId=%s,token=%s).",customId,token));
        //获取时间戳，作为当前会话的key
        //final String requestSequence = String.valueOf(System.currentTimeMillis());
        final String requestSequence = KeysHandler.getInstance().generateSequence();
        //设置异步响应的超时设置,并缓存异步响应实例
        configResponse(asyncResponse,requestSequence);
        //将请求送到消息队列，等待异步处理。 RequestMessage 是个自定义ObjectMessage
        Map params = new HashMap<String,String>();
        params.put(EOPConstants.M_CALL_AUTHORIZE_TOKEN,token);
        params.put(EOPConstants.M_CALL_QRY_CUST_CUSTINFO_PARAM1,customId);
        messageSender.send(new RequestMessage(EOPConstants.M_CALL_QRY_CUST_CUSTINFO,params
                ,requestSequence,EOPConstants.ASYNC_REQUEST_MESSAGE_PARAM_TYPE_MAP,null));
        final long timestamp = System.currentTimeMillis();
        logger.debug("messageSender.send duration in getCustomerForQueue : "+String.valueOf(timestamp - Long.parseLong(requestSequence)/1000)
                +",key="+requestSequence);
    }



    /**
     * 根据客户ID号码查询客户信息
     * @param asyncResponse 异步实现用的中间变量
     * @param customId 请求中传入的客户号码，字母和数字组合
     */
    /*@GET
    @Path("/pool/{customId : \\w+}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public void getCustomer(@Suspended final AsyncResponse asyncResponse
            ,@PathParam("customId") final String customId,@HeaderParam("token") String token ) {
        logger.debug("asyncResponse is null ? : "+(asyncResponse == null));
        asyncResponse.setTimeoutHandler(new TimeoutHandler() {

            @Override
            public void handleTimeout(AsyncResponse asyncResponse) {
                asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity("Operation time out.").build());
            }
        });
        asyncResponse.setTimeout(500, TimeUnit.MILLISECONDS);
        final Map map = new HashMap();
        map.put(EOPConstants.M_CALL_QRY_CUST_CUSTINFO_PARAM1,customId);
        map.put(EOPConstants.M_CALL_AUTHORIZE_TOKEN,token);
        taskExecutor.execute(new Thread(new Runnable() {

            @Override
            public void run() {
                logger.debug("start ProductHandler(customService).callService()"+customId);
                new ProductHandler(new Object[]{customService,productService},asyncResponse)
                        .resumeService(EOPConstants.M_CALL_QRY_CUST_CUSTINFO,map);
                //asyncResponse.resume(result);
            }

        }));
        debugThreadPool(taskExecutor);
    }
    */

    /**
     * 根据产品号码查询产品实例信息和同客户下所有产品
     * @param asyncResponse
     * @param accNbr 产品接入号码
     */
    @Path("/byaccnbr/{accNbr : \\d+}")
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public void getCustomerByAccNbr(@Suspended final AsyncResponse asyncResponse
            ,@PathParam("accNbr") final String accNbr,@HeaderParam("token") String token){
        logger.info("getCustomerByAccNbr be invoked . "+String.format("accNbr(accNbr=%s,token=%s).",accNbr,token));

        //final String transactionSequence = String.valueOf(System.currentTimeMillis());
        final String transactionSequence = KeysHandler.getInstance().generateSequence();
        configResponse(asyncResponse,transactionSequence);
        //override timeout setting
        asyncResponse.setTimeout(50, TimeUnit.SECONDS);
        final Map map = new HashMap();
        map.put(EOPConstants.M_CALL_QRY_CUST_CUSTINFO_BYNBR_PARAM1,accNbr);
        map.put(EOPConstants.M_CALL_AUTHORIZE_TOKEN,token);

        messageSender.send(new RequestMessage(EOPConstants.M_CALL_QRY_CUST_CUSTINFO_BYNBR,map
                ,transactionSequence,EOPConstants.ASYNC_REQUEST_MESSAGE_PARAM_TYPE_MAP,null));
        final long timestamp = System.currentTimeMillis();
        logger.debug("messageSender.send duration in getCustomerByAccNbr : "+String.valueOf(timestamp - Long.parseLong(transactionSequence)/1000)
                +",key="+transactionSequence);
        debugThreadPool(taskExecutor);
        /**
         * 以下代码是异步方式，已改异步方式。
         */
        /*
        try {

            ProductInfo pi = productService.getProductByNbr(accNbr);

            CustomerInfo result = customService.getCustom(pi.getProdInst().getOwnerCustId());
            List<ProdInstBean> list = productService.queryProductByPartyId(result.getCustomer().getPartyId());
            if (list != null){
                result.setProdlist(list);
            }
            return Response.ok(result).build();

        }
        catch (Exception e){
            e.printStackTrace();
            return Response.status(404).build();
        }*/

    }

}
