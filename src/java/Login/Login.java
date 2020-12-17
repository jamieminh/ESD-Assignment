package Login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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

            String[][] record = db.getRecords(String.format("SELECT role, authorized FROM APP.USERS WHERE uname='%s' AND passwd='%s'", _username, _password));

            // if found, then username and password is correct
            if (record.length != 0) {
                String authorized = record[0][1];

                // login with unauthorized username and info
                if (authorized.equals("false")) {
                    request.setAttribute("authorized", false);
                    out.print("<small class=\"Error Error-Login\">Unauthorized User</small>");
                    request.getRequestDispatcher("/Login.html").include(request, response);
                } else {

                    HttpSession session = request.getSession();
                    String role = record[0][0];
                    String name = "Admin";
                    if (role.equals("client")) {
                        name = db.getRecords("SELECT cname FROM APP.clients WHERE uname='" + _username + "'")[0][0];
                    } else if (!role.equals("admin")) {
                        name = db.getRecords("SELECT ename FROM APP.employees WHERE uname='" + _username + "'")[0][0];
                    }

                    String[] pages;
                    switch (role) {
                        case "admin":
                            pages = new String[]{"Add Employees", "Cancel Surgery", "Change Prices", "Produce Documents"};
                            break;
                        case "client":
                            pages = new String[]{"Book Appointment", "See Schedule", "Request Prescription"};
                            break;
                        case "doctor":
                            pages = new String[]{"See Schedule", "Issue Prescription", "Forward Patient"};
                            break;
                        case "nurse":
                            pages = new String[]{"See Schedule", "Issue Prescription"};
                            break;
                        default:
                            pages = new String[]{};
                    }

                    session.setAttribute("isLoggedIn", true);
                    session.setAttribute("fullName", name);
                    session.setAttribute("role", role);
                    session.setAttribute("title", "Dashboard: " + name);
                    session.setAttribute("folderUrl", "/viewer/" + role + "/");
                    session.setAttribute("pages", pages);

                    // set cookie to remember usr
                    Cookie username = new Cookie("user", _username);
                    username.setMaxAge(5 * 24 * 60 * 60);   // 10 days
                    response.addCookie(username);

                    response.sendRedirect("/viewer/Home.jsp");
                }
            } // if user input are incorrect
            else {
                out.print("<small class=\"Error Error-Login\">Your Username or Password is Incorrect</small>");
                request.getRequestDispatcher("/Login.html").include(request, response);

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
