package controller.entry;

import com.UserToken;
import com.HashPassword;
import dao.EntryDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.pojo.Employee;

/**
 *
 * @author Bao Bui
 */
public class SignUp extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String username = request.getParameter("username").trim();
            String fullName = request.getParameter("fullname").trim();
            String dob = request.getParameter("dob").trim();
            String address = request.getParameter("address").trim();
            String role = request.getParameter("role").trim();
            String password = request.getParameter("password").trim();
            String password_repeat = request.getParameter("repeat-password").trim();

            if (password.equals(password_repeat)) {
                Connection con = (Connection) getServletContext().getAttribute("con");
                EntryDao entryDao = new EntryDao(con);
                
                boolean isTaken = entryDao.unameTaken(username);
                if (isTaken) {
                    out.print("<small class=\"Error Error-Signup\">This Username is Already Taken</small>");
                    request.getRequestDispatcher("/SignUp.html").include(request, response);
                }
                
                else {
                    Employee employee = new Employee();
                    employee.setUsername(username);
                    employee.setPassword(password);
                    employee.setFullName(fullName);
                    employee.setAddress(address);
                    employee.setDob(dob);
                    employee.setRole(role);
                    
                    boolean res = entryDao.signUpStaff(employee);
                    
                    if (res) 
                        request.getRequestDispatcher("/Login.html").forward(request, response);                      
                    else {
                        out.print("<small class=\"Error Error-Signup\">There's been some error. Please try again later.</small>");
                        request.getRequestDispatcher("/SignUp.html").include(request, response);
                    }       
                }               
            } 
            else {
                out.print("<small class=\"Error Error-Signup\">Confirmation Password is Incorrect</small>");
                request.getRequestDispatcher("/SignUp.html").include(request, response);
            }

        }
    }


// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
