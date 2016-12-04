package com.telecomjs.service.impl;


import com.alibaba.fastjson.JSON;
import com.telecomjs.beans.EopAppOnlineBean;
import com.telecomjs.beans.EopResourceAccessLimitBean;
import com.telecomjs.caches.AuthRedisCache;
import com.telecomjs.constants.EOPConstants;
import com.telecomjs.entities.*;
import com.telecomjs.mapper.*;
import com.telecomjs.service.intf.AuthService;
import com.telecomjs.utils.CodeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by zark on 16/11/21.
 */
@SuppressWarnings("Duplicates")
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
    @Autowired
    private SimpleCacheManager cacheManager;
    @Autowired
    private ThreadPoolTaskExecutor authTaskExecutor ;

    private final String dbname = "auth";

    @Transactional
    @Override
    public EopAppOnlineBean authUserAndApp(String username, String password, String apikey, String secret) {

        logger.info("authUserAndApp be invoked . "
                +String.format("Parameters(username=%s,password=%s,apikey=%s,secret=%s).",username,password,apikey,secret));
        EopUser user =eopUserMapper.getUserByNameAndPassword(username, CodeUtil.encodePassword(password));
        if (user == null) return null;
        logger.debug("apikey : "+apikey+", secret : "+secret);
        EopApp app = eopAppMapper.getAppByKeyAndSecret(apikey,secret);
        if (app == null) return  null;
        EopAppOnline online = new EopAppOnline();
        String token = CodeUtil.generateToken();
        online.setApikey(app.getApikey());
        online.setAppId(app.getAppId());
        online.setAppSecret(app.getAppSecret());
        online.setClientIp(null);
        online.setStatus(EOPConstants.APP_ONLINE_STATUS_RDY);
        online.setOnlineId(System.currentTimeMillis());
        online.setCreateDate(  new java.util.Date());
        online.setToken(token);
        online.setTokenStamp(String.valueOf(System.currentTimeMillis()));
        logger.debug("app_online for inserting : " + JSON.toJSONString(online));
        EopAppOnlineBean bean = new EopAppOnlineBean();
        logger.debug("app_online token : " + online.getToken());
        //异步方式
        //存入数据库
        ///eopAppOnlineMapper.insert(online);//存入数据库
        if (authTaskExecutor != null)
            authTaskExecutor.execute(new insertToken(eopAppOnlineMapper,online));
        //载入缓存
        AuthRedisCache authCache = (AuthRedisCache) cacheManager.getCache(dbname);
        authCache.put(generateOnlineKey(token),online);
        BeanUtils.copyProperties(online,bean);
        return bean;
    }

    @Override
    public EopAppOnlineBean getTokenInfo(String token) {
        logger.info("getTokenInfo be invoked . "
                +String.format("Parameters(token=%s).",token));
        EopAppOnline online = getCachedAppOnline(token);
        if (online != null){
            logger.debug("app_online  : " + JSON.toJSONString(online));
            EopAppOnlineBean bean  = new EopAppOnlineBean();
            BeanUtils.copyProperties(online,bean);
            return bean;
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public EopAppOnlineBean authAndEndToken(String token ,String now) {
        logger.info("authAndEndToken be invoked . "
                +String.format("Parameters(token=%s,currenttimestamp=%s).",token,now));
        //EopAppOnline online = eopAppOnlineMapper.getAppOnLineByToken(token);
        EopAppOnline online = getCachedAppOnline(token);
        if (online == null)
            return null;
        long before = Long.parseLong(online.getTokenStamp());

        long diff= Long.parseLong(now) - before;
        logger.debug("token:"+online.getToken()+"now :"+now+",before:"+before+",diff:"+String.valueOf(diff));

        if (diff > 7200*1000) {
            online.setStatus(EOPConstants.APP_ONLINE_STATUS_END);
            /*eopAppOnlineMapper.updateAppOnLineByToken(online);
            eopAppOnlineMapper.archiveOnlineToken(token);
            eopAppOnlineMapper.deleteOnlineToken(token);*/
            if (authTaskExecutor != null)
                authTaskExecutor.execute(new updateAndArchiveToken(eopAppOnlineMapper,token));
            //new Thread(new updateAndArchiveToken(eopAppOnlineMapper,token)).start();
            //更新内存数据
            removeAppOnlineKey(generateOnlineKey(token));
            return null;
        }
        EopAppOnlineBean bean = new EopAppOnlineBean();
        BeanUtils.copyProperties(online,bean);
        return bean;
    }

    @Transactional
    @Override
    public EopResourceAccessLimitBean verifyAccessLimit(String token, String domain, String method,String currentDate) {
        logger.info("verifyAccessLimit be invoked . "
                +String.format("Parameters(token=%s,domain=%s,method=%s,currentDate=%s).",token,domain,method,currentDate));
        long appId;
        EopAppOnlineBean online = authAndEndToken(token,String.valueOf(System.currentTimeMillis()));
        if (online == null) //如果token有问题，直接返回
            return null;
        logger.debug("online bean: "+JSON.toJSONString(online));
        appId = online.getAppId();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String curr = sdf.format(new Date());


        try {
            String accessKey = generateAccessKey(appId,method,curr);
                    //"EopAppAccess#selectByAppIdAndDate#"+String.valueOf(appId)+','+method+","+curr;
            AuthRedisCache authCache = (AuthRedisCache) cacheManager.getCache(dbname);
            long accessTimes = authCache.getIncrementValue(accessKey);
            //更新计数器
            authCache.incrementValue(accessKey,1l);
            // "'EopAppAccess#'+ #root.methodName+'#'+T(java.lang.String).valueOf(#p0)+','+#p1"
            //String key = "EopAppAccess#selectByAppIdAndDate#"+String.valueOf(appId)+','+curr;
            //Object obj = authCache.get(key);
            logger.debug("cached object , key = " + accessKey +",value = "+accessTimes);
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
                if (domainFlag && methodFlag && accessTimes <= limit.getLimitValue()) {
                    BeanUtils.copyProperties(limit,bean);
                    return bean;
                }
            }

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        //获取并更新已访问次数，如果首次访问，创新访问记录
        /*EopAppAccess access = eopAppAccessMapper.selectByAppIdAndDate(appId,curr);
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
                logger.debug("inserting APP ACCESS failed:"+JSON.toJSONString(access));
                new Thread(new UpdateAppAccess(eopAppAccessMapper,appId,curr,false)).start();
                return null;//无法创建访问记录 直接退出
            }
        }*/


        return null;
    }



    //刷新访问次数
    @SuppressWarnings("Duplicates")
    @Transactional
    class UpdateAppAccess implements Runnable {
        private EopAppAccessMapper eopAppAccessMapper;
        private long appId;
        private String curr;
        private boolean updateFlag ;

        public UpdateAppAccess(EopAppAccessMapper eopAppAccessMapper, long appId, String curr, boolean updateFlag) {
            this.eopAppAccessMapper = eopAppAccessMapper;
            this.appId = appId;
            this.curr = curr;
            this.updateFlag = updateFlag;
        }

        public UpdateAppAccess(EopAppAccessMapper eopAppAccessMapper, long appId, String curr) {
            this(eopAppAccessMapper,appId,curr,true);//更新记录
        }



        @Override
        public void run() {
            if (this.eopAppAccessMapper != null){
                if (updateFlag ) {
                    eopAppAccessMapper.updateByAppIdAndDate(appId, curr);//更新数据
                    eopAppAccessMapper.selectByAppIdAndDate(appId, curr);//缓存到redis
                }
                else {
                    //只读到缓存
                    eopAppAccessMapper.selectByAppIdAndDate(appId, curr);//缓存到redis
                }
            }

        }
    }

    @Transactional
    //将online记录移出在线表
    class updateAndArchiveToken implements Runnable{
        private EopAppOnlineMapper eopAppOnlineMapper;
        private String token;

        public updateAndArchiveToken(EopAppOnlineMapper eopAppOnlineMapper, String token) {
            this.eopAppOnlineMapper = eopAppOnlineMapper;
            this.token = token;
        }

        @Override
        public void run() {
            updateAndArchive(token);
        }

        private void updateAndArchive(String token){
            if (eopAppOnlineMapper != null){
                eopAppOnlineMapper.updateArchiveAppOnLineByToken(token);
                eopAppOnlineMapper.archiveOnlineToken(token);
                eopAppOnlineMapper.deleteOnlineToken(token);
            }
        }

    }

    @Transactional
    class insertToken implements Runnable {
        private EopAppOnlineMapper mapper;
        private EopAppOnline online;

        public insertToken(EopAppOnlineMapper eopAppOnlineMapper, EopAppOnline online) {
            this.mapper = eopAppOnlineMapper;
            this.online = online;
        }


        @Override
        public void run() {
            mapper.insert(online);
        }
    }

    private String generateOnlineKey(String token){
        return "EopAppOnline#getAppOnLineByToken#"+token;
    }

    private String generateAccessKey(long appId,String method,String curr){
        return "EopAppAccess#selectByAppIdAndDate#"+String.valueOf(appId)+','+method+","+curr;
    }
    private EopAppOnline getCachedAppOnline(String token){
        try {
            AuthRedisCache authCache = (AuthRedisCache) cacheManager.getCache(dbname);
            return authCache.get(generateOnlineKey(token), EopAppOnline.class);
        } catch (Exception e){
            return null;
        }
    }

    private void removeAppOnlineKey(String key){

        try {
            AuthRedisCache authCache = (AuthRedisCache) cacheManager.getCache(dbname);
            authCache.evict(key);
        } catch (Exception e){
        }
    }
}
