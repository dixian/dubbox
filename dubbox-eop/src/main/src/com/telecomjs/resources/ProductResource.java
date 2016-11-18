package com.telecomjs.resources;

import com.telecomjs.constants.EOPConstants;
import com.telecomjs.handlers.ProductHandler;
import com.telecomjs.service.intf.CustomService;
import com.telecomjs.service.intf.ProductRestService;
import com.telecomjs.service.intf.ProductService;
import com.telecomjs.vo.ProductInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zark on 16/11/10.
 */
@Component
@Path("/prod")
public class ProductResource {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CustomService customService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;


    @Path("{accNbr : \\w+}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public void getProduct(@Suspended final AsyncResponse asyncResponse, @PathParam("accNbr") final String accNbr){
        logger.warn("getProduct : "+accNbr);


        asyncResponse.setTimeoutHandler(new TimeoutHandler() {

            @Override
            public void handleTimeout(AsyncResponse asyncResponse) {
                asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity("Operation time out.").build());
            }
        });
        asyncResponse.setTimeout(5, TimeUnit.SECONDS);
        taskExecutor.execute(new Thread(new Runnable() {

            @Override
            public void run() {
                logger.debug("start ProductHandler(customService).callService().getProduct"+accNbr);
                new ProductHandler(new Object[]{customService,productService},asyncResponse)
                        .resumeService(EOPConstants.M_CALL_QRY_CUST_PRODUCT_BYNBR,accNbr);
                //asyncResponse.resume(result);
            }

        }));
    }
}
