package com.telecomjs.handlers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.ws.rs.container.AsyncResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zark on 16/11/18.
 */
@Component
public class AsyncRequestMapHandler {
    private ConcurrentHashMap<String,AsyncResponse> responseHashMap = new ConcurrentHashMap<>();
    private final int warnNumber = 200;
    Logger logger = Logger.getLogger(AsyncRequestMapHandler.class);

    public AsyncResponse getAndRemoveResponse(String key) {
        if (responseHashMap == null) {
            responseHashMap = new ConcurrentHashMap<>();
        }
        AsyncResponse response= responseHashMap.get(key);
        responseHashMap.remove(key);
        return response;
    }

    public void removeResponse(String key,AsyncResponse response) {
        if (responseHashMap == null) {
            return;
        }
        responseHashMap.remove(key,response);
    }

    public void removeResponse(String key) {
        if (responseHashMap == null) {
            return;
        }
        responseHashMap.remove(key);
    }

    public void putResponse(String key,AsyncResponse value) {
        if (responseHashMap == null) {
            responseHashMap = new ConcurrentHashMap<>();
        }

        responseHashMap.put(key,value);
        // 缓存数量超过warnNumber的时候，清理掉垃圾数据。
        if (responseHashMap.size() > warnNumber ) {
            logger.info("Async response pool size : "+responseHashMap.size());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Enumeration<String> sets = responseHashMap.keys();
                    while (sets.hasMoreElements()) {
                        String oldKey = sets.nextElement();
                        Long diff = System.currentTimeMillis() - Long.parseLong(oldKey);
                        if (diff > 3600*1000) { //超过1个小时的都是不正常记录
                            responseHashMap.remove(oldKey);
                        }
                    }
                }
            }).start();
        }
    }
    public int count() {
        return responseHashMap.size();
    }
}
