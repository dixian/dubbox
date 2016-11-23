package com.telecomjs.service.rest;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.telecomjs.constants.DubboxConstants;
import com.telecomjs.service.intf.CustomRestService;
import com.telecomjs.service.intf.CustomService;
import com.telecomjs.util.ResultMapper;
import com.telecomjs.util.ResultMapperUtil;
import com.telecomjs.vo.CustomerInfo;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by zark on 16/11/8.
 */
@Path("/custom")
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8,ContentType.TEXT_XML_UTF_8})
public class CustomRestServiceImpl implements CustomRestService {
    Logger logger = Logger.getLogger(CustomService.class);
    private CustomService customService;

    public void setCustomService(CustomService customService) {
        this.customService = customService;
    }

    //dubbo调用的方法

    @GET
    @Path("{id : \\w+}")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({ContentType.APPLICATION_JSON_UTF_8,ContentType.TEXT_XML_UTF_8})
    public CustomerInfo getCustom(@PathParam("id") String customId) {
        logger.debug("CustomRestServiceImpl.getCustom:"+customId);
        return customService.getCustom(customId);
    }




}
