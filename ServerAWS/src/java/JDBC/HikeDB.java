package JDBC;
//import static JDBC.RegDB.DB_URL;
import Java.HikeSummary;
import Java.User;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
/**
 * @author manju 2/18/2017
        public boolean insertHikeInfo(String user_name,String hike_name)
        public boolean insertHikeMetaInfo(String hname,String uname,String fpath)
        public ArrayList<HikeSummary> viewAllHikes(String user_name)
        public ArrayList<HikeSummary> viewHikeDetails(String user_name,String hike_name)
*/
public class HikeDB {
    
     // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   // static final String DB_URL = "jdbc:mysql://localhost/Myhikes";
    static final String DB_URL = "jdbc:mysql://aa1m3b40v5bhppf.cswse0ugl1yx.us-west-1.rds.amazonaws.com/myhikes";
    static final String USER = "root";
    static final String PASS = "manjula123";
    String query1,query2,query3;
    public boolean insertHikeInfo(String user_name,String hike_name){
       System.out.println("Connecting to a My Hikes database...");
       Connection conn = null;
       Statement stmt = null;
       ResultSet result = null;
       int userId=0;
       String username = null;
       try{
            Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL+"?useSSL=false", USER, PASS);
            System.out.println("Connected database successfully...");
            stmt = conn.createStatement();
            String query1,query2;
            //User user = new User();
            // System.out.println(" getting user id from user table....");
            query1 = "SELECT ID FROM myhikes.users WHERE USERNAME=\""+user_name+"\";";
            System.out.println("select query is : "+query1);
            result = stmt.executeQuery(query1);
            //System.out.println("before result ....userID ");
            if (result.next()) {
                userId = result.getInt("ID");
                System.out.println("user id is " +userId);
            }
            System.out.println("userID = "+userId);
            System.out.println("username = "+user_name);
            //System.out.println("Inserting meta data into the HikeMetaDetails table...");

            query2 = "INSERT INTO myhikes.hikeinfo (USER_ID,USER_NAME,HIKE_NAME, DATE_CREATED,TIME_CREATED) "
                    + "VALUES(\""+userId+"\",\""+user_name+"\",\""+hike_name+"\",CURDATE(),CURTIME());";
            System.out.println("insert query is : "+query2);
            stmt.executeUpdate(query2);
            System.out.println("sucessfully added into data base");
            return true;
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
               if(conn!=null)
                conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        return false;
    }//end main
    
