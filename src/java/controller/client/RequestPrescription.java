/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.client;

import dao.ClientDAO;
import dao.EmployeeDao;
import dao.OperationDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.pojo.Client;
import model.pojo.Employee;
import model.pojo.Operation;

/**
 *
 * @author LAPTOPVTC.VN
 */
@WebServlet(name = "RequestPrescription", urlPatterns = {"/RequestPrescription"})
public class RequestPrescription extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession(); // set session

            //connect DAO
            Connection con = (Connection) getServletContext().getAttribute("con");
            ClientDAO clientDao = new ClientDAO(con);
            EmployeeDao employeeDao = new EmployeeDao(con);
            OperationDao operationDao = new OperationDao(con);

            ArrayList<Employee> staffsList = employeeDao.getAllEmployees();
            session.setAttribute("staffs", staffsList);

            // load data for the first time
            if (request.getParameter("request-submit") == null) {
                request.getRequestDispatcher("/viewer/client/RequestPrescription.jsp").forward(request, response);
            } else {
                //get input data
                String staffid = request.getParameter("staff-required").trim();
                String prescription = request.getParameter("prescription").trim();

                //get require staff data
                Employee requireEmployee = employeeDao.getEmpById(Integer.parseInt(staffid));
                //get client data
                Client client = clientDao.getClientData((String) session.getAttribute("fullName"));

                Operation operation = new Operation();
                operation.setEmployee(requireEmployee);
                operation.setClient(client);
                operation.setType("appointment");
                operation.setIsAproved(false);
                operation.setPrescription(prescription);
                 boolean res = operationDao.addAppointment(operation);
                    if (res) {
                        response.sendRedirect("/viewer/client/ViewPrescription.jsp");
                    } else {
                        out.print("<small class=\"Error Error-Booking\">There's been some error. Please try again later.</small>");
                        request.getRequestDispatcher("/viewer/client/RequestPrescription.jsp").include(request, response);
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(RequestPrescription.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(RequestPrescription.class.getName()).log(Level.SEVERE, null, ex);
        }
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
