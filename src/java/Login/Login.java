/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Admin
 */
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            DBBean db = new DBBean();
            Connection con = (Connection) getServletContext().getAttribute("con");
            db.connect(con);
            String _username = request.getParameter("username");
            String _password = request.getParameter("password");

            if (_username != null && _password != null) {
                // connect database

                //
                // get username and password
                String uname = db.getRecords("SELECT UNAME FROM ROOT.USERS WHERE UNAME ='" + _username + "' ")[0][0];
                String passwd = db.getRecords("SELECT PASSWD FROM ROOT.USERS WHERE PASSWD ='" + _password + "' ")[0][0];
//            boolean valid = false;

                if (_username.equals(uname) && _password.equals(passwd)) {
                    HttpSession session = request.getSession();
//                    session.setAttribute("login", "admin");
                    session.setAttribute("a", uname);
                    session.setAttribute("u", _username);
                    session.setAttribute("p", _password);

                    response.sendRedirect("viewer/login.jsp");
//                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else {
                    out.println("Invalid username or password");
                }
            } else if (_username == null && _password == null) {
                out.println("Invalid username or password");
                out.close();

            }
            /* TODO output your page here. You may use following sample code. */
//         
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
