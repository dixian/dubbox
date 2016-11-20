package com.telecomjs.resources;

import com.telecomjs.handlers.AsyncRequestMapHandler;
import com.telecomjs.handlers.MessageSender;
import com.telecomjs.service.intf.CustomService;
import com.telecomjs.service.intf.ProductService;
import com.telecomjs.vo.EOPResponseRoot;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.CompletionCallback;
import javax.ws.rs.container.ConnectionCallback;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zark on 16/11/20.
 */
public class AbstractCommonResource {

    Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private AsyncRequestMapHandler asyncRequestMapHandler;

    protected void configResponse(final AsyncResponse asyncResponse, final String sequence) {
        //讲异步响应的实例压如Hash队列中
        asyncRequestMapHandler.putResponse(sequence,asyncResponse);
        //超时的回调，当超时时，主动唤醒AsyncResource实例并设置HTTP状态码
        asyncResponse.setTimeoutHandler(new TimeoutHandler() {
            @Override
            public void handleTimeout(AsyncResponse asyncResponse) {
                //Status.SERVICE_UNAVAILABLE=503
                asyncResponse.resume(Response.status(
                        Response.Status.SERVICE_UNAVAILABLE)
                        .entity(new EOPResponseRoot().err("Operation time out.")).build());
                //超时清楚AsyncResponse的缓存
                asyncRequestMapHandler.removeResponse(sequence);
                final long end = System.currentTimeMillis();
                logger.debug("TIMEOUT duration :" +String.valueOf(end - Long.parseLong(sequence))+",key="+sequence);
            }
        });
        //设置超时，3秒
        asyncResponse.setTimeout(10, TimeUnit.SECONDS);

        //处理完成的回调
        asyncResponse.register(new CompletionCallback() {
            @Override
            public void onComplete(Throwable throwable) {
                if (throwable == null) {
                    //debugThreadPool();
                    //logger.debug("CompletionCallback-onComplete: OK");
                } else {
                    logger.debug("CompletionCallback-onComplete: ERROR: "
                            + throwable.getMessage());
                    throw  new ServerErrorException("CompletionCallback-onComplete:error",0);
                }
            }
        });

        //连接断开的回调
        asyncResponse.register(new ConnectionCallback() {
            @Override
            public void onDisconnect(AsyncResponse disconnected) {
                //Status.GONE=410
                final long end = System.currentTimeMillis();
                logger.debug("ConnectionCallback-onDisconnect duration :" +String.valueOf(end - Long.parseLong(sequence))+",key="+sequence);
                disconnected.resume(Response.status(404).
                        entity(  new EOPResponseRoot().err("disconnect")).
                        type(MediaType.APPLICATION_JSON_TYPE).
                        build());
                //throw new TimeoutException("disconnect");
            }
        });

    }

    protected void debugMapHandler(){
        logger.debug("concurrent hashmap size :"+asyncRequestMapHandler.count());
    }


    /**
     * 打印线程池和hashmap信息
     */
    protected void debugThreadPool(ThreadPoolTaskExecutor taskExecutor){
        logger.debug("ThreadPool ActiveCount : "+taskExecutor.getActiveCount());
        logger.debug("ThreadPool PoolSize : "+taskExecutor.getPoolSize());
        logger.debug("ThreadPool CorePoolSize : "+taskExecutor.getCorePoolSize());
        logger.debug("ThreadPool MaxPoolSize : "+taskExecutor.getMaxPoolSize());

    }

}
