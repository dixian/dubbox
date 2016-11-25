package com.telecomjs.service.rest;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.telecomjs.beans.EopAppOnlineBean;
import com.telecomjs.beans.EopResourceAccessLimitBean;
import com.telecomjs.service.intf.AuthRestService;
import com.telecomjs.service.intf.AuthService;
import com.telecomjs.vo.EOPResponseHeader;
import com.telecomjs.vo.EOPResponseRoot;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by zark on 16/11/21.
 */
@Path("auth")
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8,ContentType.TEXT_XML_UTF_8})
public class AuthRestServiceImpl implements AuthRestService {

    Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private  AuthService authService;

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @GET
    @Path("{user : \\w+}/{password}/{apikey}/{secret}")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({ContentType.APPLICATION_JSON_UTF_8,ContentType.TEXT_XML_UTF_8})
    public Response authUserAndApp(@PathParam("user") String user, @PathParam("password") String password
            , @PathParam("apikey")String apikey, @PathParam("secret") String secret) {
        logger.debug("user,password,apikey,secret : "+user+password+apikey+secret);
        EopAppOnlineBean bean = authService.authUserAndApp(user,password,apikey,secret);
        return Response.ok().
                entity(  new EOPResponseRoot().ok(EOPResponseHeader.ok(),bean)).
                type(MediaType.APPLICATION_JSON_TYPE).
                build();
    }

    @GET
    @Path("token/{token}/{now}")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({ContentType.APPLICATION_JSON_UTF_8,ContentType.TEXT_XML_UTF_8})
    @Override
    public Response getTokenInfo(String token) {
        return Response.ok().
                entity(  new EOPResponseRoot().ok(EOPResponseHeader.ok(),authService.getTokenInfo(token))).
                type(MediaType.APPLICATION_JSON_TYPE).
                build();
    }



    @GET
    @Path("verifytoken/{token}/{now}")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({ContentType.APPLICATION_JSON_UTF_8,ContentType.TEXT_XML_UTF_8})
    public Response authAndEndToken(@PathParam("token") String token,@PathParam("now") String now) {
        return Response.ok().
                entity(  new EOPResponseRoot().ok(EOPResponseHeader.ok(),authService.authAndEndToken(token,now))).
                type(MediaType.APPLICATION_JSON_TYPE).
                build();
    }

    @POST
    @Path("verifylimit/{token}/{domain}/{method}/{curr}")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({ContentType.APPLICATION_JSON_UTF_8,ContentType.TEXT_XML_UTF_8})
    public Response verifyAccessLimit(@PathParam("token") String token,@PathParam("domain") String domain
            ,@PathParam("method") String method,@PathParam("curr") String currentDate) {
        return Response.ok().
                entity(  new EOPResponseRoot().ok(EOPResponseHeader.ok(),authService.verifyAccessLimit(token,null,method,null))).
                type(MediaType.APPLICATION_JSON_TYPE).
                build();
    }
}
