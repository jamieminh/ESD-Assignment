<%-- 
    Document   : login
    Created on : 02-Dec-2020, 16:56:13
    Author     : zZMerciZz - modified by Jamie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/viewer/Header.jsp"/>
<div class="container w-75 SignUp">
    <div class="Description">
        <h1>Login</h1>
    </div>
    
    <form action="/Login" method="POST">
        <%
            String name = (request.getAttribute("nameLogin") == null) ? "" : (String) request.getAttribute("nameLogin");

            if (request.getAttribute("errUser") != null) {
                out.print("<small class=\"Error\">" + request.getAttribute("errUser") + "</small><br>");
            }
        %>
        <label for="username">Username:</label>
        <input type="text" class="form-control" name="username" value="<%=name%>"/>

        <% if (request.getAttribute("errName") != null) {
                out.print("<small class=\"Error\">" + request.getAttribute("errName") + "</small><br>");
            }
        %>
        
        <label for="password">Password:</label>
        <input type="password" class="form-control" name="password" maxlength="16"/>
        <% if (request.getAttribute("errPass") != null) {
                out.print("<small class=\"Error\">" + request.getAttribute("errPass") + "</small><br>");
            }
        %>

        <br>
        <input type="submit" class="btn btn-success w-100" value="Submit"/>
    </form>
</div>

<jsp:include page="/viewer/Footer.jsp"/>
