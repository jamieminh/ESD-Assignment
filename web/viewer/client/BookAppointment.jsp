<%-- 
    Document   : BookAppointment
    Created on : 17-Jan-2021, 13:24:56
    Author     : ah2dam
--%>



<%@page import="java.util.ArrayList"%>
<%@page import="model.pojo.Employee"%>
<%@page import="java.time.LocalDate"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%ArrayList<Employee> staffs = new ArrayList<Employee>();%>
<%
    if (session.getAttribute("staffs") == null)
        response.sendRedirect("/BookAppointment");
    else {
        staffs = (ArrayList<Employee>) session.getAttribute("staffs");
    }
%>


<!DOCTYPE html>
<jsp:include page="/viewer/Header.jsp"/>

<div class="MainContent">

    <h3>Let's Make A Booking!!</h3>
    
    <form action="/BookAppointment" method="post" class="FormTable">
        <table id="book-appointment">
            <tr>
                <th>Booking type <br>
                    <select name="booking-type" required>
                        <option value="null">Select a booking type ........</option>
                        <option value="appointment">Appointment</option>
                        <option value="surgery">Surgery</option>
                </th>
                <th>Staff require <br>
<!--                    <select name="staff-require" >
                        <option value="null">Select a staff........</option>
                        <option value="Au Dam">Au Dam</option>
                        <option value="Biao Shen">Biao Shen</option>
                    </select>-->
                    
                    <select name="staff-required" required>
                <option value=null>Select a staff.......</option>
                <%
                    for (Employee emp : staffs) {
                        out.print("<option value=\"" + emp.getFullName()+ "\">" + emp.getFullName() + "</option>");
                    }
                %>
            </select>
                    
                    
                </th>
                <th>Date
                    <input required type="date" class="form-control" name="booking-date" placeholder="yyyy-MM-dd" required title="yyyy-MM-dd"/>
                </th>
                <th>Time <br>
                    <input required type="time" class="form-control" name="booking-time" placeholder="HH:mm:ss" required title="HH:mm:ss"/>
                </th>
                <th>Period of consult
                    <input required type="number" class="form-control" name="consult-slot"/>
                </th>
            </tr>
          
        </table>
        <input type="submit" name="booking-submit" value="Book"/>
    </form>

</div>
<jsp:include page="/viewer/Footer.jsp"/>

