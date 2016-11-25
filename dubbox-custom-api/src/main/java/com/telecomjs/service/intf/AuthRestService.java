package com.telecomjs.service.intf;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by zark on 16/11/21.
 */
public interface AuthRestService {

    public Response authUserAndApp(String user, String password, String appkey, String secret);
    public Response getTokenInfo(String token);
    public Response authAndEndToken(String token , String now) ;
    public Response verifyAccessLimit( String token,String domain
            ,String method,String currentDate);
}
