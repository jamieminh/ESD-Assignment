<%-- 
    Document   : ProduceDocuments
    Created on : Dec 16, 2020, 10:21:13 PM
    Author     : Admin
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

    <h3>Invoices</h3>
    <form action="/Documents" method="post">
        <div style="display: flex">
            <div>Date from:</div>
            <!--            <select name="month" >
                            <option value="0">See all</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                            <option value="10">10</option>
                            <option value="11">11</option>
                            <option value="12">12</option>
                        </select>-->
            <input type="date" name="datefrom"/>
            <input type="date" name="dateto"/>

            <input type="submit" name="billing-month" value="Ok"/>
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
            <!--<th style="width: 9%" >Rate (&#163;/slot)</th>-->
            <!--                <th style="width: 10%">New Rate (&#163;/slot)</th>-->
            <!--                <th style="width: 10%">Authorzied</th>-->
            </tr>
            <%
                if (billings.size() == 0) {
                    out.print("<tr><td colspan=\"7\">No record available.</td></tr>");
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
