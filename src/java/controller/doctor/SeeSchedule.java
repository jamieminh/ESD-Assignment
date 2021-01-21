/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.doctor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import dao.EmployeeDao;
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
import model.pojo.Employee;
import model.pojo.Operation;

/**
 *
 * @author 14736
 */
public class SeeSchedule extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            Connection con = (Connection) getServletContext().getAttribute("con");
            EmployeeDao employeeDao = new EmployeeDao(con);
            OperationDao operationDao = new OperationDao(con);

            HttpSession session = request.getSession();
            ArrayList<Employee> staffs = employeeDao.getAllEmployees();
            ArrayList<Operation> schedule = new ArrayList<Operation>();
            
            session.setAttribute("staffs", staffs);

            // if See Schedule button is not clicked
            if (session.getAttribute("schedule") == null) {
                schedule = operationDao.getAllSchedule();

                session.setAttribute("schedule", schedule);
                session.setAttribute("current-emp", "all");

                request.getRequestDispatcher("/viewer/doctor/SeeSchedule.jsp").forward(request, response);
            } 
            // if admin chose a specific employee
            else if (request.getParameter("see-schedule") != null) {
                String empId = request.getParameter("staff-name");  // the emplopyee that admin chose
                if (empId.equals("all"))
                    schedule = operationDao.getAllSchedule();
                else
                    schedule = operationDao.getScheduleByEmpId(Integer.parseInt(empId));

                session.setAttribute("schedule", schedule);
                session.setAttribute("current-emp", empId);

                request.getRequestDispatcher("/viewer/doctor/SeeSchedule.jsp").forward(request, response);
            } 
            // if admin confirm cancel operation
            else if (request.getParameter("confirm-cancel") != null) {
                int paramSize = request.getParameterMap().keySet().size();
                String[] keySet = request.getParameterMap().keySet().toArray(new String[paramSize]);
                
                // if admin doesn't change anything, send back
                if (paramSize == 1) {   // only the submit button
                    out.print("Only submit button");
                    request.setAttribute("changes-made", new ArrayList<Integer>());
                    request.getRequestDispatcher("/viewer/doctor/SeeSchedule.jsp").forward(request, response);
                } 
                else {
                    ArrayList<Integer> changed_ids = new ArrayList<Integer>();

                    for (int i=0; i < paramSize-1; i++){
                        String opId = keySet[i].replaceAll("cancel-", "");
                        changed_ids.add(Integer.parseInt(opId));
                        operationDao.cancelSchedule(opId);
                    }
                    
                    // re-fetch schedule
                    if (session.getAttribute("current-emp").equals("all"))
                        schedule = operationDao.getAllSchedule();
                    else
                        schedule = operationDao.getScheduleByEmpId(Integer.parseInt((String)session.getAttribute("current-emp")));

                    session.setAttribute("schedule", schedule);
                    request.setAttribute("changes-made", changed_ids);
                    request.getRequestDispatcher("/viewer/doctor/SeeSchedule.jsp").forward(request, response);


                }
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
