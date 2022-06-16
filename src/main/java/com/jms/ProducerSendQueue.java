package com.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.logging.Logger;

public class ProducerSendQueue {

    private static final Logger LOGGER = Logger.getLogger(ProducerSendQueue.class.getName());

    public static void main(String[] args) throws JMSException {

        Session session = null;
        Connection connection = null;

        try {
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

            // Create a Connection
            connection = connectionFactory.createConnection();
            connection.start();

            // Create a Sessioin
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination [Topic of Queue]
            Queue destination = session.createQueue("myQueue");

            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            for (int i = 0; i < 5000; i++) {
                // Create a message
                String text = "Hello World!" + i;
                TextMessage message = session.createTextMessage(text);
                String messageIsSent = "Sent message " + text;
                LOGGER.info(messageIsSent);
                // Tell the producer to send the message
                producer.send(message);

            }
        } catch (JMSException e) {
            LOGGER.info(e.getMessage());
        } finally {
            session.close();
            connection.close();
        }
    }
}
