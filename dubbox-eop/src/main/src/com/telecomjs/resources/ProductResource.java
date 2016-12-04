package com.telecomjs.resources;

import com.telecomjs.constants.EOPConstants;
import com.telecomjs.handlers.AsyncRequestMapHandler;
import com.telecomjs.handlers.KeysHandler;
import com.telecomjs.handlers.MessageSender;
import com.telecomjs.handlers.ProductHandler;
import com.telecomjs.messages.RequestMessage;
import com.telecomjs.service.intf.AuthService;
import com.telecomjs.service.intf.CustomService;
import com.telecomjs.service.intf.ProductRestService;
import com.telecomjs.service.intf.ProductService;
import com.telecomjs.vo.EOPResponseRoot;
import com.telecomjs.vo.ProductInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.container.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zark on 16/11/10.
 */
@Component
@Path("/prod")
@SuppressWarnings("SpringJavaAutowiringInspection")
public class ProductResource extends AbstractCommonResource {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MessageSender messageSender;



    /*@Path("/direct/{accNbr : \\w+}")
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response getProductByDirect(@PathParam("accNbr") final String accNbr) {
        new ProductHandler(new Object[]{customService,productService},null).callService();
    }*/


    @Path("{accNbr : \\w+}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public void getProduct(@Suspended final AsyncResponse asyncResponse, @PathParam("accNbr") final String accNbr
        ,@HeaderParam("token") String token){
        logger.info("getProduct be invoked . "+String.format("accNbr(accNbr=%s,token=%s).",accNbr,token));
        //获取时间戳，作为当前会话的key
        //final String requestSequence = String.valueOf(System.currentTimeMillis());
        final String requestSequence = KeysHandler.getInstance().generateSequence();
        //设置异步响应的超时设置,并缓存异步响应实例
        configResponse(asyncResponse,requestSequence);
        //将请求送到消息队列，等待异步处理。 RequestMessage 是个自定义ObjectMessage
        Map params = new HashMap<String,String>();
        params.put("token",token);
        params.put(EOPConstants.M_CALL_QRY_CUST_CUSTINFO_BYNBR_PARAM1,accNbr);
        messageSender.send(new RequestMessage(EOPConstants.M_CALL_QRY_CUST_PRODUCT_BYNBR,params
                ,requestSequence,EOPConstants.ASYNC_REQUEST_MESSAGE_PARAM_TYPE_MAP,accNbr));
        final long timestamp = System.currentTimeMillis();
        logger.debug("messageSender.send duration  in getProduct . "+String.valueOf(timestamp - Long.parseLong(requestSequence)/1000)
                +",key="+requestSequence);

        //超时的情况下再次处理
        /*asyncResponse.setTimeoutHandler(new TimeoutHandler() {
            @Override
            public void handleTimeout(AsyncResponse asyncResponse) {

                getAsyncRequestMapHandler().removeResponse(requestSequence);
                asyncResponse.resume(Response.status(
                        Response.Status.SERVICE_UNAVAILABLE)
                        .entity(new EOPResponseRoot().err("Operation time out.")).build());
                //超时清楚AsyncResponse的缓存

                final long end = System.currentTimeMillis();
                logger.debug("TIMEOUT duration :" +String.valueOf(end - Long.parseLong(requestSequence))+",key="+requestSequence);
            }
        });*/
    }



}
