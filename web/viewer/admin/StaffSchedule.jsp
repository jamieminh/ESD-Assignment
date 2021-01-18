<%-- 
    Document   : StaffSchedule
    Created on : Jan 18, 2021, 7:11:19 PM
    Author     : Jamie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.pojo.Employee"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<Employee> staffs = new ArrayList<Employee>();

    if (request.getAttribute("staffs") == null) {
        response.sendRedirect("/StaffSchedule");
    } else {
        staffs = (ArrayList<Employee>) request.getAttribute("staffs");

    }
%>
<!DOCTYPE html>
<jsp:include page="/viewer/Header.jsp"/>

<div class="MainContent">

    <h3>Check Staff Schedule</h3>

    <form action="/StaffSchedule" method="post">

        <div class="choose-staff">
            <label>Choose Staff</label>
            <select name="staff-name" required>
                <option value=""  selected>Choose Employee...</option>
                <%
                    for (Employee emp : staffs) {
                        out.print("<option value=\"" + emp.getUsername() + "\">" + emp.getFullName() + "</option>");
                    }

                %>
            </select>
            <table id="schedule-table"> 
                <tr>
                    <th style="width: 7%" >Surgery ID</th>
                    <th style="width: 11%">Client</th>
                    <th style="width: 18%">Employee</th>
                    <th style="width: 25%">Type</th>
                    <th style="width: 10%">Slot(s)</th>
                    <th style="width: 9%" >Date</th>
                    <th style="width: 10%">Time</th>
                    <th style="width: 10%">Cancelled</th>
                </tr>
            </table>
        </div>

        <input type="submit" name="submit" value="See Schedule"/>

    </form>

</div>
<jsp:include page="/viewer/Footer.jsp"/>