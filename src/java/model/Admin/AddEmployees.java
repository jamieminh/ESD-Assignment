/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Set;
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
public class AddEmployees extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            DBBean db = new DBBean();
            Connection con = (Connection) getServletContext().getAttribute("con");
            db.connect(con);

            HttpSession session = request.getSession();
            // if unAuthStaff is not set, or is set but set to false
            // meaning the data is not loaded or, the admin has made some change
            // and now the data must be updated
            if (session.getAttribute("unAuthStaff") == null
                    || ((session.getAttribute("unAuthStaff") != null) && session.getAttribute("unAuthStaff").equals("false"))) {

                String[][] unAuthEmps = getUsersData(db);
                session.setAttribute("unAuthStaff", unAuthEmps);
                response.sendRedirect("/viewer/admin/AddEmployees.jsp");
            } else {
                Set<String> paramNames = request.getParameterMap().keySet();
                // if admin doesn't change anything, send back
                if (paramNames.size() == 1) {// only the submit button                
                    response.sendRedirect("/viewer/admin/AddEmployees.jsp");
                }

                String[][] unAuthEmps = (String[][]) session.getAttribute("unAuthStaff");
                // arr to store usernames and their auth state after submit 
                String[][] usersChecks = new String[unAuthEmps.length][2];

                // store usernames and their checked status
                for (int i = 0; i < unAuthEmps.length; i++) {
                    usersChecks[i][0] = unAuthEmps[i][0];
                    boolean check = request.getParameter("auth-" + unAuthEmps[i][0]) != null;
                    usersChecks[i][1] = String.valueOf(check);
                }

                // update db
                for (String[] user : usersChecks) {
                    if (user[1].equals("true")) {
                        db.authorizeUser(user[0]);
                    }
                }

                session.setAttribute("unAuthStaff", "false");
                request.setAttribute("success", true);
                request.getRequestDispatcher("/viewer/admin/AddEmployees.jsp").forward(request, response);
            }

            String[][] updated = getUsersData(db);
            session.setAttribute("unAuthStaff", updated);
            response.sendRedirect("/viewer/admin/AddEmployees.jsp");
        }

    }

    String[][] getUsersData(DBBean db) {
        String[][] records = db.getRecords("SELECT uname, role FROM APP.USERS WHERE authorized='false'");
        String[][] unAuthEmps = new String[records.length][5];

        for (int i = 0; i < records.length; i++) {
            String[] rec = records[i];
            String getEmployee = "SELECT ename, eaddress, erate FROM APP.EMPLOYEES WHERE uname='" + rec[0] + "'";
            String[] empInfo = db.getRecords(getEmployee)[0];

            // [username, full name, address, role, rate]
            String[] fullInfo = new String[]{rec[0], empInfo[0], empInfo[1], rec[1], empInfo[2]};
            unAuthEmps[i] = fullInfo;
        }

        return unAuthEmps;
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
