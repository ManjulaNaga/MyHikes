import JDBC.RegDB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Java.User;
import static java.lang.System.console;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
/** @manju 2/28/2017
 */
public class RegistrationServlet extends HttpServlet {

    @EJB
    private User user;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        try (PrintWriter out = response.getWriter()) {
            RegDB rdb = new RegDB();
            String firstname=request.getParameter("firstname");
        System.out.println("first name is in servlet  :        "+firstname);
            String lastname=request.getParameter("lastname");
            String username=request.getParameter("username");
            String password=request.getParameter("password");
            String email=request.getParameter("email");
            String phone=request.getParameter("phone");
            String address=request.getParameter("address");
            String country=request.getParameter("country");
            String gender=request.getParameter("gender");
            String zip=request.getParameter("zipCode");
            user.setFirstname(firstname);
            user.setUsername(username);
            user.setLastname(lastname);
            user.setEmail(email);
            user.setPhone(phone);
            user.setAddress(address);
            user.setCountry(country);
            user.setGender(gender);
            user.setPassword(password);
            user.setZipcode(zip);

            if(rdb.insertUserDetails(user))
            {
              //  out.println("in insert user details...");
               
                
                out.println("newHike.html");
            }
            else{
                System.out.println(username +"already exists,please try with another username");
                 out.println("register.html");

            }
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

}
