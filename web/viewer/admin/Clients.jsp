<%-- 
    Document   : Users
    Created on : Dec 25, 2020, 1:21:25 PM
    Author     : Bao Bui
--%>


<%@page import="model.pojo.Client"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<Client> clients = new ArrayList<Client>();
    String changesMade = "";
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
    <h1>Client Management</h1>
    <%
        if (request.getAttribute("changes-made") != null) {
            changesMade = (String) request.getAttribute("changes-made");

            if ( changesMade.equals("0") )
                out.print("<div class=\"changes-made\"><em>0 changes made</em></div>");
            else {
                out.print("<div class=\"changes-made\"><em>" + changesMade + " clients deleted.</em></div>");
            }
        }
    %>
    
    <div class='instructions'>
        <h4>Instructions</h4>
        <p>Admin can delete client(s) from database by checking the checkbox and then click "Confirm" to submit.</p>
        <p>Deleting a client is final and <em style="color:red">cannot be reverted</em>.</p>
    </div>
    <h3>There are <span class="data-num"><%=clients.size()%></span> client(s) in SmartCare.</h3>
    <form action="/Clients" method="get" class="FormTable" onsubmit="return confirm('Do you really want to make these changes?');">
        <table id="client-table">
            <tr>
                <th>Client ID</th>
                <th>Username</th>
                <th>Full Name</th>
                <th>Address</th>
                <th>Type</th>
                <th>Delete</th>
            </tr>
            <%
                if (clients.size() == 0) {
                    out.print("<tr><td colspan=\"7\">No record available.</td></tr>");
                } else {
                    for (Client pat : clients) {
                        out.print("<tr>");
                        out.print("<td>" + pat.getId() + "</td>");
                        out.print("<td>" + pat.getUsername() + "</td>");
                        out.print("<td>" + pat.getFullName() + "</td>");
                        out.print("<td>" + pat.getAddress() + "</td>");
                        out.print("<td>" + pat.getType() + "</td>");
                        out.print("<td><input type=\"checkbox\" name = client-" + pat.getId() + " ");
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

