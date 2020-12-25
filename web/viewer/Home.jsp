<%-- 
    Document   : Home
    Created on : Dec 5, 2020, 12:56:04 PM
    Author     : Jamie
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/viewer/Header.jsp"/>
<div class="MainContent">
    <%
        String[] tasks = (String[]) session.getAttribute("pages");
        String userUrl = (String) session.getAttribute("folderUrl");
        String fullName = (String) session.getAttribute("fullName");
    %>
    <h3>You are currently logged in as <span style="font-weight: bold"><%=fullName%></span> </h3>
    <h4>What do you want to do?</h4>
    <ul>
        <%
            for (String task : tasks) {
                String url = userUrl + task.trim().replace(" ", "") + ".jsp";
                out.print("<li><a href=\"" + url + "\">" + task + "</a></li>");
            }
        %>
    </ul>

</div>
<jsp:include page="/viewer/Footer.jsp"/>
