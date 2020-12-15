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
import model.dbHandler.DBBean;

/**
 *
 * @author Jamie
 */
public class SignUpClient extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            DBBean db = new DBBean();
            Connection con = (Connection) getServletContext().getAttribute("con");
            db.connect(con);
            String username = request.getParameter("username").trim();
            String fullName = request.getParameter("fullname").trim();
            String address = request.getParameter("address").trim();
            String type = request.getParameter("type").trim();
            String password= request.getParameter("password").trim();
            String password_repeat= request.getParameter("repeat-password").trim();

            // if passwords are the same
            if (password.equals(password_repeat)) {                
                String findUsername = "SELECT uname FROM APP.USERS WHERE uname='" + username + "'";
                String[][] res = db.getRecords(findUsername);
                if (res.length != 0) {
                    request.setAttribute("userExist", "Username is already taken");
                    request.getRequestDispatcher("/viewer/SignUpClient.jsp").forward(request, response);
                }
                
                boolean inserted = db.insertUser(new String[] {username, password, "client", "true"});
                boolean insertRole = false;

                if (inserted)
                    insertRole = db.insertClient(new String[] {username, fullName, address, type});
                
                if (insertRole) {
                    request.getRequestDispatcher("/viewer/Login.jsp").forward(request, response);
                }
                
            }
            
            else {                
                request.setAttribute("errRepeatPw", "Confirmation Password not Correct");
                
                // send these back so user dont have to enter again
                request.setAttribute("nameSignup", username);
                request.setAttribute("fullNameSignup", fullName);
                request.setAttribute("typeSignup", type);
                request.setAttribute("addressSignup", address);

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
