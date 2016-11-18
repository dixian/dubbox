package com.telecomjs.resources;

import com.alibaba.fastjson.JSON;
import com.telecomjs.beans.ProdInstBean;
import com.telecomjs.constants.EOPConstants;
import com.telecomjs.handlers.ProductHandler;
import com.telecomjs.service.intf.CustomService;
import com.telecomjs.service.intf.ProductService;
import com.telecomjs.vo.CustomerInfo;
import com.telecomjs.vo.ProductInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zark on 16/11/14.
 */
@Component
@Path("/cust")
public class CustomerResource {
    Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private CustomService customService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;


    /*@Path("{id : \\w+}")
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response getCustomer(@PathParam("id")String partyId){
        logger.warn("getCustomer : "+partyId);

        try {

            CustomerInfo pi = customService.getCustom(partyId);
            System.out.println(JSON.toJSONString(pi));
            return Response.ok(pi).build();

        }
        catch (Exception e){
            e.printStackTrace();
            return Response.status(404).build();
        }

    }*/

    private void debugThreadPool(){
        logger.debug("ThreadPool ActiveCount : "+taskExecutor.getActiveCount());
        logger.debug("ThreadPool PoolSize : "+taskExecutor.getPoolSize());
        logger.debug("ThreadPool CorePoolSize : "+taskExecutor.getCorePoolSize());
        logger.debug("ThreadPool MaxPoolSize : "+taskExecutor.getMaxPoolSize());
    }

    /**
     * 根据客户ID号码查询客户信息
     * @param asyncResponse 异步实现用的中间变量
     * @param customId 请求中传入的客户号码，字母和数字组合
     */
    @GET
    @Path("{customId : \\w+}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public void getCustomer(@Suspended final AsyncResponse asyncResponse
            ,@PathParam("customId") final String customId) {
        logger.debug("asyncResponse is null ? : "+(asyncResponse == null));
        asyncResponse.setTimeoutHandler(new TimeoutHandler() {

            @Override
            public void handleTimeout(AsyncResponse asyncResponse) {
                asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity("Operation time out.").build());
            }
        });
        asyncResponse.setTimeout(500, TimeUnit.MILLISECONDS);
        taskExecutor.execute(new Thread(new Runnable() {

            @Override
            public void run() {
                logger.debug("start ProductHandler(customService).callService()"+customId);
                new ProductHandler(new Object[]{customService,productService},asyncResponse)
                        .resumeService(EOPConstants.M_CALL_QRY_CUST_CUSTINFO,customId);
                //asyncResponse.resume(result);
            }

        }));
        debugThreadPool();
    }


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
            ,@PathParam("accNbr") final String accNbr){
        logger.debug("getCustomerByAccNbr : "+accNbr);

        //logger.debug("asyncResponse is null ? : "+(asyncResponse == null));
        asyncResponse.setTimeoutHandler(new TimeoutHandler() {

            @Override
            public void handleTimeout(AsyncResponse asyncResponse) {
                asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity("Operation time out.").build());
            }
        });
        asyncResponse.setTimeout(50, TimeUnit.SECONDS);
        taskExecutor.execute(new Thread(new Runnable() {

            @Override
            public void run() {
                logger.debug("start ProductHandler(customService).callService().getCustomerByAccNbr"+accNbr);
                new ProductHandler(new Object[]{customService,productService},asyncResponse)
                        .resumeService(EOPConstants.M_CALL_QRY_CUST_CUSTINFO_BYNBR,accNbr);
                //asyncResponse.resume(result);
            }

        }));
        debugThreadPool();
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
