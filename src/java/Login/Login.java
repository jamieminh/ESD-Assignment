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
 * @author Jamie + Bao Bui
 */
public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            DBBean db = new DBBean();
            Connection con = (Connection) getServletContext().getAttribute("con");
            db.connect(con);

            // get user token from cookie
            Cookie[] cookies = request.getCookies();
            String tokenCk = null;
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        tokenCk = cookie.getValue();
                    }
                }
            }

            String role = "";           // user role
            String name = "Admin";      // user full name
            boolean flag = true;        // true will continue to successful login

            // --------------- COOKIE LOGIN ------------------
            if (tokenCk != null) {
                System.out.println("[FROM COOKIE LOGIN]");

                String[][] record = db.getRecords("SELECT uname, role FROM APP.USERS WHERE token='" + tokenCk + "' ");
                String uname = record[0][0];
                role = record[0][1];

                if (role.equals("client")) {
                    name = db.getRecords("SELECT cname FROM APP.clients WHERE uname='" + uname + "'")[0][0];
                } else if (!role.equals("admin")) {
                    name = db.getRecords("SELECT ename FROM APP.employees WHERE uname='" + uname + "'")[0][0];
                }
            } // --------------- FORM LOGIN ------------------
            else {
                System.out.println("[FROM FORM LOGIN]");
                String _username = request.getParameter("username").trim();
                String _password = request.getParameter("password").trim();
                String hasedPw = new HashPassword().hashPassword(_password);    // hash password

                String[][] record = db.getRecords(String.format("SELECT role, authorized FROM APP.USERS WHERE uname='%s' AND passwd='%s'", _username, hasedPw));

                // if found, then username and password is correct
                if (record.length != 0) {
                    System.out.println("[CORRECT USERNAME AND PASSWORD]");
                    String authorized = record[0][1];

                    // login with unauthorized credentials --> error 
                    if (authorized.equals("false")) {
                        flag = false;
                        request.setAttribute("authorized", false);
                        out.print("<small class=\"Error Error-Login\">Unauthorized User</small>");
                        request.getRequestDispatcher("/Login.html").include(request, response);
                    } 
                    // authorized credentials --> login
                    else {
                        System.out.println("[AUTHORIZED CREDENTIALS]");
                        role = record[0][0];
                        if (role.equals("client")) {
                            name = db.getRecords("SELECT cname FROM APP.clients WHERE uname='" + _username + "'")[0][0];
                        } else if (!role.equals("admin")) {
                            name = db.getRecords("SELECT ename FROM APP.employees WHERE uname='" + _username + "'")[0][0];
                        }

                        // if 'Remember me' box is checked
                        if (request.getParameter("remember-me") != null) {
                            System.out.println("Remember: ON");
                            String token = db.getRecords("SELECT token FROM APP.users WHERE uname='" + _username + "'")[0][0];
                            Cookie setTokenCk = new Cookie("token", token);
                            setTokenCk.setMaxAge(5 * 24 * 60 * 60);   // 10 days
                            response.addCookie(setTokenCk);
                        }
                    }

                } // if user input are incorrect
                else {
                    flag = false;
                    System.out.println("[FROM INCORRECT CREDENTIALS]");
                    out.print("<small class=\"Error Error-Login\">Your Username or Password is Incorrect</small>");
                    request.getRequestDispatcher("/Login.html").include(request, response);
                }
            }

            // if all login info is correct
            if (flag) {
                HttpSession session = request.getSession(); // set session

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

                response.sendRedirect("/viewer/Home.jsp");
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
