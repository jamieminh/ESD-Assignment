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

    if (session.getAttribute("staffs") == null || session.getAttribute("prescription") == null) {
        response.sendRedirect("/PrescriptionSchedule");
    } else {
        schedule = (ArrayList<Operation>) session.getAttribute("prescription");
    }
%>
<!DOCTYPE html>
<jsp:include page="/viewer/Header.jsp"/>

<div class="MainContent">
    <form action="/PrescriptionSchedule" class="FormTable" method="post" onsubmit="return confirm('Are you sure you want to make these changes?')">
        <table id="schedule-table"> 
            <tr>
                <th style="width: 9%" >ID</th>
                <th style="width: 17%">Employee</th>
                <th style="width: 17%">Client</th>
                <th style="width: 12%">Prescription Date</th>
                <th style="width: 7%">Prescription Use</th>
                <th style="width: 15%" >Prescription Description</th>
            </tr>
            <%  if (schedule.size() == 0) {
                    out.print("<tr><td colspan=\"8\">There is no schedule.</td></tr>");
                } else {
                    for (Operation pr : schedule) {

                                
                        out.print("<td>" + pr.getId() + "</td>");
                        out.print("<td>" + pr.getEmployee().getFullName() + "</td>");
                        out.print("<td>" + pr.getClient().getFullName() + "</td>");
                        out.print("<td>" + pr.getDate() + "</td>");
                        out.print("<td>" + pr.getUseTime() + "</td>");
                        out.print("<td>" + pr.getPreDescription() + "</td>");
                        out.print("</tr>");
                    }
                }
            %>
        </table>
        <!--<input name="prescription"/>-->
    </form>


</div>
<jsp:include page="/viewer/Footer.jsp"/>

