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
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            DBBean db = new DBBean();
            Connection con = (Connection) getServletContext().getAttribute("con");
            db.connect(con);

            OperationDao opeDao = new OperationDao(con);

            if (request.getParameter("billing-month") == null || (request.getParameter("dateto") == null && request.getParameter("datefrom") == null)) {
                ArrayList<Operation> operations = opeDao.getAllSchedulePassedNotCancelled();

                ArrayList<String[]> arrOfBills = getScheduleAsStringArr(operations, con);
                request.setAttribute("billings", arrOfBills);

                request.getRequestDispatcher("/viewer/admin/Documents.jsp").forward(request, response);

            } else if (request.getParameter("datefrom") != null && request.getParameter("dateto") != null) {
                Date today = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                Date datefromDate = formatter.parse(request.getParameter("datefrom"));  // "yyyy-MM-dd";
                Date datetoDate = formatter.parse(request.getParameter("dateto"));      // "yyyy-MM-dd";

                ///// if (datefrom have NOT passed OR datefrom >= dateto), return 
                // compareTo returns -1/0/1 if less/equal/greater
                if (datefromDate.compareTo(today) != -1 || (datefromDate.compareTo(datetoDate) != -1)) {
                    ArrayList<Operation> operations = opeDao.getAllSchedulePassedNotCancelled();
                    ArrayList<String[]> arrOfBills = getScheduleAsStringArr(operations, con);
                    
                    request.setAttribute("date-select-error", "wrong");
                    request.setAttribute("billings", arrOfBills);

                    request.getRequestDispatcher("/viewer/admin/Documents.jsp").forward(request, response);
                }
                else {
                    String dateto = request.getParameter("dateto");
                    // if dateTo > today => set dateTo = today
                    if (datetoDate.compareTo(today) == 1) {
                        String todayStr = formatter.format(new Date());  // today
                        dateto = todayStr;
                    }
                    ArrayList<Operation> operations = opeDao.getAllScheduleFromTo(request.getParameter("datefrom"), dateto);
                    ArrayList<String[]> arrOfBills = getScheduleAsStringArr(operations, con);

                    request.setAttribute("billings", arrOfBills);
                    request.setAttribute("date-to", dateto);
                    request.setAttribute("date-from", request.getParameter("datefrom"));

                    request.getRequestDispatcher("/viewer/admin/Documents.jsp").forward(request, response);
                }               
                
            }
        }
    }

    public ArrayList<String[]> getScheduleAsStringArr(ArrayList<Operation> operations, Connection con) {
        BillingDao billingDao = new BillingDao(con);
        ArrayList<String[]> arrOfBills = new ArrayList<String[]>();
        for (Operation ope : operations) {
            int SID = ope.getId();
            float charge = ope.getEmployee().getRate() * ope.getnSlot();
            if (ope.getType().equals("surgery")) {
                Billing billing = billingDao.getBillingBySID(SID);
                charge = billing.getCharge();
            }
            arrOfBills.add(new String[]{ope.getEmployee().getFullName(),
                ope.getClient().getFullName(),
                ope.getType(),
                Float.toString(ope.getEmployee().getRate()),
                Integer.toString(ope.getnSlot()),
                ope.getDate(),
                ope.getTime(),
                Float.toString(charge)
            });
        }
        return arrOfBills;
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
            Logger.getLogger(Documents.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Documents.class.getName()).log(Level.SEVERE, null, ex);
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
