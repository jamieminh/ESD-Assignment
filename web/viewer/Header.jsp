<%-- 
    Document   : Header
    Created on : Dec 5, 2020, 12:52:04 PM
    Author     : Jamie
--%>

<%-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    HEADER PURPOSE:
    - This page should be included in every view file (jsp)
    - The content of the navigaton bar is dependant on 2 things:
        + whether a user is logged in
        + if they're logged in, which type of user they are
    - For not logged in user, the navbar should have 2 items: HOME and LOGIN
    - For logged in user, the session should set relevant attributes like: 
        + isLoggedIn = true
        + user full name,
        + user type (client/doctor/nurse/admin), 
        + title of the page, 
        + the jsp folder url ("/viewer/client" or "/viewer/admin/", etc
        + the jsp dashboard page list, see below)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    String title = "SmartCare";  
    String folderUrl = "/viewer/";
    String welcome = "";
    String[] pages = new String[] {"Login", "Sign Up", "Our Service"};
    boolean loggedIn = false;
    if (session.getAttribute("isLoggedIn") != null) {
        loggedIn = true;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" href="/logo/ico.ico">
        <title><%=title%></title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Maven+Pro:wght@500;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
        <link rel="stylesheet" href="/style/style.css">
    </head>
    <body>
        <%
            if (loggedIn) {
                welcome = "Hello " + session.getAttribute("fullName");
                title = (String) session.getAttribute("title");
                folderUrl = (String) session.getAttribute("folderUrl");
                pages = (String[]) session.getAttribute("pages");                
            }
            
            %>
        <nav class="navbar navbar-expand-lg navbar-light bg-light ">
            <a href="/viewer/Home.jsp" class="navbar-brand"><img src="/logo/caduceus.png" id="logo"></a>
            <div class="navbar-brand mr-auto"><span id="BrandName">SmartCare</span></div>

            <div class="navbar-brand ml-auto d-lg-none"><span id="UserName"><%=welcome%></span></div>

            <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#myNavbar">
                <i class="fa fa-bars" ></i>
            </button>

            <div class="collapse navbar-collapse MainNavbar text-right" id="myNavbar">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item <%=request.getRequestURI().equals("/viewer/Home.jsp") ? "active" : ""%>">
                        <a class="nav-link" href="/viewer/Home.jsp">Home</a>
                    </li>
                    <%  
                        for (String pageName : pages) {
                            String urlName = folderUrl + pageName.trim().replace(" ", "") + ".jsp";
                            // set active navigation item
                            String active = request.getRequestURI().equals(urlName) ? "active" : "";
                            out.print(String.format("<li class=\"nav-item %s\"> " +
                                                        "<a class=\"nav-link\" href=\"%s\">%s</a></li>", active, urlName, pageName));
                        }
                        
                        if (loggedIn)
                            out.println("<li class=\"nav-item\">" +
                                            "<form action=\"/Logout\" method=\"post\" >" +
                                                    "<input type=\"submit\" value=\"Logout\" id=\"LogoutButton\" />"+
                                            "</form>"+
                                        "</li>");
                    %>
                </ul>
            </div>
            <div class="navbar-brand ml-auto d-none d-lg-block"><span id="UserName"><%=welcome%></span></div>

        </nav>
        




