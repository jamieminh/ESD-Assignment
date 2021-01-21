/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.client;

import dao.BillingDao;
import dao.ClientDAO;
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
import model.pojo.Billing;
import model.pojo.Client;
import model.pojo.Operation;

/**
 *
 * @author Jamie
 */
public class PayBills extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession(); // set session
            Connection con = (Connection) getServletContext().getAttribute("con");

            ClientDAO clientDao = new ClientDAO(con);
            Client client = clientDao.getClientById(Integer.parseInt((String) session.getAttribute("roleId")));

            ArrayList<String[]> returnedData = new ArrayList<String[]>();
            
            // first load
            if (request.getParameter("pay-confirm") == null) {
                returnedData = allCharges(client.getId(), con);
                request.setAttribute("unpaid-bills", returnedData);
                request.getRequestDispatcher("/viewer/client/PayBills.jsp").forward(request, response);
            }
            // client confirm pay charge(s)
            else {
                int paramSize = request.getParameterMap().keySet().size();
                String[] keySet = request.getParameterMap().keySet().toArray(new String[paramSize]);

                // if admin doesn't change anything, send back
                if (paramSize == 1) {   // only the submit button
                    returnedData = allCharges(client.getId(), con);
                    request.setAttribute("unpaid-bills", returnedData);
                    request.setAttribute("changes-made", "0");
                    request.getRequestDispatcher("/viewer/client/PayBills.jsp").forward(request, response);
                }
                else {
                    int changesMade = paramSize - 1 ;
                    BillingDao billingDao = new BillingDao(con);

                    for (int i = 0; i < paramSize - 1; i++) {
                        String bId = keySet[i].replaceAll("pay-", "");
                        billingDao.payBillBybId(Integer.parseInt(bId));
                    }

                    // re-fetch schedule
                    returnedData = allCharges(client.getId(), con);
                    
                    request.setAttribute("unpaid-bills", returnedData);
                    request.setAttribute("changes-made", String.valueOf(changesMade));
                    request.getRequestDispatcher("/viewer/client/PayBills.jsp").forward(request, response);
                }
                
            }
        }
    }

    public ArrayList<String[]> allCharges(int cliId, Connection con) {
        BillingDao billingDao = new BillingDao(con);

        ArrayList<String[]> allCharges = new ArrayList<String[]>();

        ArrayList<Billing> unpaidBills = billingDao.getAllBillingByCliID(cliId);
        OperationDao operationDao = new OperationDao(con);

        for (Billing bill : unpaidBills) {
            int sid = bill.getOp().getId();
            Operation op = operationDao.getScheduleById(sid);
            String nslot = op.getType().equals("surgery") ? "N/A" : String.valueOf(op.getnSlot());

            // [bid, stype, emp name, rate, slots, description, date, time, charge, isPaid]
            allCharges.add(new String[]{String.valueOf(bill.getbId()),
                op.getType(), op.getEmployee().getFullName(),
                String.valueOf(op.getEmployee().getRate()), nslot, op.getDescription(),
                op.getDate(), op.getTime(), String.valueOf(bill.getCharge()),
                String.valueOf(bill.isIsPaid())});

        }
        return allCharges;
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
