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
    <h3><%=clients.size()%></h3>

    <form action="/Clients" method="get" class="FormTable" onsubmit="return confirm('Do you really want to make these changes?');">
        <table id="client-table">
            <tr id="client-inf">
                <th>Staff ID</th>
                <th><center>Username</center></th>
                <th><center>Full Name</center></th>
                <th><center>Address</center></th>
                <th><center>Type</center></th>
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
                        String inputClass = "";

                        out.print("<tr class=\"" + inputClass + "\">");
                        out.print("<td><center>" + emp.getId() + "</center></td>");
                        out.print("<td><center>" + emp.getUsername() + "</center></td>");
                        out.print("<td><center>" + emp.getFullName() + "</center></td>");
                        out.print("<td><center>" + emp.getAddress() + "</center></td>");
                        out.print("<td><center>" + emp.getType() + "</center></td>");
                        out.print("<td><input type=\"checkbox\" name = client" + emp.getId() + " ");
//                        out.print("<td><input type=\"checkbox\" " + checked + " name=\"auth- /></td>");
                        out.print("</tr>");
                    }
                }
            %>
        </table>
        <input type="submit" name="client-submit" value="Confirm"/>
    </form>
</div>
<jsp:include page="/viewer/Footer.jsp"/>

