package model.Admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.dbHandler.DBBean;

/**
 *
 * @author Jamie
 */
public class CancelSurgery extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            DBBean db = new DBBean();
            Connection con = (Connection) getServletContext().getAttribute("con");
            db.connect(con);

            HttpSession session = request.getSession();

            // if 'surgeries' is not set
            if (session.getAttribute("surgeries") == null
                    || ((session.getAttribute("surgeries") != null) && session.getAttribute("surgeries").equals("false"))) {

                String[][] surgeries = getSurgeriesData(db);
                session.setAttribute("surgeries", surgeries);
                response.sendRedirect("/viewer/admin/CancelSurgery.jsp");
            } else {
                int paramSize = request.getParameterMap().keySet().size();
                // if admin doesn't change anything, send back
                if (paramSize == 1) // only the submit button
                {
                    response.sendRedirect("/viewer/admin/CancelSurgery.jsp");
                } else {
                    String[][] surgeries = (String[][]) session.getAttribute("surgeries");
                    // arr to store surgery ids and their cancellation state after submit 
                    String[][] surgChecks = new String[surgeries.length][2];

                    // store surgery ids and their checked status
                    for (int i = 0; i < surgeries.length; i++) {
                        surgChecks[i][0] = surgeries[i][0];
                        boolean check = request.getParameter("surg-" + surgeries[i][0]) != null;
                        surgChecks[i][1] = String.valueOf(check);
                    }

                    for (String[] surg : surgChecks) {
                        if (surg[1].equals("true")) {
                            db.cancelSchedule(surg[0]);
                        }
                    }

                    // re-fetch data
                    String[][] updated = getSurgeriesData(db);
                    session.setAttribute("surgeries", updated);
                    response.sendRedirect("/viewer/admin/CancelSurgery.jsp");
                }

            }
        }
    }

    String[][] getSurgeriesData(DBBean db) {
        Date today = new Date();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(today);
        String time = new SimpleDateFormat("HH:mm:ss").format(today);
        // select where the type is surgery AND not cancelled AND
        String schedule = String.format("SELECT sid, eid, cid, sdate, stime FROM APP.SCHEDULE "
                + "WHERE cancelled='false' AND stype='surgery' "
                + "AND ( (sdate > '%s') OR (sdate = '%s' AND stime > '%s') ) ", date, date, time);
        String[][] records = db.getRecords(schedule);

        String[][] surgeries = new String[records.length][5];

        for (int i = 0; i < surgeries.length; i++) {
            String[] rec = records[i];
            String[] surgery = new String[5];

            String getEmp = "SELECT ename FROM APP.EMPLOYEES WHERE eid=" + rec[1];
            String getPat = "SELECT cname FROM APP.CLIENTS WHERE cid=" + rec[2];
            String empName = db.getRecords(getEmp)[0][0];
            String patName = db.getRecords(getPat)[0][0];
            surgery[0] = rec[0];    // surgery id
            surgery[1] = empName;   // employee name
            surgery[2] = patName;   // patient name
            surgery[3] = rec[3];    // date
            surgery[4] = rec[4];    // time
            surgeries[i] = surgery;
        }
        return surgeries;
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
