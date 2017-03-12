package Java;

import Java.SessionUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.servlet.http.HttpSession;

/**
  * @manju 1/29/2017
 */
@ManagedBean(name="hikeController")
@RequestScoped
public class HikeController implements Serializable  {

    String username;
    String password;
    
   // private ArrayList<Userbean> mails;
    //private Userbean c;
    //private String touser;
    //private String tosubject;
    //private String totext;
    
//@EJB
    //private Email email;  
 
  //private Store store=null;
  // private Folder folder = null;
  // private Message [] messages=null;
   public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String validate(){
        String user=getUsername();
        String pass=getPassword();
        System.out.println("password is" +pass);
        if((pass!=null) && user.equals("manju") && ("manju".equals(pass)))
             {
        	System.out.println("Logged in sucessfully");
                HttpSession session1 = SessionUtils.getSession();
			session1.setAttribute("username", user);
                        return "welcome";
             }
        else{
                FacesContext.getCurrentInstance().addMessage(null,
	        new FacesMessage(FacesMessage.SEVERITY_WARN,"Incorrect Username and Passowrd","Please enter correct username and Password"));
		return "index";
 	}		
    }/*
    public String sendMail(){
        System.out.println("you are sending mail");
        return "sendmail";           
    }
    public void sendMails(){
        String usr= getUsername();
        String pwd=getPassword();
        //Userbean user=new Userbean();
        String to1= getTouser();
        String sub= getTosubject();
        String text1= getTotext();
        System.out.println("We are in sendMails() ......");
        System.out.println("usr is " + usr);
        System.out.println("password is " + pwd);
        System.out.println("recipient is " + to1);
        System.out.println("subject is " + sub);
        System.out.println("text is " + text1);
        //System.out.println("session is " + session);
        System.out.println(" *********************");
        email.connect(usr, "cs6522");
        email.sendMsg(to1,usr,sub,text1);  
        email.disconnect();
    }
      public String checkMail() {
        int numberOfMails = email.connect(username, password);
        for (int i = 0; i < numberOfMails; i++) {
            Userbean ub = new Userbean();
            ub.setBool(false);
            ub.setText(email.getContent(i));
            ub.setDate(email.getDat(i));
            ub.setFrom(email.getFromAddress(i));
            ub.setNumber(email.getNum(i));
            ub.setSubject(email.getMessage(i));
            mails.add(ub);
        }
        return "checkmail.xhtml";
    }*/
       public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        System.out.println("you are logging out");
        //email.disconnect();
        return "index";
    }
}

