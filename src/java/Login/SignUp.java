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
            String username = request.getParameter("username").trim();
            String fullName = request.getParameter("fullname").trim();
            String role = request.getParameter("role").trim();
            String address = request.getParameter("address").trim();
            String rate = request.getParameter("rate").trim();
            String password = request.getParameter("password").trim();
            String password_repeat = request.getParameter("repeat-password").trim();
//            out.print(username+""+fullName+""+role+""+address+""+rate+""+password+""+password_repeat+"");
            // if passwords are the same
            if (password.equals(password_repeat)) {
                String findUsername = "SELECT uname FROM APP.USERS WHERE uname='" + username + "'";
                String[][] found = db.getRecords(findUsername);
                if (found.length != 0) {
                    request.setAttribute("userExist", "Username is already taken");
                    request.getRequestDispatcher("/viewer/SignUp.jsp").forward(request, response);
                } //                out.print(1);
                else {
                    boolean inserted = db.insertUser(new String[]{username, password, role, "false"});
                    if (inserted) {
                        boolean insertEmployee = db.insertEmployee(new String[]{username, fullName, address, rate});
                        response.sendRedirect("viewer/Login.jsp");

                    }
                }
//                out.print(1);

//                boolean insertEmployee = db.insertEmployee(new String[]{username, fullName, address, rate});
//                out.print(insertEmployee);
//                    if (inserted) {
//                        out.print(1);
//                        if (insertEmployee) {
//                        }
//                    }
            } //                String authorized = "false";
            //                boolean insertRole;
            //                
            //                if (role.equals("client"))
            //                    authorized = "true";
            //                if (role.equals("client"))
            //                    insertRole = db.insertClient(values)
            else {
                request.setAttribute("errRepeatPw", "Confirmation Password not Correct");

                // send these back so user dont have to enter again
                request.setAttribute("nameSignup", username);
                request.setAttribute("fullNameSignup", fullName);
                request.setAttribute("roleSignup", role);
                request.setAttribute("addressSignup", address);
                request.setAttribute("rateSignup", rate);

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
