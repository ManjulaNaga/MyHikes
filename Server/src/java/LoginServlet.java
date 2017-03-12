import JDBC.RegDB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Java.User;
import javax.servlet.RequestDispatcher;
/** @manju 2/18/2017
 */
public class LoginServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        try (PrintWriter out = response.getWriter()) {
            RegDB rdb = new RegDB();
            User user = new User();
            String username=request.getParameter("username");
            String password=request.getParameter("password");
            user.setUsername(username);
            user.setPassword(password);
            if(rdb.checkUserDetails(username,password))
            {
               // RequestDispatcher rd = request.getRequestDispatcher("newHike.html");
                //rd.include(request,response);
                
                out.println("welcome.html");
            }
            else{
                System.out.println("user not found,please try again..");
                out.println("login.html");

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
