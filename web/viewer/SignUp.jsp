<%-- 
    Document   : SignUp
    Created on : Dec 6, 2020, 2:01:06 PM
    Author     : Jamie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/viewer/Header.jsp"/>
<div class="container w-75 SignUp">
    <div class="Description">
        <h1>Sign Up</h1>
        <h5>Create a SmartCare account to benefit from our service</h5>
    </div>
    
    <form action="/SignUp" method="POST">
        <%
            String option = (request.getAttribute("roleSignup") == null) ? "" : (String) request.getAttribute("roleSignup");
            String name = (request.getAttribute("nameSignup") == null) ? "" : (String) request.getAttribute("nameSignup");

            if (request.getAttribute("errUser") != null) {
                out.print("<small class=\"Error\">" + request.getAttribute("errUser") + "</small><br>");
            }
        %>
        <label for="username">Username:</label>
        <input type="text" class="form-control" name="username" value="<%=name%>"/><br>

        <% if (request.getAttribute("errName") != null) {
                out.print("<small class=\"Error\">" + request.getAttribute("errName") + "</small><br>");
            }
        %>
        <label for="password">Password:</label>
        <input type="password" class="form-control" name="password" maxlength="16"/><br>
        <% if (request.getAttribute("errPass") != null) {
                out.print("<small class=\"Error\">" + request.getAttribute("errPass") + "</small><br>");
            }
        %>

        <label>Signup as: </label><br>
        <select class="form-control" name="role" class="SelectForm">
            <option value="default" selected>Choose your user type...</option>
            <option value="client" <% out.print(option.equals("client") ? "selected" : ""); %>>Client</option>
            <option value="doctor" <% out.print(option.equals("doctor") ? "selected" : ""); %>>Doctor</option>
            <option value="nurse"  <% out.print(option.equals("nurse") ? "selected" : ""); %>>Nurse</option>
            <option value="admin"  <% out.print(option.equals("admin") ? "selected" : ""); %>>Admin</option>
        </select>
        <% if (request.getAttribute("errRole") != null) {
                out.print("<small class=\"Error\">" + request.getAttribute("errRole") + "</small><br>");
            }
        %>
        <br><br>
        <input type="submit" class="btn btn-success w-100" value="Submit"/>
    </form>
</div>

<jsp:include page="/viewer/Footer.jsp"/>
