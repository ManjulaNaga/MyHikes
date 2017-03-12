import JDBC.HikeFileToDB;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
/**
 * @manju 3/5/2017
 */
public class HikeProcessor implements MessageListener {
    @Override
    public void onMessage(Message message) { 
        try {
            HikeFileToDB hfdb = new HikeFileToDB();
            String msg = ((TextMessage)message).getText();
            //int hike_id = (int)msg;
            System.out.println("HikeProcessor msg: " + msg);
            //if(hfdb.insertHikeInfoToDB(uname,hname,fileName)
            if(hfdb.insertHikeInfoToDB(msg)){
               System.out.println("successfully added hike details to MyHikes database by JMS");
            }
            // TODO: Process file from here
        } catch (Exception e) { 
            System.out.println("Worker caught an Exception"); 
        } 
    } 
}
