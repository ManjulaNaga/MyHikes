package JDBC;
import Java.User;
import java.sql.*;
import java.util.Properties;
import java.util.Random;
import javax.ejb.EJB;
/**
 * @author manju 2/10/2017 to 2/18/2017
 */
public class RegDB {
     // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
  
   static final String DB_URL = "jdbc:mysql://localhost/Myhikes";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "manjula123";
           //User user= new User();
    public boolean insertUserDetails(User user){
       System.out.println("Connecting to a My Hikes database...");
       Connection conn = null;
       Statement stmt = null;
       try{
        //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
          //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL+"?useSSL=false", USER, PASS);
            System.out.println("Connected database successfully...");
              
            //STEP 4: Execute a query
            System.out.println("Inserting records into the table...");
            stmt = conn.createStatement();
            String sql;
            Random rand = new Random();
            int id = rand.nextInt(1000);
            System.out.println(user);
            String uname= user.getUsername();
            String fname = user.getFirstname();
            String password = user.getPassword();
            String lname = user.getLastname();
            String email =user.getEmail();
            String address = user.getAddress();
            String phone= user.getPhone();
            String country = user.getCountry();
            String zip= user.getZipcode();
            System.out.println("user.getUsername:"+user.getUsername());
            sql = "INSERT INTO Myhikes.users (id, FirstName, LastName,UserName, Password, Email, Phone, Address,Country,ZipCode,created_date,created_time) VALUES " +
               "(" +id+",\""+fname+"\",\""+lname+"\",\""+uname+ "\",\""+password+ "\",\""+email+"\",\""
                    +phone+"\",\""+address+"\",\""+country+"\",\""+zip+"\",CURDATE(),CURTIME());";
            System.out.println("SQL is : "+sql);
            
               stmt.executeUpdate(sql);
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
    
       public boolean checkUserDetails(String param_user,String param_pwd){
       System.out.println("Connecting to a My Hikes database...");
       Connection conn = null;
       Statement stmt = null;
       try{
        //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
          //STEP 3: Open a connection
            System.out.println("Connecting to a Myhikes database...");
            conn = DriverManager.getConnection(DB_URL+"?useSSL=false", USER, PASS);
            System.out.println("Connected Myhikes database successfully...");
              
            //STEP 4: Execute a query
            System.out.println("Fetching user record from user table...");
            stmt = conn.createStatement();
            String sql;
            ResultSet resultSet = null; 
            String username,password;
     
            sql = "SELECT username,password FROM Myhikes.users WHERE username = \""+ param_user +"\" AND password =  \""+param_pwd+"\";";
            System.out.println("SQL is : "+sql);
           
			//PreparedStatement statement = conn.prepareStatement(query);
            resultSet = stmt.executeQuery(sql);
            String db_uname="";
            String db_pwd="";            
            while (resultSet.next()) {
                db_uname = resultSet.getString("username");
                db_pwd = resultSet.getString("password");                                        
             }
            if( (param_user.equals(db_uname)) && (param_pwd.equals(db_pwd))){
                    System.out.println("SUCCESS");
                    return true;
            } else {
                System.out.println("FAIL");
				System.out.println("No Data Found");
		return false;	
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
   return false;
}
}

