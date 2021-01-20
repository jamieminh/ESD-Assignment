/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.client;

import dao.ClientDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
public class Profile extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession(); // set session
            
            Connection con = (Connection) getServletContext().getAttribute("con");
            ClientDAO clientDao = new ClientDAO(con);
            Client client = clientDao.getClientData((String) session.getAttribute("fullName"));  
            
            if (request.getParameter("submit") == null) {
                session.setAttribute("username", client.getUsername());
                session.setAttribute("address", client.getAddress());
                session.setAttribute("clientType", client.getType());
                session.setAttribute("clientId", String.valueOf(client.getId()));

                session.setAttribute("fetched", true);

                request.getRequestDispatcher("/viewer/client/Profile.jsp").forward(request, response);
            } else {
                String fullName = request.getParameter("fullName");
                String type = request.getParameter("user-type");
                String address = "";
                String username = (String) session.getAttribute("username");
                
                // the parameter "address" only available if user enter a postcode and choose an address
                if(request.getParameter("address") == null)
                    address = (String) session.getAttribute("address"); // get the old address from session
                else 
                    address = request.getParameter("address");          // get the new address              
                
                session.setAttribute("fullName", fullName);
                session.setAttribute("address", address);
                session.setAttribute("clientType", type);

                boolean res = clientDao.updateClient(username, fullName, type, address);
                if (res)
                    out.print("<script>alert(\"Changes Successful\");</script>");
                else 
                    out.print("<script>alert(\"Changes Failed\");</script>");
                
                request.getRequestDispatcher("/viewer/client/Profile.jsp").include(request, response);

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
