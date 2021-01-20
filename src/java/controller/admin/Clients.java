/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.ClientDAO;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import model.dbHandler.DBBean;
import model.pojo.Client;

/**
 *
 * @author WIN 10
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

//            HttpSession session = request.getSession();
            if (request.getParameter("client-submit") == null) {
                ArrayList<Client> clients = clientDao.getAllEmployees();
//                ArrayList<String[]> curStates = clientDao.getFormChanges(staffs);

                request.setAttribute("clients", clients);

                request.getRequestDispatcher("/viewer/admin/Clients.jsp").forward(request, response);
            } else {
//                out.print(1);

                ArrayList<Client> clients = clientDao.getAllEmployees();
//                out.print(clients);
                for (Client client : clients) {
//                    out.print(1);
                    if (request.getParameter("client" + client.getId() + "") == null) {
//                        out.print(request.getParameter("client" + client.getId() + ""));

//                        out.print(client.getFullName());
                    } else {
                        db.deleteClient(client.getId());
                        db.deleteUser(client.getUsername());
                        out.print(request.getParameter("client" + client.getId() + ""));
                        out.print(client.getFullName());
                        request.getRequestDispatcher("/viewer/admin/Clients.jsp").forward(request, response);

                    }
                }
//                String a = request.getParameter("client1");
//                if(a == null){
//                    
//                    out.print(1);
//                }
//                else
//                {
//                    out.print(a);
//                    out.print(a.getClass().getName());
//                    out.print(2);
//                }
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
