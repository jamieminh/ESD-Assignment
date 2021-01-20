<%-- 
    Document   : BookSurgery
    Created on : Jan 20, 2021, 6:20:15 PM
    Author     : Jamie
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="model.pojo.Client"%>
<%
    ArrayList<Client> patients = new ArrayList<Client>();
    if (request.getAttribute("patient-list") == null) {
        response.sendRedirect("/BookSurgery");
    } else {
        patients = (ArrayList<Client>) request.getAttribute("patient-list");

    }

%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="/viewer/Header.jsp"/>
<div class="MainContent">   

    <% if (request.getAttribute("result") != null) {
            if (request.getAttribute("result").equals("success")) {
                out.print("<div class=\"book-result success\">");
                out.print("<p> Booking Succesful</p>");
                out.print("</div>");
            } else {
                out.print("<div class=\"book-result failed\">");
                out.print("<p>Booking Failed. Invalid Date and/or Time. </p>");
                out.print("</div>");
            }

        }%>
    <div class="book-surgery-form">
        <form method="get" action="/BookSurgery">
            <div class="form-item">
                <label>Select Patient</label>
                <select name="surgery-patient" required>
                    <option value="">Choose a Patient...</option>
                    <%
                        for (Client pat : patients) {
                            String value = pat.getFullName() + " - ID " + pat.getId();
                            out.print("<option value=\"" + pat.getId() + "\">" + value + "</option>");
                        }
                    %>

                </select>


            </div>
            <div class="form-item">
                <lebel>Select date</lebel> 
                <input type="date" name="surgery-date" required/>

            </div>
            <div class="form-item">
                <lebel>Select date</lebel>
                <input type="time" name="surgery-time" required/>

            </div>

            <div class="book-surgery-submit">
                <input type="submit" name="book-surgery" value="Book"/> 
            </div>
        </form>
    </div>


</div>
<jsp:include page="/viewer/Footer.jsp"/>