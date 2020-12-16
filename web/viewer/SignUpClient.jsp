<%-- 
    Document   : SignUpClient
    Created on : Dec 14, 2020, 5:05:32 PM
    Author     : WIN 10
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
        <div class="container SignUp">
            <div class="Description">
                <!--<h2 class="BrandName">SMART<img src="/logo/caduceus.png"/>CARE</h2>-->
                <h2 class="BrandName">SMARTCARE</h2>
                <h2>Client Sign Up</h2>
            </div>

            <form action="/SignUpClient" method="POST">
                <%
                    String name = (request.getAttribute("nameSignup") == null) ? "" : (String) request.getAttribute("nameSignup");
                    String fullName = (request.getAttribute("fullNameSignup") == null) ? "" : (String) request.getAttribute("fullNameSignup");
                    String option = (request.getAttribute("typeSignup") == null) ? "" : (String) request.getAttribute("typeSignup");
                    String address = (request.getAttribute("addressSignup") == null) ? "" : (String) request.getAttribute("addressSignup");

                %>

            <!-- username -->    
                <div class="formitem">
                    <span><i class="fa fa-user" aria-hidden="true"></i></span>
                    <input required type="text" class="form-control" name="username" placeholder="username" value="<%=name%>"/>
                </div>


            <!-- full name -->    
                <div class="formitem">
                    <span><i class="fa fa-signature" aria-hidden="true"></i></span>
                    <input required type="text" class="form-control" name="fullname" placeholder="full name" value="<%=fullName%>"/>
                </div>

            <!-- type -->       
                <div class="formitem">
                    <span><i class="fas fa-user-tag"></i></span>
                    <select class="form-control" name="type" class="SelectForm" required>
                        <option value=""  selected>Signup as...</option>
                        <option value="private" <% out.print(option.equals("private") ? "selected" : ""); %>>Private</option>
                        <option value="nhs" <% out.print(option.equals("nhs") ? "selected" : ""); %>>NHS</option>
                    </select>
                </div>

            <!-- address -->       
                <div class="formitem">
                    <span><i class="fas fa-map"></i></span>
                    <input required type="text" class="form-control" name="address" placeholder="address" value="<%=address%>"/>
                </div>

                
            <!-- password -->      
                <div class="formitem">
                    <span><i class="fas fa-key"></i></span>
                    <input required type="password" class="form-control" name="password" placeholder="password"/>
                </div>                

                
            <!-- repeat password -->       
                <div class="formitem">
                    <span><i class="fas fa-key"></i></span>
                    <input required type="password" class="form-control" name="repeat-password" placeholder="repeat password"/>
                </div>    

                <%            
                    if (request.getAttribute("errRepeatPw") != null) {
                        out.print("<small class=\"Error\">" + request.getAttribute("errRepeatPw") + "</small><br>");
                    } 
                    if (request.getAttribute("userExist") != null)
                        out.print("<small class=\"Error\">" + request.getAttribute("userExist") + "</small><br>");
                %>            

                
                <br>
                <input type="submit" class="btn btn-success w-100" value="Sign Up"/>
            </form>

            <div class="SignUp-Link">
                <p>Already have an account? <a href="/viewer/Login.jsp"> Login </a></p>
            </div>
        </div>
    </body>
</html>