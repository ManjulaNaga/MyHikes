import JDBC.HikeDB;
import JDBC.HikeFileToDB;
import Java.User;
import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
//import static com.sun.xml.ws.spi.db.BindingContextFactory.LOGGER;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import javax.jms.*;  
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
/**
 * @manju 2/19/2017
 */
public class HikeServlet extends HttpServlet {
//-----
 private final String user = ActiveMQConnection.DEFAULT_USER;
    private final String password = ActiveMQConnection.DEFAULT_PASSWORD;
    //private final String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private final String url = "tcp://ip-172-31-4-40:61616";
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
//------
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
    response.setContentType("text/html;charset=UTF-8");
         // Create path components to save the file
    final String path = "/tmp";
    String uname=request.getParameter("username");
    String hname=request.getParameter("hikename");
    int hike_id = 0;
    final Part filePart = request.getPart("file");
    final String fileName = uname+"_"+System.currentTimeMillis();

    OutputStream out = null;
    InputStream filecontent = null;
    final PrintWriter writer = response.getWriter();
    writer.print("username is "+uname);
    writer.print("hikename is "+hname);
    try {
        String fpath = path + '/'+ fileName;
        System.out.println("fpath is........................... "+fpath);
        out = new FileOutputStream(new File(fpath));
        filecontent = filePart.getInputStream();
        int read = 0;
        final byte[] bytes = new byte[1024];
        while ((read = filecontent.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        writer.println("New file " + fileName + " created at " + path);
        LOGGER.log(Level.INFO, "File{0}being uploaded to {1}", 
                new Object[]{fileName, path});
         HikeDB hdb = new HikeDB();
         HikeFileToDB hfdb = new HikeFileToDB();
            if(hdb.insertHikeInfo(uname,hname))
            {
                //out.println("success.html");
                hike_id = hdb.insertHikeMetaInfo(uname,hname,fpath);
                if(hike_id!=0)
                {
                    //if(hfdb.insertHikeInfoToDB(uname,hname,fileName)){
                    if (sender == null) {
                     writer.println(_message);
                    } else {
                        try {
                            String text = Integer.toString(hike_id);
                            TextMessage msg;
                            msg = sess.createTextMessage(text);
                            sender.send(msg);
                            writer.println("Just sent " + msg.getText());
                        } catch (JMSException ex) {
                            writer.println(ex.toString());
                        }
                    }
                        writer.println("success.html");
                    //}
                }
                else{
                    writer.println("can not load to DB");
                }
            }
            else{
                writer.println("error while loading to hikeMetaInfo");
                  writer.println("You either did not specify a file to upload or are "
                + "trying to upload a file to a protected or nonexistent "
                + "location.");
            }
        
    } catch (FileNotFoundException fne) {
        writer.println("<br/> ERROR: " + fne.getMessage());
        LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}", 
        new Object[]{fne.getMessage()});
    } finally {
        if (out != null) {
            out.close();
        }
        if (filecontent != null) {
            filecontent.close();
        }
        if (writer != null) {
            writer.close();
        }
    }
}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
 
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(HikeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(HikeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
