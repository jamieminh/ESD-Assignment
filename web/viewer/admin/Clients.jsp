<%-- 
    Document   : Users
    Created on : Dec 25, 2020, 1:21:25 PM
    Author     : Bao Bui
--%>


<%@page import="model.pojo.Client"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<Client> clients = new ArrayList<Client>();
    if (request.getAttribute("clients") == null) {
        response.sendRedirect("/Clients");
    } else {
        clients = (ArrayList<Client>) request.getAttribute("clients");
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="/viewer/Header.jsp"/>
<div class="MainContent">
    <h3>There are <span class="data-num"><%=clients.size()%></span> client(s) in SmartCare.</h3>
    <div class='instructions'>
        <h4>Instructions</h4>
        <p>Admin can delete client(s) from database by checking the checkbox and then click "Confirm" to submit.</p>
    </div>
    <form action="/Clients" method="get" class="FormTable" onsubmit="return confirm('Do you really want to make these changes?');">
        <table id="client-table">
            <tr>
                <th>Staff ID</th>
                <th>Username</th>
                <th>Full Name</th>
                <th>Address</th>
                <th>Type</th>
                <th>Delete</th>
                <!--<th style="width: 9%" >Rate (&#163;/slot)</th>-->
                <!--                <th style="width: 10%">New Rate (&#163;/slot)</th>-->
                <!--                <th style="width: 10%">Authorzied</th>-->
            </tr>
            <%
                if (clients.size() == 0) {
                    out.print("<tr><td colspan=\"7\">No record available.</td></tr>");
                } else {
                    for (Client emp : clients) {
                        out.print("<tr>");
                        out.print("<td>" + emp.getId() + "</td>");
                        out.print("<td>" + emp.getUsername() + "</td>");
                        out.print("<td>" + emp.getFullName() + "</td>");
                        out.print("<td>" + emp.getAddress() + "</td>");
                        out.print("<td>" + emp.getType() + "</td>");
                        out.print("<td><input type=\"checkbox\" name = client" + emp.getId() + " ");
//                        out.print("<td><input type=\"checkbox\" " + checked + " name=\"auth- /></td>");
                        out.print("</tr>");
                    }
                }
            %>
        </table>
        <div class="staff-buttons">
            <input type="submit" name="client-submit" value="Confirm"/>
        </div>
    </form>
</div>
<jsp:include page="/viewer/Footer.jsp"/>

