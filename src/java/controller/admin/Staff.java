/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.pojo.Employee;
import dao.EmployeeDao;

/**
 *
 * @author Jamie
 */
public class Staff extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Connection con = (Connection) getServletContext().getAttribute("con");
            EmployeeDao employeeDao = new EmployeeDao(con);

            HttpSession session = request.getSession();

            // load data for the first time
            if (request.getParameter("staff-submit") == null) {
                ArrayList<Employee> staffs = employeeDao.getAllEmployees();
                ArrayList<String[]> curStates = employeeDao.getFormChanges(staffs);
                
                request.setAttribute("staffs", staffs);
                session.setAttribute("curentStaffStates", curStates);

                request.getRequestDispatcher("/viewer/admin/Staff.jsp").forward(request, response);
            } 
            // confirm button clicked
            else {
                ArrayList<String[]> oldStates = (ArrayList<String[]>) session.getAttribute("curentStaffStates");    // old states
                ArrayList<String[]> newChanges = new ArrayList<String[]>();             // new states

                // store usernames and their checked status
                for (String[] emp : oldStates) {
                    boolean oldAuth = Boolean.parseBoolean(emp[2]);                      // old auth state
                    boolean newAuth = request.getParameter("auth-" + emp[0]) != null;    // true if not null

                    String oldRate = emp[1];                                             // old rate
                    String newRate = request.getParameter("price-" + emp[0]);            // new rate   

                    // if either rate or auth is changed, or both
                    if (!newRate.equals("") || oldAuth != newAuth) {
                        newRate = newRate.equals("") ? oldRate : newRate;       // set rate to newRate if there's rate change
                        newAuth = oldAuth == newAuth ? oldAuth : newAuth;       // set auth to newAuth if auth is changed
                        newChanges.add(new String[]{emp[0], newRate, String.valueOf(newAuth)});
                    }
                }

                if (newChanges.isEmpty()) { // if there's no changes, re-fetch data, and send back
                    ArrayList<Employee> staffs = employeeDao.getAllEmployees();
                    request.setAttribute("staffs", staffs);
                    request.getRequestDispatcher("/viewer/admin/Staff.jsp").forward(request, response);
                } else {
                    // update db
                    for (String[] user : newChanges) 
                        employeeDao.updateRateAuth(user[0], user[1], user[2]);
                    
                    // re-fetch info from db
                    ArrayList<Employee> updatedStaff = employeeDao.getAllEmployees();
                    ArrayList<String[]> curStates = employeeDao.getFormChanges(updatedStaff);
                
                    request.setAttribute("staffs", updatedStaff);
                    session.setAttribute("curentStaffStates", curStates);
                    
                    request.setAttribute("newChanges", newChanges);
                    request.getRequestDispatcher("/viewer/admin/Staff.jsp").forward(request, response);
                }
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
