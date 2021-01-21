<%-- 
    Document   : RequestPrescription
    Created on : Jan 21, 2021, 4:42:00 AM
    Author     : LAPTOPVTC.VN
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="model.pojo.Employee"%>
<%@page import="java.time.LocalDate"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%ArrayList<Employee> staffs = new ArrayList<Employee>();%>
<%
    if (session.getAttribute("staffs") == null)
        response.sendRedirect("/RequestPrescription");
    else {
        staffs = (ArrayList<Employee>) session.getAttribute("staffs");
    }
%>


<!DOCTYPE html>
<jsp:include page="/viewer/Header.jsp"/>

<div class="MainContent">

    <h3>Tell us your request</h3>
    
    <form action="/RequestPrescription" method="post" class="FormTable">
        <table id="book-appointment">
            <tr>
                <th>Staff require <br>
                    
                    <select name="staff-required" required>
                <option value=null>Select a staff.......</option>
                <%
                    for (Employee emp : staffs) {
                        out.print("<option value=\"" + emp.getId()+ "\">" + emp.getFullName() + "</option>");
                    }
                %>
            </select>
                           
                <th>Request Prescription 
                    <textarea required class="form-control" name="prescription"></textarea>
                </th>
            </tr>
          
        </table>
        <input type="submit" name="request-submit" value="Request"/>
    </form>
            
</div>
<jsp:include page="/viewer/Footer.jsp"/>

