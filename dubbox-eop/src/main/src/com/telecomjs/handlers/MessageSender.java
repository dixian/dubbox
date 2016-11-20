package com.telecomjs.handlers;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import com.telecomjs.messages.RequestMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

/**
 * Created by zark on 16/11/18.
 */
@Component
public class MessageSender {

    Logger logger = Logger.getLogger(MessageSender.class);
    @Autowired
    private  JmsTemplate jmsTemplate;
    @Autowired
    private  Destination destination;

    public MessageSender() {
    }
    /*
    public MessageSender(final JmsTemplate jmsTemplate, final Destination destination) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
    }*/

    public void send(final RequestMessage message) {
        try {
            logger.debug("MessageSender send :  "+message.getCallMethod()
                    +(message.getParams() == null ?  message.getParam().toString(): message.getParams().toString())+",message = "+message.toString());
            jmsTemplate.setDefaultDestination(destination);
            //jmsTemplate.convertAndSend(message);
            jmsTemplate.send(new MessageCreator(){

                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createObjectMessage(message);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
