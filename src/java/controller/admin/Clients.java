/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dao.BillingDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.ClientDAO;
import dao.UserDao;
import dao.OperationDao;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpSession;
import model.dbHandler.DBBean;
import model.pojo.Client;
import model.pojo.Operation;

/**
 *
 * @author Jamie + Bao
 */
public class Clients extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            DBBean db = new DBBean();
            Connection con = (Connection) getServletContext().getAttribute("con");
            db.connect(con);
            ClientDAO clientDao = new ClientDAO(con);
            HttpSession session = request.getSession();

            ArrayList<Client> clients = new ArrayList<Client>();

            // first load
            if (request.getParameter("client-submit") == null && request.getParameter("filter-pat-type") == null) {
                clients = clientDao.getAllClients();
                session.setAttribute("current-pat-type", "all");
                request.setAttribute("clients", clients);
                request.getRequestDispatcher("/viewer/admin/Clients.jsp").forward(request, response);
            }// if admin chose a specific type
            else if (request.getParameter("filter-pat-type") != null) {
                String ctype = request.getParameter("patient-type");  // the emplopyee that admin chose
                clients = getClientType(ctype, con);

                request.setAttribute("clients", clients);
                session.setAttribute("current-pat-type", ctype);

                request.getRequestDispatcher("/viewer/admin/Clients.jsp").forward(request, response);

            } // delete client
            else if (request.getParameter("client-submit") != null) {
                int paramSize = request.getParameterMap().keySet().size();
                String[] keySet = request.getParameterMap().keySet().toArray(new String[paramSize]);

                // if admin doesn't change anything, send back
                if (paramSize == 1) {   // only the submit button
                    request.setAttribute("changes-made", "0");
                    clients = getClientType((String) session.getAttribute("current-pat-type"), con);
                    request.setAttribute("clients", clients);
                    request.getRequestDispatcher("/viewer/admin/Clients.jsp").forward(request, response);
                } else {
                    int changedCount = paramSize - 1;
                    BillingDao billingDao = new BillingDao(con);
                    OperationDao operationDao = new OperationDao(con);
                    UserDao userDao = new UserDao(con);

                    for (int i = 0; i < paramSize - 1; i++) {
                        int cliId = Integer.parseInt(keySet[i].replaceAll("client-", "")); // get client id from parameter
                        Client client = clientDao.getClientById(cliId);   // get uname to later delete user

                        // cancel operation related to this user from today
                        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
                        String today = formatDate.format(new Date()); // today
                        String nowTime = formatTime.format(new Date()); // today
                        ArrayList<Operation> futureOps = operationDao.getAllScheduleFromTo(today, "2099-12-31", nowTime, cliId); // delete billing from operation

                        for (Operation op : futureOps) {
                            operationDao.cancelSchedule(String.valueOf(op.getId()));    // cancel operation
                            billingDao.removeBilling(op.getId());       // delete related billing
                        }
                        clientDao.deleteClient(cliId);
                        userDao.deleteUser(client.getUsername());

                    }
                    // re-fetch clients
                    clients = getClientType((String) session.getAttribute("current-pat-type"), con);

                    request.setAttribute("changes-made", String.valueOf(changedCount));
                    request.setAttribute("clients", clients);
                    request.getRequestDispatcher("/viewer/admin/Clients.jsp").forward(request, response);
                }
            }
        }
    }

    public ArrayList<Client> getClientType(String ctype, Connection con) {
        ArrayList<Client> clients = new ArrayList<Client>();
        ClientDAO clientDao = new ClientDAO(con);

        if (ctype.equals("all")) {
            clients = clientDao.getAllClients();
        } else if (ctype.equals("nhs")) {
            clients = clientDao.getAllNHSClients();
        } else if (ctype.equals("private")) {
            clients = clientDao.getAllPrivateClients();
        }

        return clients;
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
