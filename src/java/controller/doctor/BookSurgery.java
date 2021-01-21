/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.doctor;

import dao.ClientDAO;
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.pojo.Client;

/**
 *
 * @author Jamie
 */
public class BookSurgery extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Connection con = (Connection) getServletContext().getAttribute("con");

            HttpSession session = request.getSession();
            ClientDAO clientDao = new ClientDAO(con);

            if (request.getParameter("book-surgery") == null) {
                ArrayList<Client> clients = clientDao.getAllClients();
                request.setAttribute("patient-list", clients);
                request.getRequestDispatcher("/viewer/doctor/BookSurgery.jsp").forward(request, response);
            } else {
                OperationDao operationDao = new OperationDao(con);
                String patId = request.getParameter("surgery-patient");
                String surgDate = request.getParameter("surgery-date");
                String surgTime = request.getParameter("surgery-time");
                String description = request.getParameter("surgery-description");
                String empId = (String) session.getAttribute("roleId");

                Date today = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                String date_time = surgDate + "T" + surgTime + ":00"; // "yyyy-MM-ddTHH:mm:ss";
                Date chosenDate = formatter.parse(date_time);

                boolean datePassed = chosenDate.compareTo(today) != 1;  // true if date have passed

                ArrayList<Client> clients = clientDao.getAllClients();
                request.setAttribute("patient-list", clients);
                
                if (datePassed) {
                    request.setAttribute("result", "failed");
                    request.getRequestDispatcher("/viewer/doctor/BookSurgery.jsp").forward(request, response);

                } else {
                    operationDao.addSurgery(Integer.parseInt(empId), Integer.parseInt(patId), surgDate, surgTime, description);

                    request.setAttribute("result", "success");
                    request.getRequestDispatcher("/viewer/doctor/BookSurgery.jsp").forward(request, response);

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
            Logger.getLogger(BookSurgery.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(BookSurgery.class.getName()).log(Level.SEVERE, null, ex);
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