    public int insertHikeMetaInfo(String uname,String hname,String fpath)
    {
        //System.out.println("Connecting to a My Hikes database...");
       Connection conn = null;
       Statement stmt = null;
       PreparedStatement pstmt= null;
       ResultSet result = null;
       int userId=0;
       int hikeId=0;
       FileInputStream fis = null;
       String username = null;
       try{
            Class.forName("com.mysql.jdbc.Driver");
           // System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL+"?useSSL=false", USER, PASS);
           // System.out.println("Connected database successfully...");
            stmt = conn.createStatement();
           // System.out.println(" getting user id from user table....");
            query1 = "SELECT ID FROM myhikes.users WHERE USERNAME=\""+uname+"\";";
            System.out.println("select query is : "+query1);
            result = stmt.executeQuery(query1);
            if (result.next()) {
                userId = result.getInt("ID");
                  System.out.println("user id is " +userId);
            }
            System.out.println("userID = "+userId);
            System.out.println("\n username = "+uname);
            //____getting meta details from HIke INFO table________
             System.out.println(" getting hike id from hike info table....");
              System.out.println("hname="+ hname+"uname= "+uname);
            query2 = "SELECT HIKE_ID FROM myhikes.hikeinfo WHERE USER_NAME=\""+uname+"\" AND HIKE_NAME=\""+hname+"\";";
            System.out.println("select query is : "+query2);
            result = stmt.executeQuery(query2);
            if (result.next()) {
                hikeId = result.getInt("HIKE_ID");
               }
            System.out.println("hikeId = "+hikeId);
            System.out.println("\n hikename = "+hname);
            System.out.println("Inserting data into the Hikemetainfo table...");
            query3= "INSERT INTO myhikes.hikemetainfo (HIKE_ID,HIKE_NAME,USER_NAME,"
                    + "FILE_PATH) VALUES(\""+hikeId+"\",\""+hname+"\",\""+uname+"\",\""+fpath+"\");";
                pstmt=conn.prepareStatement(query3);            
                System.out.println("insert query is : "+query3);
                pstmt.executeUpdate();
                //conn.commit();
                System.out.println("sucessfully added into data base");
            return hikeId;
        }catch(SQLException se){
        //Handle errors for JDBC
        se.printStackTrace();
        }catch(Exception e){
        //Handle errors for Class.forName
        e.printStackTrace();
        }finally{
        //finally block used to close resources
        try{
            if(stmt!=null)
                conn.close();
        }catch(SQLException se){
        }// do nothing
        try{
            if(conn!=null)
                conn.close();
        }catch(SQLException se){
             se.printStackTrace();
        }//end finally try
        }//end try
        System.out.println("Goodbye!");
        return 0;
    }
    //insert hike details into HikeDetails table
    
//view all hikes
    public ArrayList<HikeSummary> viewAllHikes(String user_name)
    {
       System.out.println("Connecting to a My Hikes database...");
       Connection conn = null;
       Statement stmt = null;
       ResultSet result = null;
       int userId=0;
       String username = null;
       ArrayList<HikeSummary> hikes = null;
       try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL+"?useSSL=false", USER, PASS);
            System.out.println("Connected database successfully...");
            stmt = conn.createStatement();
            String query1,query2;
            //User user = new User();
             System.out.println(" getting user id from user table....");
            query1 = "SELECT ID FROM myhikes.users WHERE USERNAME=\""+user_name+"\";";
            System.out.println("select query is : "+query1);
            result = stmt.executeQuery(query1);
            System.out.println("before result ....userID ");
            if (result.next()) {
                userId = result.getInt("ID");
                System.out.println("user id is " +userId);
            }
            System.out.println("userID = "+userId);
            System.out.println("\n username = "+user_name);
            System.out.println("Fetching meta data from HikeInfo table...");
            query2 = "SELECT *  FROM myhikes.hikeinfo  WHERE USER_NAME=\""+user_name+"\" AND USER_ID = \""+userId+"\";"; 
            System.out.println("select query is : "+query2);
            result = stmt.executeQuery(query2);
            System.out.println("sucessfully fetched from data base");
            //ArrayList list = new ArrayList();
            hikes = new ArrayList<HikeSummary>();
            while (result.next()) {
                HikeSummary hike = new HikeSummary();
                userId = result.getInt("USER_ID");
                hike.setName(result.getString("HIKE_NAME"));
                hike.setDate(result.getString("DATE_CREATED"));
                hike.setTime(result.getString("TIME_CREATED"));
                hikes.add(hike);
             }
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
               if(conn!=null)
                conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        return hikes;
    }//end main
    //----------------
    public ArrayList<HikeSummary> viewHikeDetails(String user_name,String hike_name)
    {
       System.out.println("Connecting to a My Hikes database...");
       Connection conn = null;
       Statement stmt = null;
       ResultSet result = null;
       ///int userId=0;
       ArrayList<HikeSummary> hikes = null;
       try{
                Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL+"?useSSL=false", USER, PASS);
            System.out.println("Connected database successfully...");
            stmt = conn.createStatement();
            System.out.println("Fetching meta data from HikeDetails table...");
            query1 = "SELECT * FROM myhikes.hikedetails  WHERE USER_NAME=\""+user_name+"\" AND HIKE_NAME = \""+hike_name+"\";"; 
            System.out.println("select query is : "+query1);
            result = stmt.executeQuery(query1);
            System.out.println("sucessfully fetched from hike details table");
            hikes = new ArrayList<HikeSummary>();
            while (result.next()) {
                HikeSummary hike = new HikeSummary();
                hike.setName(result.getString("HIKE_NAME"));
                hike.setUser(result.getString("USER_NAME"));
                hike.setTime(result.getString("TIMESTAMP"));
                hike.setLatitude(result.getString("LATITUDE"));
                hike.setLongitude(result.getString("LONGITUDE"));
                hikes.add(hike);
             }

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
               if(conn!=null)
                conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        return hikes;
    }//end main
    
}
