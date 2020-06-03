package com.controller.service.serviceImpl;

import com.controller.entity.DocModel;
import com.controller.entity.User;
import com.controller.entity.UserJms;
import com.controller.service.MessageService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class MessageServiceImpl implements MessageService, MessageListener {

    private static final String EMAIL = "email";
    private static final String CONTENT = "content";
    private static final String USER = "user";

    private static int ackMode;
    private static String clientQueueName;

    private boolean transacted = false;
    private MessageProducer producer;

    static {
        clientQueueName = "client.messages";
        ackMode = Session.AUTO_ACKNOWLEDGE;
    }

    @Override
    public void sendMessage(String email, String content, User user) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connectionFactory.setTrustAllPackages(true);
        Connection connection;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(transacted, ackMode);
            Destination adminQueue = session.createQueue(clientQueueName);

            this.producer = session.createProducer(adminQueue);
            this.producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            Destination tempDest = session.createTemporaryQueue();

            MapMessage txtMessage = session.createMapMessage();
            ObjectMessage objectMessage = session.createObjectMessage();

            Map<String, Object> map = new HashMap<>();
            map.put(USER, createUserJms(user));
            map.put(EMAIL, email);
            map.put(CONTENT, content);

            txtMessage.setString(EMAIL, email);
            txtMessage.setString(CONTENT, content);
           // txtMessage.setObject(USER, map);
            objectMessage.setObject(new DocModel(map));

            txtMessage.setJMSReplyTo(tempDest);

            String correlationId = this.createRandomString();
            txtMessage.setJMSCorrelationID(correlationId);
            this.producer.send(txtMessage);
        } catch (JMSException e) {
            //Handle the exception appropriately
        }
    }

    private String createRandomString() {
        Random random = new Random(System.currentTimeMillis());
        long randomLong = random.nextLong();
        return Long.toHexString(randomLong);
    }

    private UserJms createUserJms(User user) {
        return new UserJms(user.getId(), user.getUsername(), user.getPassword());
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println(textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    private void setMessageListener(Destination destination, Session session) throws JMSException {
        MessageConsumer messageConsumer = session.createConsumer(destination);
        messageConsumer.setMessageListener(this);
    }
}
