/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dao.EmployeeDao;
import dao.OperationDao;
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
import model.pojo.Operation;

/**
 *
 * @author Jamie
 */
public class StaffSchedule extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            Connection con = (Connection) getServletContext().getAttribute("con");
            EmployeeDao employeeDao = new EmployeeDao(con);
            OperationDao operationDao = new OperationDao(con);

            HttpSession session = request.getSession();
            
            if (request.getParameter("submit") == null) {
                ArrayList<Employee> staffs = employeeDao.getAllEmployees();
//                ArrayList<String[]> curStates = employeeDao.getFormChanges(staffs);
                ArrayList<Operation> schedule = operationDao.getAllSchedule();
                                
                
                for (Operation op : schedule) {
                    out.print(op.getId() + " __ " + op.getClient().getFullName() + " __ " + op.getEmployee().getFullName() + " __ "+ op.getType() 
                            + " __ " + op.getDate() + " __ "+ op.getTime() + " __ " + op.getnSlot() + " __ " + op.isIsCancelled() + "<br>");
                }


                request.setAttribute("staffs", staffs);
                request.setAttribute("schedule", schedule);
//                session.setAttribute("curentStaffStates", curStates);

                request.getRequestDispatcher("/viewer/admin/StaffSchedule.jsp").forward(request, response);
            }
            else {
//                String emp_uname = request.getParameter("staff-name");
//                out.print(emp_uname);
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
