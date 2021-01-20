/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dao.BillingDao;
import dao.OperationDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dbHandler.DBBean;
import model.pojo.Billing;
import model.pojo.Operation;

/**
 *
 * @author Admin
 */
public class Documents extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            DBBean db = new DBBean();
            Connection con = (Connection) getServletContext().getAttribute("con");
            db.connect(con);
            BillingDao billingDao = new BillingDao(con);
            OperationDao opeDao = new OperationDao(con);

            System.out.println(request.getParameter("datefrom"));
            System.out.println(1111111111);
            System.out.println(request.getParameter("dateto"));
            
//            request.setAttribute("datefrom", request.getParameter("datefrom"));
            if (request.getParameter("billing-month") == null || (request.getParameter("dateto") == null && request.getParameter("datefrom") == null )) {
                ArrayList<Operation> operations = opeDao.getAllSchedule();
//                ArrayList<Billing> billingList = billingDao.getAllBillings();
                ArrayList<String[]> arrOfBills = new ArrayList<String[]>();
                for (Operation ope : operations) {
                    int SID = ope.getId();
                    Billing bill = billingDao.getBillingBySID(SID);
                    arrOfBills.add(new String[]{ope.getEmployee().getFullName(),
                        ope.getClient().getFullName(),
                        ope.getType(),
                        Float.toString(ope.getEmployee().getRate()),
                        Integer.toString(ope.getnSlot()),
                        ope.getDate(),
                        ope.getTime(),
                        Float.toString(ope.getEmployee().getRate() * ope.getnSlot())
                    });
                }

                request.setAttribute("billings", arrOfBills);

                request.getRequestDispatcher("/viewer/admin/Documents.jsp").forward(request, response);

            } else if (request.getParameter("datefrom") != null && request.getParameter("dateto") != null) {
                
                ArrayList<Operation> operations = opeDao.getAllScheduleFromTo(request.getParameter("datefrom"), request.getParameter("dateto"));
                ArrayList<String[]> arrOfBills = new ArrayList<String[]>();
                for (Operation ope : operations) {
                    System.out.println(ope.getDate().getClass().getName());
                    int SID = ope.getId();
                    Billing bill = billingDao.getBillingBySID(SID);
                    arrOfBills.add(new String[]{ope.getEmployee().getFullName(),
                        ope.getClient().getFullName(),
                        ope.getType(),
                        Float.toString(ope.getEmployee().getRate()),
                        Integer.toString(ope.getnSlot()),
                        ope.getDate(),
                        ope.getTime(),
                        Float.toString(ope.getEmployee().getRate() * ope.getnSlot())
                    });
                }

                request.setAttribute("billings", arrOfBills);

                request.getRequestDispatcher("/viewer/admin/Documents.jsp").forward(request, response);
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
