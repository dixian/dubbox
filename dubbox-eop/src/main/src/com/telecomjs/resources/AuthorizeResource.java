package com.telecomjs.resources;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.telecomjs.constants.EOPConstants;
import com.telecomjs.exceptions.*;
import com.telecomjs.handlers.KeysHandler;
import com.telecomjs.handlers.MessageSender;
import com.telecomjs.handlers.ProductHandler;
import com.telecomjs.messages.RequestMessage;
import com.telecomjs.service.intf.AuthService;
import com.telecomjs.service.intf.CustomService;
import com.telecomjs.service.intf.ProductService;
import com.telecomjs.vo.EOPResponseHeader;
import com.telecomjs.vo.EOPResponseRoot;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zark on 16/11/24.
 */
@Component
@Path("/auth")
public class AuthorizeResource extends AbstractCommonResource {
    Logger logger = Logger.getLogger(this.getClass());
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private AuthService authService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private CustomService customService;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ProductService productService;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ThreadPoolTaskExecutor authTaskExecutor;
    @Autowired
    private MessageSender messageSender;

    @Path("/logon")
    @POST
    @Produces({ MediaType.APPLICATION_JSON})
    public void authorize( @Suspended final AsyncResponse asyncResponse,  String data) {

        logger.debug("post data :"+  data);
        JSONObject json = JSON.parseObject(data);
        if (json == null) {
            asyncResponse.resume(EOPResponseRoot.err("请求参数为空!"));
        }
        final Map args = new HashMap<>();

        Iterator<String> jsonKeys = json.keySet().iterator();
        while (jsonKeys.hasNext()) {
            String jsonKey = jsonKeys.next();
            Object jsonValObj = json.get(jsonKey);
            if (jsonValObj instanceof String) {
                args.put(jsonKey, jsonValObj);
            }
        }

        //final String requestSequence = String.valueOf(System.currentTimeMillis());
        final String requestSequence = KeysHandler.getInstance().generateSequence();
        configResponse(asyncResponse,requestSequence,2);


        authTaskExecutor.execute(new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                logger.debug("start ProductHandler(customService).callService() "+ EOPConstants.M_CALL_AUTHORIZE_APP+args.toString());
                //目前统一按map参数处理
                new ProductHandler(new Object[]{customService,productService,authService},asyncResponse)
                        .resumeService(EOPConstants.M_CALL_AUTHORIZE_APP, args);
                long end = System.currentTimeMillis();
                logger.debug(this.getClass().getName()+".taskExecutor thread durations :" + String.valueOf(end-start));
            }
        }));
    }

    @GET
    @Path("test/{va}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response testWebService(@PathParam("va") long value) throws Exception {
        if((100 / value) > 10) {
            return Response.ok().entity(EOPResponseRoot.ok(EOPResponseHeader.ok(),"OK")).build();
        }
        else if((100 / value) > 8) {
            throw  new EopParameterIsInvalidException();
        }
        else if((100 / value) > 7) {
            throw  new EopServiceConnectionException();
        }
        else if((100 / value) > 6) {
            throw  new EopServiceIsErrorException();
        }
        else if((100 / value) > 5) {
            throw  new EopServiceCallOverflowException();
        }else if((100 / value) > 4) {
            throw  new EopTokenIsInvalidException();
        }
        else if((100 / value) > 3) {
            throw  new EopTokenIsExpiredException();
        }
        return Response.ok().entity(EOPResponseRoot.ok(EOPResponseHeader.ok(),"OK")).build();
    }
}
