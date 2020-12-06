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
 * @author Jamie
 */
public class SignUp extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            DBBean db = new DBBean();
            Connection con = (Connection) getServletContext().getAttribute("con");
            db.connect(con);
            String _username = request.getParameter("username").trim();
            String _password = request.getParameter("password").trim();
            String _role = request.getParameter("role").trim();

            // if all 3 fields are not empty
            if (!_username.equals("") && !_password.equals("") && !_role.equals("default")) {
                out.print(_username + " - " + _password + " - " + _role);
            }
            // if not all fields have been filled
            else {
                if (_username.equals(""))
                    request.setAttribute("errName", "Please enter username");
                if (_password.equals(""))
                    request.setAttribute("errPass", "Please enter password");
                if (_role.equals("default"))
                    request.setAttribute("errRole", "Please select your user type");
                
                // send these back so user dont have to enter again
                request.setAttribute("nameSignup", _username);
                request.setAttribute("roleSignup", _role);

                request.getRequestDispatcher("/viewer/SignUp.jsp").forward(request, response);
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
