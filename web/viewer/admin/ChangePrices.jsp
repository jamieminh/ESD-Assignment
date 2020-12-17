<%-- 
    Document   : ChangePrices
    Created on : Dec 16, 2020, 2:14:04 PM
    Author     : Jamie
--%>


<%@page import="java.util.ArrayList"%>
<%
    String[][] servicesPrice = new String[1][1];

    // if the session dont have the required info to load the page
    if (session.getAttribute("servicesPrice") == null) 
        response.sendRedirect("/ChangePrices");
    else 
        servicesPrice = (String[][]) session.getAttribute("servicesPrice");    

    ArrayList<String> changedUsers = new ArrayList<String>();
    if (session.getAttribute("changedUsers") != null) {
        changedUsers = (ArrayList<String>) session.getAttribute("changedUsers");
    }

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<jsp:include page="/viewer/Header.jsp"/>

<div class="MainContent">

    <h3>SmartCare Services' Price </h3>
    <h4>There is <span class="data-num"><%=changedUsers.size()%></span> new change(s).</h4>

    <form action="/ChangePrices" method="post" class="FormTable">
        <table id="services-price">
            <tr>
                <th>Username</th>
                <th>Full Name</th>
                <th>Role</th>
                <th>Rate (&#163;/slot)</th>
                <th>New Rate (&#163;/slot)</th>
            </tr>
            <%
                for (String[] price : servicesPrice) {
                    String username = price[0];
                    String inputClass = "";
                    // add class to highlight changes
                    if (!changedUsers.isEmpty() && changedUsers.get(0).equals(username)) {
                        inputClass = "changed";
                        changedUsers.remove(0);
                    }

                    out.print("<tr>");
                    for (String info : price) 
                        out.print("<td class=\"" + inputClass + "\">" + info + "</td>");
                    out.print("<td class=\"" + inputClass + "\"><input type=\"number\" name=\"serv-" + username + "\" min=\"1\" max=\"100\"  /></td>");
                    out.print("</tr>");
                }

            %>
        </table>
        <input type="submit" name="price-submit" value="Confirm"/>
    </form>




</div>
<jsp:include page="/viewer/Footer.jsp"/>
