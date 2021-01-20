package controller.client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
 * @author ah2dam
 */
public class ManageAppointment extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            Connection con = (Connection) getServletContext().getAttribute("con");
            OperationDao operationDao = new OperationDao(con);
            ClientDAO clientDao = new ClientDAO(con);
            

            HttpSession session = request.getSession();
            ArrayList<Operation> schedule = new ArrayList<Operation>();
            Client client = clientDao.getClientData((String) session.getAttribute("fullName")); 
            
            // first load
            if (session.getAttribute("confirm-cancel") == null) {
                schedule = operationDao.getScheduleByCliId(client.getId());

                session.setAttribute("schedule", schedule);

                request.getRequestDispatcher("/viewer/client/ManageAppointment.jsp").forward(request, response);
            } 
            // if client confirm cancel operation
            else if (request.getParameter("confirm-cancel") != null) {
                int paramSize = request.getParameterMap().keySet().size();
                String[] keySet = request.getParameterMap().keySet().toArray(new String[paramSize]);
                
                // if client doesn't change anything, send back
                if (paramSize == 1) {   // only the submit button
                    System.out.print("Only submit button");
                    request.setAttribute("changes-made", new ArrayList<Integer>());
                    request.getRequestDispatcher("/viewer/client/ManageAppointment.jsp").forward(request, response);
                } 
                else {
                    ArrayList<Integer> changed_ids = new ArrayList<Integer>();

                    for (int i=0; i < paramSize-1; i++){
                        String opId = keySet[i].replaceAll("cancel-", "");
                        changed_ids.add(Integer.parseInt(opId));
                        operationDao.cancelSchedule(opId);
                    }
                    
                    // re-fetch schedule
                    schedule = operationDao.getScheduleByCliId(client.getId());

                    session.setAttribute("schedule", schedule);
                    request.setAttribute("changes-made", changed_ids);
                    request.getRequestDispatcher("/viewer/client/ManageAppointment.jsp").forward(request, response);


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
