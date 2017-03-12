import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.JMSException;

import javax.jms.*;  
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
/**
 * @manju 3/5/2017
 */
public class SenderServlet extends HttpServlet {

    private final String user = ActiveMQConnection.DEFAULT_USER;
    private final String password = ActiveMQConnection.DEFAULT_PASSWORD;
    private final String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private Destination destination;
    private MessageProducer sender;
    private Session sess;
    private Connection connection;
    private String       _message;    
    
    @Override
    public void init()
        {
            try
            {
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password,url);
                connection = connectionFactory.createConnection();
                connection.start();
                sess = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                destination = sess.createQueue("MyHikes.HikeProcessor.Queue");
                sender = sess.createProducer(destination);
                sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT); 
            }
            catch (Exception ex)
            {
                _message = ex2str(ex);
            }
    }
     public static String ex2str(Throwable t)
    {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            PrintWriter pw = new PrintWriter(os);
            t.printStackTrace(pw);
            pw.flush();
            return new String(os.toByteArray());
            }
        catch (Throwable e) {
            return t.toString();
        }
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
         
        try (PrintWriter out = response.getWriter()) 
        {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<h1>Status</h1>");
                                                                          
            if (sender == null) {
             out.println(_message);
            } else {
                try {
                    String text = "120";
                    TextMessage msg;
                    msg = sess.createTextMessage(text);
                    sender.send(msg);
                    out.println("Just sent " + msg.getText());
                } catch (JMSException ex) {
                    out.println(ex.toString());
                }
            }
            out.println("</body>");
            out.println("</html>");
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    @Override
    public void destroy() {
        try {
            connection.close();
        } catch (JMSException ex) {
            Logger.getLogger(SenderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
