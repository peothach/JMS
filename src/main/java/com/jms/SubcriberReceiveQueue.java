package com.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.logging.Logger;

public class SubcriberReceiveQueue {

    private static final Logger LOGGER = Logger.getLogger(SubcriberReceiveQueue.class.getName());

    public static void main(String[] args) throws JMSException {
        Connection connection = null;
        Session session = null;

        try{
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

            // Create a Connection
            connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination [Topic or Queue]
            Topic destination = session.createTopic("myQueuePublisher");

            // Create MessageConsumer from the Session to the Topic or Queue
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(new MyMessageListener("Consumer Name"));

            /* Synchronize get message
            do{
                // Wait for a message
                Message message = consumer.receive(1000);
                String messageReceived = "";

                if(message == null){
                    break;
                }

                if(message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage) message;
                    String text  = textMessage.getText();
                    messageReceived = "Received " + text;
                }else{
                    messageReceived = "Received " + message;
                }

                LOGGER.info(messageReceived);
                Thread.sleep(10);
            }while(true);
             */

        } catch (JMSException e) {
            LOGGER.info(e.getMessage());
        }
    }
}
