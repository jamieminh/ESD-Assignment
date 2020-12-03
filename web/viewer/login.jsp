<%-- 
    Document   : login
    Created on : 02-Dec-2020, 16:56:13
    Author     : zZMerciZz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello  <%= request.getSession().getAttribute("a")%>!</h1>
       

    </body>
</html>
