package com.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Logger;

public class MyMessageListener implements MessageListener {
    private static final Logger LOGGER = Logger.getLogger(MyMessageListener.class.getName());
    private String consumerName;

    public MyMessageListener(String consumerName){
        this.consumerName = consumerName;
    }

    @Override
    public synchronized void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try{
            String messageReceived = "Message Received " +  textMessage.getText();
            LOGGER.info(messageReceived);
        } catch (JMSException e) {
            LOGGER.info(e.getMessage());
        }
    }
}
