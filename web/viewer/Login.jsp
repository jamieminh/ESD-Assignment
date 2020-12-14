<%-- 
    Document   : Login
    Created on : 02-Dec-2020, 16:56:13
    Author     : zZMerciZz - modified by Jamie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" href="/logo/ico.ico">
        <title>Login</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Maven+Pro:wght@500;600&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Nova+Mono&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
        <link rel="stylesheet" href="/style/form.css">
    </head>
    <body>
        <div class="container Login">
            <div class="Description">
                <h2 class="BrandName">SMARTCARE</h2>
                <h1>Login</h1>
            </div>

            <form action="/Login" method="POST">
                <%
                    String name = (request.getAttribute("nameLogin") == null) ? "" : (String) request.getAttribute("nameLogin");

                %>

                <!-- username -->    
                <div class="formitem">
                    <span><i class="fa fa-user" aria-hidden="true"></i></span>
                    <input required type="text" class="form-control" name="username" placeholder="username" value="<%=name%>"/>
                </div>

                <!-- password -->      
                <div class="formitem">
                    <span><i class="fas fa-key"></i></span>
                    <input required type="password" class="form-control" name="password" placeholder="password"/>
                </div>  
                
                <%
                    if (request.getAttribute("errUser") != null) {
                        out.print("<small class=\"Error\">" + request.getAttribute("errUser") + "</small><br>");
                    }
                %>

                <br>
                <input type="submit" class="btn btn-success w-100" value="Submit"/>
            </form>
            <div class="SignUp-Link">
                <p>Or Sign Up as <a href="/viewer/SignUpClient.jsp">Client</a> or <a href="/viewer/SignUp.jsp">Staff</a> </p>
            </div>
        </div>


    </div>
</body>
</html>
