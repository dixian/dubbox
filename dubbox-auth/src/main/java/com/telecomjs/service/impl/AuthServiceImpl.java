package com.telecomjs.service.impl;


import com.alibaba.fastjson.JSON;
import com.telecomjs.beans.EopAppOnlineBean;
import com.telecomjs.beans.EopResourceAccessLimitBean;
import com.telecomjs.constants.EOPConstants;
import com.telecomjs.entities.*;
import com.telecomjs.mapper.*;
import com.telecomjs.service.intf.AuthService;
import com.telecomjs.utils.CodeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by zark on 16/11/21.
 */
@Service
public class AuthServiceImpl implements AuthService {
    Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private EopUserMapper eopUserMapper;
    @Autowired
    private EopAppMapper eopAppMapper;
    @Autowired
    private EopAppOnlineMapper eopAppOnlineMapper;
    @Autowired
    private EopResourceAccessLimitMapper eopResourceAccessLimitMapper;
    @Autowired
    private EopAppAccessMapper eopAppAccessMapper;

    @Override
    public EopAppOnlineBean authUserAndApp(String username, String password, String apikey, String secret) {

        String newPassword = CodeUtil.encodePassword(password);
        logger.debug("oldPassword : "+password+",new password : "+newPassword);
        EopUser user =eopUserMapper.getUserByNameAndPassword(username, CodeUtil.encodePassword(password));
        if (user == null) return null;
        logger.debug("apikey : "+apikey+", secret : "+secret);
        EopApp app = eopAppMapper.getAppByKeyAndSecret(apikey,secret);
        if (app == null) return  null;
        EopAppOnline online = new EopAppOnline();
        online.setApikey(app.getApikey());
        online.setAppId(app.getAppId());
        online.setAppSecret(app.getAppSecret());
        online.setClientIp(null);
        online.setStatus(EOPConstants.APP_ONLINE_STATUS_RDY);
        online.setOnlineId(System.currentTimeMillis());
        online.setCreateDate(  new java.util.Date());
        online.setToken(CodeUtil.generateToken());
        online.setTokenStamp(String.valueOf(System.currentTimeMillis()));
        logger.debug("app_online for inserting : " + JSON.toJSONString(online));
        EopAppOnlineBean bean = new EopAppOnlineBean();
        logger.debug("app_online token : " + online.getToken());
        eopAppOnlineMapper.insert(online);
        BeanUtils.copyProperties(online,bean);
        return bean;
    }

    @Override
    public EopAppOnlineBean getTokenInfo(String token) {
        EopAppOnline online = eopAppOnlineMapper.getAppOnLineByToken(token);
        if (online != null){
            logger.debug("app_online  : " + JSON.toJSONString(online));
            EopAppOnlineBean bean  = new EopAppOnlineBean();
            BeanUtils.copyProperties(online,bean);
            return bean;
        } else {
            return null;
        }
    }

    @Override
    public EopAppOnlineBean authAndEndToken(String token ,String now) {
        EopAppOnline online = eopAppOnlineMapper.getAppOnLineByToken(token);
        if (online == null)
            return null;
        long before = Long.parseLong(online.getTokenStamp());

        long diff= Long.parseLong(now) - before;
        logger.debug("token:"+online.getToken()+"now :"+now+",before:"+before+",diff:"+String.valueOf(diff));
        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.debug("now :"+now+",before:"+System.currentTimeMillis()+",diff:"+String.valueOf(Long.parseLong(now)-System.currentTimeMillis()));
        */
        if (diff > 60*60*1000) {
            online.setStatus(EOPConstants.APP_ONLINE_STATUS_END);
            eopAppOnlineMapper.updateAppOnLineByToken(online);
            return null;
        }
        EopAppOnlineBean bean = new EopAppOnlineBean();
        BeanUtils.copyProperties(online,bean);
        return bean;
    }

    @Override
    public EopResourceAccessLimitBean verifyAccessLimit(String token, String domain, String method,String currentDate) {
        long appId;
        EopAppOnlineBean online = authAndEndToken(token,String.valueOf(System.currentTimeMillis()));
        if (online == null) //如果token有问题，直接返回
            return null;
        logger.debug("online bean: "+JSON.toJSONString(online));
        appId = online.getAppId();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String curr = sdf.format(new java.util.Date());
        //获取并更新已访问次数，如果首次访问，创新访问记录
        EopAppAccess access = eopAppAccessMapper.selectByAppIdAndDate(appId,curr);
        if (access != null) {
            //访问量+1,采用多线程方式
            //eopAppAccessMapper.updateAccessByAppidAndDate(appId,currentDate);
            new Thread(new UpdateAppAccess(eopAppAccessMapper,appId,curr)).start();
        }
        else {
            //第一次访问,阻塞方式
            access = new EopAppAccess(BigDecimal.valueOf(System.currentTimeMillis()),appId,domain,method,curr,0l,new java.util.Date());
            logger.debug("insert APP ACCESS : "+JSON.toJSONString(access));
            if ( eopAppAccessMapper.insert(access) <= 0 ){
                return null;//无法创建访问记录 直接退出
            }
        }

        //先增加访问次数
        List<EopResourceAccessLimit> limits = eopResourceAccessLimitMapper.selectByAppId(appId);
        if (limits == null) //找不到限额的配置，立即返回
            return null;
        //logger.debug(" ACCESS limit : "+JSON.toJSONString(limits));
        for (EopResourceAccessLimit limit : limits ) {
            boolean domainFlag = false;
            boolean methodFlag = false;
            if (domain != null ){ //目前domain字段暂时不用
                if (limit.getDomain().equalsIgnoreCase(domain) || limit.getDomain().equals("*")){
                    domainFlag = true;
                }
            }
            else { //目前不用domain判断
                domainFlag = true;
            }

            if (method != null){
                if (limit.getMethodName().equalsIgnoreCase(method) || limit.getMethodName().equals("*")){
                    methodFlag = true;
                }
            }
            else {
                methodFlag = false;
            }
            EopResourceAccessLimitBean bean = new EopResourceAccessLimitBean();
            if (domainFlag && methodFlag && access.getTimes() <= limit.getLimit()) {
                BeanUtils.copyProperties(limit,bean);
                return bean;
            }
        }
        return null;
    }



    //刷新访问次数
    class UpdateAppAccess implements Runnable {
        private EopAppAccessMapper eopAppAccessMapper;
        private long appId;
        private String curr;

        public UpdateAppAccess(EopAppAccessMapper eopAppAccessMapper, long appId, String curr) {
            this.eopAppAccessMapper = eopAppAccessMapper;
            this.appId = appId;
            this.curr = curr;
        }

        @Override
        public void run() {
            if (this.eopAppAccessMapper != null){
                this.eopAppAccessMapper.updateByAppIdAndDate(appId,curr);
            }

        }
    }
}
