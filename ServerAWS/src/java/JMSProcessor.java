
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Session;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @manju 5/3/2017
 */
public class JMSProcessor implements Runnable {

    @Override
    public void run() {
        try {
            String user = ActiveMQConnection.DEFAULT_USER;
            String password = ActiveMQConnection.DEFAULT_PASSWORD;
            //String url = ActiveMQConnection.DEFAULT_BROKER_URL;
            String url = "tcp://ip-172-31-4-40:61616";

            Connection connection;
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
            connection = connectionFactory.createConnection();
            Session sess = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = sess.createQueue("MyHikes.HikeProcessor.Queue");
            MessageConsumer consumer = sess.createConsumer(destination);

            //This MessageListener will do stuff with the message
            HikeProcessor hikeProcessor = new HikeProcessor();
            consumer.setMessageListener(hikeProcessor);
            connection.start();

            // Start connection or nothing will happen!!!
            connection.start();
        } catch (JMSException ex) {
            //TODO
        }
    }
}
