package controller.entry;

import com.HashPassword;
import dao.EntryDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.pojo.User;
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
            Connection con = (Connection) getServletContext().getAttribute("con");

            EntryDao entryDao = new EntryDao(con);

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
            String token = null;        // cookie login token
            boolean flag = true;        // true will continue to successful login

            // ----------- COOKIE LOGIN ------------
            if (tokenCk != null) {
                String[] userInfo = entryDao.cookieLogin(tokenCk);
                role = userInfo[0];
                name = userInfo[1].equals("") ? name : userInfo[1]; // if there's no name, set it to "Admin"
            } 
            // ----------- FORM LOGIN --------------
            else {
                String _username = request.getParameter("username").trim();
                String _password = request.getParameter("password").trim();
                boolean isRemember = request.getParameter("remember-me") != null;

                User user = new User();
                user.setUsername(_username);
                user.setPassword(_password);
                String[] userInfo = entryDao.formLogin(user, isRemember);

                if (userInfo.length == 1) {
                    flag = false;
                    out.print("<small class=\"Error Error-Login\">" + userInfo[0] + "</small>");
                    request.getRequestDispatcher("/Login.html").include(request, response);
                } else {
                    role = userInfo[0];
                    name = userInfo[1];
                    token = userInfo[2];
                    if (token != null) {
                        Cookie setTokenCk = new Cookie("token", token);
                        setTokenCk.setMaxAge(5 * 24 * 60 * 60);   // 10 days
                        response.addCookie(setTokenCk);
                    }
                }
            }

            
            // if all login info is correct
            if (flag) {
                HttpSession session = request.getSession(); // set session

                String[] pages;
                String[] pagesIcons;
                String userPic = "/assets/users/";
                switch (role) {
                    case "admin":
                        pages = new String[]{"Staff", "Clients", "Cancel Surgery", "Documents"};
                        pagesIcons = new String[] {"user-md", "user-injured", "syringe", "file-invoice"};
                        userPic += "admin.png";
                        break;
                    case "client":
                        pages = new String[]{"Book Appointment", "See Schedule", "Request Prescription"};
                        pagesIcons = new String[] {"this is", "just", "a", "placeholder"};
                        userPic += "client.jpg";
                        break;
                    case "doctor":
                        pages = new String[]{"See Schedule", "Issue Prescription", "Forward Patient"};
                        pagesIcons = new String[] {"this is", "just", "a", "placeholder"};
                        userPic += "doctor.jpg";
                        break;
                    case "nurse":
                        pages = new String[]{"See Schedule", "Issue Prescription"};
                        pagesIcons = new String[] {"this is", "just", "a", "placeholder"};
                        userPic += "nurse.jpg";
                        break;
                    default:
                        pages = new String[]{};
                        pagesIcons = new String[] {};
                        userPic += "error.jpg";
                }

                session.setAttribute("isLoggedIn", true);
                session.setAttribute("fullName", name);
                session.setAttribute("role", role);
                session.setAttribute("title", "Dashboard: " + name);
                session.setAttribute("folderUrl", "/viewer/" + role + "/");
                session.setAttribute("pages", pages);
                session.setAttribute("pagesIcons", pagesIcons);
                session.setAttribute("userPic", userPic);

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
