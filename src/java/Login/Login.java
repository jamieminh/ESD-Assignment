package Login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.dbHandler.DBBean;

/**
 *
 * @author BaoBui - modified by Jamie
 */
public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            DBBean db = new DBBean();
            Connection con = (Connection) getServletContext().getAttribute("con");
            db.connect(con);
            String _username = request.getParameter("username").trim();
            String _password = request.getParameter("password").trim();
            
            // if the 2 fields are not empty
            if (!_username.equals("") && !_password.equals("")) {
                String[][] record = db.getRecords(String.format("SELECT role FROM APP.USERS WHERE uname='%s' AND passwd='%s'", _username, _password));

                if (record.length != 0) {
                    HttpSession session = request.getSession();
                    String role = record[0][0];
                    String name = "";
                    if (role.equals("client"))
                        name = db.getRecords("SELECT cname FROM APP.clients WHERE uname='" + _username + "'")[0][0];
                    else if (!role.equals("admin"))
                        name = db.getRecords("SELECT ename FROM APP.employees WHERE uname='" + _username + "'")[0][0];

                    String[] pages;
                    switch(role) {
                        case "admin":
                            pages = new String[] {"Add Employees", "Cancel Surgery", "Produce Documents", "Lists"};
                            break;
                        case "client":
                            pages = new String[] {"Book Appointment", "See Schedule", "Request Prescription"};
                            break;
                        case "doctor":
                            pages = new String[] {"See Schedule", "Issue Prescription", "Forward Patient"};
                            break;
                        case "nurse":
                            pages = new String[] {"See Schedule", "Issue Prescription"};
                            break;    
                        default:
                            pages = new String[] {};
                    }

                    session.setAttribute("isLoggedIn", true);
                    session.setAttribute("fullName", name);
                    session.setAttribute("role", role);
                    session.setAttribute("title", "Dashboard: " + name);
                    session.setAttribute("folderUrl", "/viewer/" + role + "/");
                    session.setAttribute("pages", pages);
                    
                    response.sendRedirect("/viewer/Home.jsp");
                }
                // if user input are incorrect
                else {
                    request.setAttribute("errUser", "Your Username or Password is Incorrect");
                    request.getRequestDispatcher("/viewer/Login.jsp").forward(request, response);
                }
            }
            // if one or both are empty
            else {
                if (_username.equals(""))
                    request.setAttribute("errName", "Please enter username");
                if (_password.equals(""))
                    request.setAttribute("errPass", "Please enter password");
                request.getRequestDispatcher("/viewer/Login.jsp").forward(request, response);
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
