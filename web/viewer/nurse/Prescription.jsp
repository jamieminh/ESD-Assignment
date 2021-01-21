<%-- 
    Document   : IssuePrescription
    Created on : 2021-1-20, 18:40:05
    Author     : 14736
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="model.pojo.Client"%>
<%
    ArrayList<Client> patients = new ArrayList<Client>();
    if (request.getAttribute("patient-list") == null) {
        response.sendRedirect("/Prescription");
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
                out.print("<div class=\"Update success\">");
                out.print("<p> Update Succesful</p>");
                out.print("</div>");
            } else {
                out.print("<div class=\"Update failed\">");
                out.print("<p>Update Failed. Invalid Date and/or Time. </p>");
                out.print("</div>");
            }

        }%>
    <div class="book-surgery-form">
        <form method="get" action="/Prescription">
            <div class="form-item">
                <label>Select Patient</label>
                <select name="issue-patient" required>
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
                <lebel>Issue Information</lebel> 
                <input type="input" name="issue-information" required/>

            </div>
            <div class="form-item">
                <lebel>Select date</lebel> 
                <input type="date" name="issue-date" required/>

            </div>
            <div class="form-item">
                <lebel>use time</lebel>
                <input type="time" name="issue-time" required/>

            </div>

            <div class="book-surgery-submit">
                <input type="submit" name="book-surgery" value="Book"/> 
            </div>
        </form>
    </div>


</div>
<jsp:include page="/viewer/Footer.jsp"/>
