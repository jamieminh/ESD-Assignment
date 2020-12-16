<%-- 
    Document   : checkUser
    Created on : Dec 14, 2020, 8:23:31 PM
    Author     : WIN 10
--%>

<%--<%@page contentType="text/html" pageEncoding="UTF-8"%>--%>
<%
    // before the user login, this var will be null, then, it's set by session
    boolean loggedIn = false;
    if (session.getAttribute("isLoggedIn") != null) {
        loggedIn = true;
        if (!session.getAttribute("role").equals("admin")) {
            response.sendRedirect("/viewer/Login.jsp");
        }
    }
    else{
        response.sendRedirect("/viewer/Login.jsp");
    }
%>
