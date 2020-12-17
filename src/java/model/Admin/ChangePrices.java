/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
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
public class ChangePrices extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            DBBean db = new DBBean();
            Connection con = (Connection) getServletContext().getAttribute("con");
            db.connect(con);
            HttpSession session = request.getSession();

            // if 'servicesPrice' is not set
            if (session.getAttribute("servicesPrice") == null
                    || ((session.getAttribute("servicesPrice") != null) && session.getAttribute("servicesPrice").equals("false"))) {

                // get employee info
                String[][] empsRates = getEmpsData(db);

                session.setAttribute("servicesPrice", empsRates);
                session.removeAttribute("changedUsers");
                response.sendRedirect("/viewer/admin/ChangePrices.jsp");

            } 
            else {
                Set<String> paramNames = request.getParameterMap().keySet();
                ArrayList<String[]> changedRate = new ArrayList<>();
                ArrayList<String> changedUsers = new ArrayList<>();

                int i = 0;

                for (String param : paramNames) {
                    if (i >= paramNames.size() - 1) // exclude the submit button
                        break;
                    
                    String username = param.replaceAll("serv-", "");    // get username
                    String rate = request.getParameter(param);          // get new rate
                    if (!request.getParameter(param).equals("")) {      // if the rate param is changed
                        String[] userRate = new String[]{username, rate};
                        changedRate.add(userRate);
                        changedUsers.add(username);
                    }
                    i++;
                }

                // update db
                for (String[] userRate : changedRate) {
                    db.changePrice(userRate[0], userRate[1]);
                }

                // re-fetch employee info
                String[][] empsRates = getEmpsData(db);
                session.setAttribute("servicesPrice", empsRates);
                session.setAttribute("changedUsers", changedUsers);
                response.sendRedirect("/viewer/admin/ChangePrices.jsp");
            }
        }
    }

    public String[][] getEmpsData(DBBean db) {
        String[][] employees = db.getRecords("SELECT uname, ename, erate FROM APP.employees");
        String[][] empsRates = new String[employees.length][4];

        for (int i = 0; i < employees.length; i++) {
            String[] emp = employees[i];
            String[] empRate = new String[4];

            String getRole = db.getRecords("SELECT role FROM APP.USERS WHERE uname='" + emp[0] + "' ")[0][0];
            empRate[0] = emp[0];    // emp uname
            empRate[1] = emp[1];    // employee full name
            empRate[2] = getRole;   // role
            empRate[3] = emp[2];    // current rate
            empsRates[i] = empRate;
        }
        return empsRates;
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
