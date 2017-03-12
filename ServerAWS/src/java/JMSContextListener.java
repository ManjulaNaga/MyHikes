import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @manju 3/5/2017
 */
public class JMSContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        Thread thread = new Thread(new JMSProcessor());
        thread.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    }
}