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
 * on /jamie branch
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

//            boolean in1 = db.insertUser(new String[] {"raelle", "deathwish", "doctor"});
//            boolean in2 = db.insertUser(new String[] {"scylla", "notaspree", "client"});
//            boolean in3 = db.insertEmployee(new String[] {"raelle", "Raelle Collar", "Chippewa Cession, Carolina", "140"});
//            boolean in4 = db.insertClient(new String[] {"scylla", "Scylla Ramshorn", "Fort Salem", "NHS"});
//            boolean in5 = db.insertSchedule(new String[] {"Raelle Collar", "Scylla Ramshorn", "appointment", "2021-01-21", "14:40", "4"});
//            boolean in6 = db.insertSchedule(new String[] {"Raelle Collar", "Scylla Ramshorn", "surgery", "2021-01-22", "19:00", "1"});
//            boolean in7 = db.insertBilling(new String[] {"Raelle Collar", "2021-01-22", "19:00", "1223"});   // surgery
//            boolean in8 = db.insertBilling(new String[] {"Raelle Collar", "2021-01-21", "14:40", "thisfielddoesn'tmatter"});   // appointment


            // check if inserting is successful
            out.print("<h3> insert successful? " + in + "</h3>");
            String[][] res = db.getAllRecords("users");
            for (String[] rec : res) {
                for (String col : rec) 
                    out.print(col + " __ ");
                out.print("<br>");
            }
            
//             get records using query
//            String[][] select = db.getRecords(String.format("SELECT sid,stype,nslot FROM app.schedule "
//                            + "WHERE eid=%s AND sdate='%s' AND stime='%s'", 4, "2021-01-21", "14:40"));
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