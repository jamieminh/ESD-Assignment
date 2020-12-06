<%-- 
    Document   : TEMPLATE
    Created on : Dec 6, 2020, 6:04:02 PM
    Author     : Jamie
--%>

<%--
    THIS JSP SHOULD BE USED AS A TEMPLATE TO DEVELOPE OTHER JSP PAGES
    DO NOT CHANGE ANY ACTIVE CODE 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="/viewer/Header.jsp"/>
    <%
        Object role = session.getAttribute("role"); // type-cast a null obj will cause error
        
        // prevent user from accessing other users' pages by directly type the url in the address bar
        if (role == null || (role != null && !role.equals("admin"))) 
            response.sendRedirect("/viewer/Home.jsp");
    %>

    <div class="container-fluid MainContent">
        <%-- THIS PART IS WHERE ALL YOUR IMPLEMENTATION OF THE JSP GOES --%>
    </div> 
<jsp:include page="/viewer/Footer.jsp"/>
