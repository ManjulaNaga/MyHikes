package JDBC;
import Java.HikeSummary;
import Java.User;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import javax.ejb.EJB;
/**
 *
 * @manju 3/3/2017
 */
public class HikeFileToDB {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
//    static final String DB_URL = "jdbc:mysql://localhost/Myhikes";
      static final String DB_URL = "jdbc:mysql://aa1m3b40v5bhppf.cswse0ugl1yx.us-west-1.rds.amazonaws.com/myhikes";
      static final String USER = "root";
    static final String PASS = "manjula123";
    String query1,query2,query3;
    public boolean insertHikeInfoToDB(String strhikeID) throws SQLException, FileNotFoundException, IOException{
       System.out.println("in insert hike file details into to DB...");
       Connection conn = null;
       PreparedStatement preparedStatement = null; 
       Statement stmt = null;
       ResultSet result = null;
       int hikeID = Integer.parseInt(strhikeID);

       String filename = null;
       try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to a MY Hikes database...");
            conn = DriverManager.getConnection(DB_URL+"?useSSL=false", USER, PASS);
            System.out.println("Connected to MY hikes database successfully...");
           // stmt = conn.createStatement();
            String query1,query2;
            stmt = conn.createStatement();
            System.out.println(" getting username,hikename,filepath from hikeMetaInfo table....");
            query1 = "SELECT USER_NAME,HIKE_NAME,FILE_PATH FROM myhikes.hikemetainfo WHERE HIKE_ID="+hikeID+";";
            System.out.println("select query is : "+query1);
            result = stmt.executeQuery(query1);
            if (result.next()) {
                filename = result.getString("FILE_PATH");
               }
            System.out.println("file path = "+filename);
            System.out.println("Inserting  data  from file into the HikeDetails table...");
            query2 = "INSERT INTO myhikes.hikedetails (HIKE_ID,HIKE_NAME,USER_NAME,"
                    + "LATITUDE,LONGITUDE,TIMESTAMP)"
                    + "VALUES(?,?,?,?,?,?);";
            String fname = filename;
            System.out.println(" file path is " +fname);
            preparedStatement = conn.prepareStatement(query2); 
            BufferedReader reader = new BufferedReader(new FileReader(fname)); 
            String line = null; //line read from csv 
            Scanner scanner = null; //scanned line 

            //READING FILE LINE BY LINE AND UPLOADING INFORMATION TO DATABASE 
            while((line = reader.readLine()) != null){
                System.out.println("line "+line);
                scanner = new Scanner(line); 
                scanner.useDelimiter(","); 
                while(scanner.hasNext()){ 
                    preparedStatement.setInt(1,hikeID);    
                    preparedStatement.setString(2,scanner.next()); //hike name
                    preparedStatement.setString(3,scanner.next()); //user name
                    preparedStatement.setString(4,scanner.next()); //latitude
                    preparedStatement.setString(5,scanner.next()); //longitude
                    preparedStatement.setString(6,scanner.next()); //timestamp
                }	
                preparedStatement.executeUpdate(); 
            }
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
}
