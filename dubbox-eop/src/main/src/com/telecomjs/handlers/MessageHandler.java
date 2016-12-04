package com.telecomjs.handlers;

import com.telecomjs.constants.EOPConstants;
import com.telecomjs.messages.RequestMessage;
import com.telecomjs.service.intf.AuthService;
import com.telecomjs.service.intf.CustomService;
import com.telecomjs.service.intf.ProductService;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.jms.*;
import javax.ws.rs.container.AsyncResponse;
import java.util.Map;

/**
 * Created by zark on 16/11/18.
 */
@Component
public class MessageHandler implements MessageListener {
    Logger logger = Logger.getLogger(MessageHandler.class);
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private CustomService customService;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ProductService productService;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private AsyncRequestMapHandler asyncRequestMapHandler;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private AuthService authService;


    public void onMessage(Message message) {
        logger.debug("message handler "+message.toString());
        final long timestampBefore = System.currentTimeMillis();
        if (message instanceof TextMessage) {
            logger.debug("TextMessage  handler  "+message.toString());
            TextMessage textMessage = (TextMessage) message;
            try {
                String text = textMessage.getText();
                logger.debug("接收到消息: " + text);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        else if (message instanceof ActiveMQObjectMessage) {
            logger.debug("RequestMessage  handler  "+message.toString());
            try {
                ObjectMessage om = (ObjectMessage) message;
                RequestMessage req = (RequestMessage) om.getObject();
                final long beginSeq = Long.parseLong(req.getRequestSequence())/1000;
                final AsyncResponse asyncResponse = asyncRequestMapHandler.getAndRemoveResponse(req.getRequestSequence());
                if (asyncResponse == null){
                    logger.info("Concurrent asyncresponse key not exists ! key = "+req.getRequestSequence());
                    return;
                }
                final Object args ;
                if (req.getParamType() == EOPConstants.ASYNC_REQUEST_MESSAGE_PARAM_TYPE_STRING)
                    args = req.getParam(); //单值参数
                else if (req.getParamType() == EOPConstants.ASYNC_REQUEST_MESSAGE_PARAM_TYPE_MAP)
                    args = req.getParams();//多值参数 map 类型
                else
                    args = req.getParams();

                final String method = req.getCallMethod();
                taskExecutor.execute(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long start = System.currentTimeMillis();
                        logger.debug("start ProductHandler(customService).callService() "+method+args.toString());
                        //目前统一按map参数处理
                        new ProductHandler(new Object[]{customService,productService,authService},asyncResponse)
                                .resumeService(method, (Map) args);
                        long end = System.currentTimeMillis();
                        logger.debug(this.getClass().getName()+".taskExecutor thread durations :" + String.valueOf(end-start));
                        logger.debug(this.getClass().getName()+".taskExecutor resume durations :" + String.valueOf(end-beginSeq));
                    }
                }));
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long start = System.currentTimeMillis();
                        logger.debug("start ProductHandler(customService).callService() "+method+args.toString());
                        //目前统一按map参数处理
                        new ProductHandler(new Object[]{customService,productService,authService},asyncResponse)
                                .resumeService(method, (Map) args);
                        long end = System.currentTimeMillis();
                        logger.debug(this.getClass().getName()+".taskExecutor thread durations :" + String.valueOf(end-start));
                        logger.debug(this.getClass().getName()+".taskExecutor resume durations :" + String.valueOf(end-beginSeq));
                    }
                }).start();*/
                final long timestamp = System.currentTimeMillis();
                logger.debug("messageHandler.receiver duration : "+String.valueOf(timestamp - Long.parseLong(req.getRequestSequence())/1000)
                        +",key="+req.getRequestSequence());
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
        else {
            logger.debug("unsupported message.  " +message.getClass().toString());
        }

    }

}