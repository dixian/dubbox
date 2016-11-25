package com.telecomjs.service.intf;

import com.telecomjs.beans.EopAppBean;
import com.telecomjs.beans.EopAppOnlineBean;
import com.telecomjs.beans.EopResourceAccessLimitBean;
import com.telecomjs.beans.EopUserBean;

/**
 * Created by zark on 16/11/21.
 */
public interface AuthService {

    public EopAppOnlineBean authUserAndApp(String user,String password,String apikey, String secret);
    public EopAppOnlineBean authAndEndToken(String token ,String now);
    public EopResourceAccessLimitBean verifyAccessLimit(String token,String domain,String method,String currentDate);
    public EopAppOnlineBean getTokenInfo(String token);
}