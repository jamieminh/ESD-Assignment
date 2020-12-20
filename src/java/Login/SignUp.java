package Login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dbHandler.DBBean;

/**
 *
 * @author Bao Bui
 */
public class SignUp extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            DBBean db = new DBBean();
            Connection con = (Connection) getServletContext().getAttribute("con");
            db.connect(con);
            String username = request.getParameter("username").trim();
            String fullName = request.getParameter("fullname").trim();
            String role = request.getParameter("role").trim();
            String address = request.getParameter("address").trim();
            String rate = request.getParameter("rate").trim();
            String password = request.getParameter("password").trim();
            String password_repeat = request.getParameter("repeat-password").trim();

            if (password.equals(password_repeat)) {
                String findUsername = "SELECT uname FROM APP.USERS WHERE uname='" + username + "'";
                String[][] found = db.getRecords(findUsername);
                if (found.length != 0) {
                    out.print("<small class=\"Error Error-Signup\">This Username is Already Taken</small>");
                    request.getRequestDispatcher("/SignUp.html").include(request, response);
                }
                
                String token = new UserToken().generateToken();     // user token
                String hashedPW = new HashPassword().hashPassword(password); // hash password
                
                boolean inserted = db.insertUser(new String[]{username, hashedPW, role, "false", token});
                boolean insertRole = false;
                if (inserted) {
                    insertRole = db.insertEmployee(new String[]{username, fullName, address, rate});
                }

                if (insertRole) {
                    request.getRequestDispatcher("/Login.html").forward(request, response);
                }

            } else {
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
