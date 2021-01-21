<%-- 
    Document   : SeeSchedule
    Created on : 2021-1-20, 18:15:43
    Author     : 14736
--%>
<%-- 
    Document   : SeeSchedule
    Created on : 2021-1-20, 13:28:56
    Author     : 14736
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="model.pojo.Operation"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<Operation> schedule = new ArrayList<Operation>();
    ArrayList<Integer> changed_ids = new ArrayList<Integer>();

    if (session.getAttribute("staffs") == null || session.getAttribute("schedule") == null) {
        response.sendRedirect("/SeeSchedule");
    } else {
        schedule = (ArrayList<Operation>) session.getAttribute("schedule");
    }
%>
<!DOCTYPE html>
<jsp:include page="/viewer/Header.jsp"/>

<div class="MainContent">
    <form action="/SeeSchedule" class="FormTable" method="post" onsubmit="return confirm('Are you sure you want to make these changes?')">
        <table id="schedule-table"> 
            <tr>
                <th style="width: 9%" >Surgery ID</th>
                <th style="width: 17%">Employee</th>
                <th style="width: 17%">Client</th>
                <th style="width: 12%">Type</th>
                <th style="width: 7%">Slot(s)</th>
                <th style="width: 15%" >Date</th>
                <th style="width: 15%">Time</th>
            </tr>
            <%  if (schedule.size() == 0) {
                    out.print("<tr><td colspan=\"8\">There is no schedule.</td></tr>");
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
                        out.print("<td>" + op.getDate() + "</td>");
                        out.print("<td>" + op.getTime().substring(0, 5) + "</td>");
                        out.print("<td><input type=\"checkbox\" " + checked
                                + " name=\"cancel-" + op.getId() + "\" " + disabled + "/></td>");
                        out.print("</tr>");
                    }
                }
            %>
        </table>
    </form>


</div>
<jsp:include page="/viewer/Footer.jsp"/>
