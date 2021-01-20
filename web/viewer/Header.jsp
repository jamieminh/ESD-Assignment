<%-- 
    Document   : Header
    Created on : Dec 5, 2020, 12:52:04 PM
    Author     : Jamie
--%>

<%-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    HEADER PURPOSE:
    - This page should be included in every view file (jsp)
    - Since not logged in users cannot access .jsp pages, just the html pages,
        no login check is needed
    - The content of the navigaton bar is dependant on the user role
    - For logged in user, the session should set relevant attributes like: 
        + user full name,
        + user role (client/doctor/nurse/admin), 
        + title of the page, 
        + the jsp folder url ("/viewer/client" or "/viewer/admin/", etc
        + the jsp dashboard page list
        + the jsp dashboard page icon list
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String title = "SmartCare - Dashboard: " + (String) session.getAttribute("fullName");
    String folderUrl = (String) session.getAttribute("folderUrl");
    String fullName = (String) session.getAttribute("fullName");
    String role = (String) session.getAttribute("role");
    String[] pages = (String[]) session.getAttribute("pages");
    String[] pagesIcons = (String[]) session.getAttribute("pagesIcons");
    String userPic = (String) session.getAttribute("userPic");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" href="/assets/logo/ico.ico">
        <title><%=title%></title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
        <link rel="stylesheet" href="/style/table.css">
        <link rel="stylesheet" href="/style/main.css">
        <link rel="stylesheet" href="/style/profile.css">

    </head>
    <body>           
        <div class="wrapper">
            <!-- Sidebar  -->
            <nav id="sidebar">
                <div class="sidebar-header">
                    <a href="/viewer/Home.jsp">
                        <img class="brand-logo" src="/assets/logo/caduceus.png"/><h4 class="BrandName">SMART CARE</h4>
                        <img class="brand-logo sm" src="/assets/logo/caduceus.png"/>
                    </a>
                </div>

                <ul class="list-unstyled components">
                    <li class="<%=request.getRequestURI().equals("/viewer/Home.jsp") ? "active" : ""%>">
                        <a href="/viewer/Home.jsp" >
                            <span class="icon"><i class="fas fa-clinic-medical"></i></span>
                            <span class="page-name">Home</span>
                        </a>
                    </li>
                    <%
                        for (int i = 0; i < pages.length; i++) {
                            String pageName = pages[i];
                            String pageIcon = pagesIcons[i];
                            String urlName = folderUrl + pageName.trim().replace(" ", "") + ".jsp";
                            String active = request.getRequestURI().equals(urlName) ? "active" : "";
                            %>
                            <li class="<%=active%>">
                                <a href="<%=urlName%>">
                                    <span class="icon"><i class="fas fa-<%=pageIcon%>"></i></span>
                                    <span class="page-name"><%=pageName%></span>
                                </a>                 
                            </li>
                    <%   }                          
                        if (role.equals("client")) { %>
                            <li class="<%=request.getRequestURI().equals("/viewer/client/Profile.jsp") ? "active" : ""%>">
                                <a href="/viewer/client/Profile.jsp" >
                                    <span class="icon"><i class="fas fa-user-edit"></i></span>
                                    <span class="page-name">Profile</span>
                                </a>
                            </li>                    
                    <%  } %>
                    
                    

                    <li class="mt-3">
                        <a href="/Logout" >
                            <span class="icon"><i class="fas fa-power-off"></i></span>
                            <span class="page-name">Logout</span>
                        </a>
                    </li>
                </ul>

                <footer class="page-footer">
                    <!--Copyright--> 
                    <div class="footer-copyright text-center py-3">©2021 Copyright:
                        Hue Nguyen - Bao Bui - Phong Phan - Biao Shen - Au Dam
                    </div>
                </footer>
            </nav>
            <div id="content">
                <nav class="navbar navbar-expand-lg navbar-light bg-light">
                    <div class="container-fluid">
                        <button type="button" id="sidebarCollapse" class="btn">
                            <i class="fas fa-bars "></i>
                        </button>      

                        <div class="user-info">
                            <div class="user-picture">
                                <img src="<%=userPic%>" />
                            </div>

                            <div class="user-identification">
                                <div class="user-name"><%=fullName%></div>
                                <div class="user-role"><%=role%></div>
                            </div>
                        </div>
                    </div>                                      
                </nav>       