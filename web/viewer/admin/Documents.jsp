<%-- 
    Document   : Documents
    Created on : Dec 16, 2020, 10:21:13 PM
    Author     : Bao Bui
--%>


<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.pojo.Billing"%>
<%@page import="java.util.ArrayList"%>



<%
    ArrayList<String[]> billings = new ArrayList<String[]>();
    if (request.getAttribute("billings") == null) {
        response.sendRedirect("/Documents");
    } else {
        billings = (ArrayList<String[]>) request.getAttribute("billings");
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<jsp:include page="/viewer/Header.jsp"/>

<div class="MainContent">

    <h1>Turnover</h1>
    <%
        if (request.getAttribute("date-select-error") != null) {
            out.print("<h3 style=\"color: red\">Chosen dates not valid. Try again.</h3>");
        }
        if (request.getAttribute("date-from") != null) {
            out.print("<h3 style=\"color: #59B259\">Invoice within " + request.getAttribute("date-from")
                    + " to " + request.getAttribute("date-to") + "</h3>");
        }
    %>
    <div class='instructions' style="margin: 30px 0px">
        <h4>Instructions</h4>
        <p>Chose time confines to see charges within that duration.</p>
        <p>The Turnover only shows Paid and Passed operations.</p>
    </div>
    
    <form action="/Documents" method="post">
        <div style="display: flex ; flex-direction:row">
            <div style="display: flex ; flex-direction:column">
                <div><label style="width: 100px">Date from</label><input type="date" name="datefrom"/></div></br>
                <div><label style="width: 100px">Date to</label><input type="date" name="dateto"/></div></br>

            </div>
            <div class="staff-buttons" style="margin-left: 50px; margin-top: 20px"> <input type="submit" name="billing-month" value="Ok"/></div>
        </div>


    </form>
    <form class="FormTable">
        <table id="doc-table">
            <tr id="doc-inf">
                <th>Employee</th>
                <th><center>Patient</center></th>
            <th><center>Schedule Type</center></th>
            <th><center>Consultation Rate</center></th>
            <th><center>Slots</center></th>
            <th>Date</th>
            <th>Time</th>
            <th>Charge</th>
            </tr>
            <%
                if (billings.size() == 0) {
                    out.print("<tr><td colspan=\"8\">No record available.</td></tr>");
                } else {
                    for (String[] bill : billings) {
                        String inputClass = "";

                        out.print("<tr class=\"" + inputClass + "\">");
                        out.print("<td><center>" + bill[0] + "</center></td>");
                        out.print("<td><center>" + bill[1] + "</center></td>");
                        out.print("<td><center>" + bill[2] + "</center></td>");
                        out.print("<td><center>" + bill[3] + "</center></td>");
                        out.print("<td><center>" + bill[4] + "</center></td>");
                        out.print("<td><center>" + bill[5] + "</center></td>");
                        out.print("<td><center>" + bill[6] + "</center></td>");
                        out.print("<td><center>" + bill[7] + "</center></td>");

                        out.print("</tr>");
                    }
                }
            %>
        </table>

    </form>
</div>
<jsp:include page="/viewer/Footer.jsp"/>
