package com.telecomjs.handlers;

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
    private ConcurrentHashMap<String,AsyncResponse> responseHashMap = new ConcurrentHashMap<>(200);

    public AsyncResponse getAndRemoveResponse(String key) {
        if (responseHashMap == null) {
            responseHashMap = new ConcurrentHashMap<>(200);
        }
        AsyncResponse response= responseHashMap.get(key);
        responseHashMap.remove(key);
        return response;
    }

    public void removeResponse(String key) {
        if (responseHashMap == null) {
            return;
        }
        responseHashMap.remove(key);
    }
    public void putResponse(String key,AsyncResponse value) {
        if (responseHashMap == null) {
            responseHashMap = new ConcurrentHashMap<>(200);
        }

        responseHashMap.put(key,value);
        // 缓存数量超过一半的时候，清理掉垃圾数据。
        if (responseHashMap.size() > 100 ) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Enumeration<String> sets = responseHashMap.keys();
                    while (sets.hasMoreElements()) {
                        String oldKey = sets.nextElement();
                        Long diff = System.currentTimeMillis() - Long.parseLong(oldKey);
                        if (diff > 5000) {
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
