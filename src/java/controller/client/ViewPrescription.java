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
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.pojo.Client;
import model.pojo.Operation;

/**
 *
 * @author LAPTOPVTC.VN
 */
public class ViewPrescription extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
            /* TODO output your page here. You may use following sample code. */
            Connection con = (Connection) getServletContext().getAttribute("con");
            OperationDao operationDao = new OperationDao(con);
            ClientDAO clientDao = new ClientDAO(con);
            

            HttpSession session = request.getSession();
            ArrayList<Operation> prescription = new ArrayList<Operation>();
            Client client = clientDao.getClientData((String) session.getAttribute("fullName")); 
            
            // first load
            if (request.getParameter("confirm-aprove") == null) {
                prescription = operationDao.getPrescriptionByCliId(client.getId());

                request.setAttribute("prescription", prescription);

                request.getRequestDispatcher("/viewer/client/ViewPrescription.jsp").forward(request, response);
            } 
            // if client confirm cancel operation
            else if (request.getParameter("confirm-aprove") != null) {
                int paramSize = request.getParameterMap().keySet().size();
                String[] keySet = request.getParameterMap().keySet().toArray(new String[paramSize]);
                
                // if client doesn't change anything, send back
                if (paramSize == 1) {   // only the submit button
                    System.out.print("Only request button");
                    request.setAttribute("changes-made", new ArrayList<Integer>());
                    request.getRequestDispatcher("/viewer/client/ViewPrescription.jsp").forward(request, response);
                } 
                else {
                    ArrayList<Integer> changed_ids = new ArrayList<Integer>();
                    BillingDao billingDao = new BillingDao(con);

                    for (int i=0; i < paramSize-1; i++){
                        String opId = keySet[i].replaceAll("cancel-", "");
                        changed_ids.add(Integer.parseInt(opId));
                        operationDao.aprovePrescription(opId);                  // cancel in schedule db
                        billingDao.removeBilling(Integer.parseInt(opId));   // delete from billing db
                    }
                    
                    // re-fetch schedule
                    prescription = operationDao.getScheduleByCliId(client.getId());

                    request.setAttribute("prescription", prescription);
                    request.setAttribute("changes-made", changed_ids);
                    request.getRequestDispatcher("/viewer/client/ViewPrescription.jsp").forward(request, response);


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
