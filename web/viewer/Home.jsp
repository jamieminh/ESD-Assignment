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
<div class="MainContent">
    <% 
        if (loggedIn) { 
            String[] tasks = (String[]) session.getAttribute("pages"); 
            String userUrl = (String) session.getAttribute("folderUrl");
    %>
    <h3>You are currently logged in as <span style="font-weight: bold">Admin</span> </h3>
    <h4>What do you want to do?</h4>
    <ul>
        <% 
            for (String task : tasks) {
                String url = userUrl + task.trim().replace(" ", "") + ".jsp";
//                out.print("<li><a href=\"/" + task.trim().replace(" ", "") +"\">" + task + "</a></li>");
                out.print("<li><a href=\"" + url +"\">" + task + "</a></li>");
            }
        %>
    </ul>

    <% } else { %>
    <h1>Welcome to SmartCare!</h1>
    <h3>You are not logged in.</h3>

    <% } %>
</div>
<jsp:include page="/viewer/Footer.jsp"/>
