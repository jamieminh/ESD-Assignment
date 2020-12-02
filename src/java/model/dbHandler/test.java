package model.dbHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jamie
 */
public class test extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            DBBean db = new DBBean();

            Connection con = (Connection) getServletContext().getAttribute("con");
            db.connect(con);
            boolean in = true;

            boolean in1 = db.insertUser(new String[] {"josie", "restingputface", "nurse"});
            boolean in2 = db.insertUser(new String[] {"hope", "tribid", "client"});
            boolean in3 = db.insertEmployee(new String[] {"josie", "Josie Saltzman", "Mystic Falls"});
            boolean in4 = db.insertClient(new String[] {"hope", "Hope Michaelson", "New Orleans", "private"});
            boolean in5 = db.insertOperation(new String[] {"Josie Saltzman", "Hope Michaelson", "2021", "1", "24", "21", "00", "1", "4533"});
            boolean in6 = db.insertBooking(new String[] {"Josie Saltzman", "Hope Michaelson", "2021", "1", "20", "8", "05"});


            // check if inserting is successful
            out.print("<h3> insert successful? " + in6 + "</h3>");
            String[][] res = db.getAllRecords("users");
            for (String[] rec : res) {
                for (String col : rec) 
                    out.print(col + " __ ");
                out.print("<br>");
            }
            
            // get records using query
//            String[][] select = db.getRecords("SELECT * FROM app.clients WHERE ctype = 'NHS'");
//            for (String[] rec : select) {
//                for (String col : rec) 
//                    out.print(col + " __ ");
//                out.print("<br>");
//            }

//            String[][] find = db.getRecords("SELECT cid FROM app.clients WHERE cname='Hope Michaelson'");
//            String eid = find[0][0];
//            out.print(eid);
            

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
