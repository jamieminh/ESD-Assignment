<%-- 
    Document   : Staff
    Created on : Dec 14, 2020, 7:01:56 PM
    Author     : Jamie
--%>


<%@page import="model.pojo.Employee"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<Employee> staffs = new ArrayList<Employee>();
    ArrayList<String[]> newChanges = new ArrayList<String[]>();

    if (request.getAttribute("staffs") == null) 
        response.sendRedirect("/Staff");
    else {
        staffs = (ArrayList<Employee>) request.getAttribute("staffs");
        if (request.getAttribute("newChanges") != null) {
            newChanges = (ArrayList<String[]>) request.getAttribute("newChanges");
        }
    }
%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<jsp:include page="/viewer/Header.jsp"/>

<div class="MainContent">

    <h3>There are <span class="data-num"><%=staffs.size()%></span> employee(s) in SmartCare.</h3>
    <h4>There have been new change(s) in <span class="data-num"><%=newChanges.size()%></span> employee(s).</h4>
    
    <form action="/Staff" method="get" class="FormTable" onsubmit="return confirm('Do you really want to make these changes?');">
        <table id="staff-table">
            <tr>
                <th style="width: 7%" >Staff ID</th>
                <th style="width: 11%">Username</th>
                <th style="width: 18%">Full Name</th>
                <th style="width: 25%">Address</th>
                <th style="width: 10%">Role</th>
                <th style="width: 9%" >Rate (&#163;/slot)</th>
                <th style="width: 10%">New Rate (&#163;/slot)</th>
                <th style="width: 10%">Authorzied</th>
            </tr>
            <%
                if (staffs.size() == 0) {
                    out.print("<tr><td colspan=\"7\">No record available.</td></tr>");
                } else {
                    for (Employee emp : staffs) {
                        String inputClass = "";
                        String checked = emp.isAuthorized() ? "checked" : "";    // checkbox cheked state
                        if (!newChanges.isEmpty() && newChanges.get(0)[0].equals(emp.getUsername())) {
                            inputClass = "changed";
                            newChanges.remove(0);
                        }
                        out.print("<tr class=\"" + inputClass + "\">");
                        out.print("<td>" + emp.getId() + "</td>");
                        out.print("<td>" + emp.getUsername() + "</td>");
                        out.print("<td>" + emp.getFullName() + "</td>");
                        out.print("<td>" + emp.getAddress() + "</td>");
                        out.print("<td>" + emp.getRole() + "</td>");
                        out.print("<td>" + emp.getRate() + "</td>");
                        out.print("<td><input type=\"number\" name=\"price-" + emp.getUsername() + "\" min=\"1\" max=\"100\"  /></td>");
                        out.print("<td><input type=\"checkbox\" " + checked + " name=\"auth-" + emp.getUsername() + "\" /></td>");
                        out.print("</tr>");
                    }
                }
            %>
        </table>
        <input type="submit" name="staff-submit" value="Confirm"/>
    </form>

</div>
<jsp:include page="/viewer/Footer.jsp"/>
