package com.telecomjs.service.rest;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.telecomjs.constants.DubboxConstants;
import com.telecomjs.service.intf.CustomRestService;
import com.telecomjs.service.intf.CustomService;
import com.telecomjs.util.ResultMapper;
import com.telecomjs.util.ResultMapperUtil;
import com.telecomjs.vo.CustomerInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private CustomService customService;

    public void setCustomService(CustomService customService) {
        this.customService = customService;
    }

    @Override
    @GET
    @Path("{id : \\d+}")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({ContentType.APPLICATION_JSON_UTF_8,ContentType.TEXT_XML_UTF_8})
    public ResultMapper getCustom(@PathParam("id") String customId) {
        if (RpcContext.getContext().getRequest(HttpServletRequest.class) != null) {
            System.out.println("Client IP address from RpcContext: " + RpcContext.getContext().getRequest(HttpServletRequest.class).getRemoteAddr());
        }
        if (RpcContext.getContext().getResponse(HttpServletResponse.class) != null) {
            System.out.println("Response object from RpcContext: " + RpcContext.getContext().getResponse(HttpServletResponse.class));
        }
        return ResultMapperUtil.defaultResultMapper(DubboxConstants.API_MESSAGE_CUSTOM, (Object)customService.getCustom(customId));
    }

    @Override
    public CustomerInfo getCustomByAccNbr(String number) {
        return null;
    }

    @Override
    public List<CustomerInfo> getAccNbrAll(int customId) {
        return null;
    }
}
