<%-- 
    Document   : StaffSchedule
    Created on : Jan 18, 2021, 7:11:19 PM
    Author     : Jamie
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="model.pojo.Operation"%>
<%@page import="model.pojo.Employee"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<Employee> staffs = new ArrayList<Employee>();
    ArrayList<Operation> schedule = new ArrayList<Operation>();
    ArrayList<Integer> changed_ids = new ArrayList<Integer>();
    String current_emp = "";

    if (session.getAttribute("staffs") == null || request.getAttribute("schedule") == null) {
        response.sendRedirect("/StaffSchedule");
    } else {
        staffs = (ArrayList<Employee>) session.getAttribute("staffs");
        schedule = (ArrayList<Operation>) request.getAttribute("schedule");
        current_emp = (String) session.getAttribute("current-emp");
    }
%>
<!DOCTYPE html>
<jsp:include page="/viewer/Header.jsp"/>

<div class="MainContent">

    <h1>Check Staff Schedule</h1>
    <%
        if (request.getAttribute("changes-made") != null) {
             changed_ids = (ArrayList<Integer>) request.getAttribute("changes-made");

            if ( changed_ids.equals("0"))
                out.print("<div class=\"changes-made\"><em>0 changes made</em></div>");
            else {
                out.print("<div class=\"changes-made\"><em>" + changed_ids.size() + " changes made</em></div>");
            }
        }
    %>

    <div class='instructions'>
        <h4>Instructions</h4>
        <p>Choose a specific employee to see their schedule.</p>
        <p>Greyed-out row means the operation have passed or cancelled, there can be no changes made.</p>
        <p>Cancellation of an operation is <em style="color: red">final</em>.</p>
        <p>Click on the cancel box next to each operation and click confirm to cancel it.</p>
    </div>
    <form action="/StaffSchedule" class="FormTable" method="post">
        <div id="choose-staff">
            <label>Choose Staff: </label>
            <select name="staff-name" required>
                <option value="all" <%=current_emp.equals("all") ? "selected" : ""%> >All Employee</option>
                <%
                    for (Employee emp : staffs) {
                        String value = emp.getId() + ". " + emp.getFullName() + " - " + emp.getRole();
                        String selected = current_emp.equals(String.valueOf(emp.getId())) ? "selected" : "";
                        out.print("<option " + selected + " value=\"" + emp.getId() + "\">" + value + "</option>");
                    }
                %>
            </select>
            <input type="submit" name="see-schedule" value="See Schedule"/>

        </div>
    </form>



    <form action="/StaffSchedule" class="FormTable" method="post" onsubmit="return confirm('Are you sure you want to make these changes?')">
        <table id="schedule-table"> 
            <tr>
                <th style="width: 9%" >Schedule ID</th>
                <th style="width: 17%">Employee</th>
                <th style="width: 17%">Client</th>
                <th style="width: 12%">Type</th>
                <th style="width: 7%">Slot(s)</th>
                <th style="width: 15%">Description</th>
                <th style="width: 15%" >Date</th>
                <th style="width: 15%">Time</th>
                <th style="width: 8%">Cancelled</th>
            </tr>
            <%                if (schedule.size() == 0) {
                    out.print("<tr><td colspan=\"9\">There is no schedule.</td></tr>");
                } else {
                    for (Operation op : schedule) {
                        String checked = op.isIsCancelled() ? "checked" : "";    // checkbox cheked state
                        Date today = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                        String op_date_time = op.getDate() + "T" + op.getTime(); // "yyyy-MM-ddTHH:mm:ss";
                        Date op_Date = formatter.parse(op_date_time);

                        ///// if op date have passed or op has been cancelled, disable the row
                        // compareTo returns -1/0/1 if less/equal/greater
                        String disabled = (op_Date.compareTo(today) != 1 || checked.equals("checked")) ? "disabled" : "";
                        String passed = (op_Date.compareTo(today) != 1 || checked.equals("checked")) ? "passed" : "";
                        String changed = (changed_ids.contains(op.getId())) ? "changed" : "";
                        String className = passed + " " + changed;
                                
                        out.print("<tr id=\"op-" + op.getId() + "\" class=\"" + className + "\">");
                        out.print("<td>" + op.getId() + "</td>");
                        out.print("<td>" + op.getEmployee().getFullName() + "</td>");
                        out.print("<td>" + op.getClient().getFullName() + "</td>");
                        out.print("<td>" + op.getType() + "</td>");
                        out.print("<td>" + op.getnSlot() + "</td>");
                        out.print("<td>" + op.getDescription() + "</td>");
                        out.print("<td>" + op.getDate() + "</td>");
                        out.print("<td>" + op.getTime().substring(0, 5) + "</td>");
                        out.print("<td><input type=\"checkbox\" " + checked
                                + " name=\"cancel-" + op.getId() + "\" " + disabled + "/></td>");
                        out.print("</tr>");
                    }
                }
            %>
        </table>
        <input type="submit" name="confirm-cancel" id="confirm-cancel-btn" value="Confirm"/>
    </form>





</div>
<jsp:include page="/viewer/Footer.jsp"/>