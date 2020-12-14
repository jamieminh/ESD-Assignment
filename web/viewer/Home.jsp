<%-- 
    Document   : Home
    Created on : Dec 5, 2020, 12:56:04 PM
    Author     : Jamie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // before the user login, this var will be null, then, it's set by session
    boolean loggedIn = false;
    if (session.getAttribute("isLoggedIn") != null) {
        loggedIn = true;
    }
%>

<jsp:include page="/viewer/Header.jsp"/>
<div class="container-fluid">
    <h1>Home</h1>
    <p style="color: salmon">The main (home) page lets users select the type of user and action ahead</p>
    <p style="color: salmon">A user should be able to access its own dashboard and the home page from any page</p>
    
    <%
        if (loggedIn) { 
            String fullName = (String) session.getAttribute("fullName");   
            out.println("<h3>You are currently logged in as <span style=\"font-weight: bold\">" + fullName +"</span> </h3>");
        }
        else { %>
            <h1>Welcome to SmartCare!</h1>
            <h3>You are not logged in.</h3>
    
    <%
        }
    %>
</div>
<jsp:include page="/viewer/Footer.jsp"/>
