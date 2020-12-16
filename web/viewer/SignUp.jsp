<!-- 
    Document   : SignUp
    Created on : Dec 6, 2020, 2:01:06 PM
    Author     : Jamie
-->


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
                <h2 class="BrandName">SMARTCARE</h2>
                <h2>Sign Up</h2>
            </div>

            <form action="/SignUp" method="POST">
                <%
                    String option = (request.getAttribute("roleSignup") == null) ? "" : (String) request.getAttribute("roleSignup");
                    String name = (request.getAttribute("nameSignup") == null) ? "" : (String) request.getAttribute("nameSignup");
                    String fullName = (request.getAttribute("fullNameSignup") == null) ? "" : (String) request.getAttribute("fullNameSignup");
                    String rate = (request.getAttribute("rateSignup") == null) ? "" : (String) request.getAttribute("rateSignup");
                    String address = (request.getAttribute("addressSignup") == null) ? "" : (String) request.getAttribute("addressSignup");

                    if (request.getAttribute("errUser") != null)
                        out.print("<small class=\"Error\">" + request.getAttribute("errUser") + "</small><br>");
                    else
                        out.print("<br>");
                %>

            <!-- username -->    
                <div class="formitem">
                    <span><i class="fa fa-user" aria-hidden="true"></i></span>
                    <input required type="text" class="form-control" name="username" placeholder="username" value="<%=name%>"/>
                </div>
                
                <%
                    if (request.getAttribute("userExist") != null) 
                        out.print("<small class=\"Error\">" + request.getAttribute("userExist") + "</small><br>");

                %>

            <!-- full name -->    
                <div class="formitem">
                    <span><i class="fa fa-signature" aria-hidden="true"></i></span>
                    <input required type="text" class="form-control" name="fullname" placeholder="full name" value="<%=fullName%>"/>
                </div>

            <!-- role -->       
                <div class="formitem">
                    <span><i class="fas fa-user-tag"></i></span>
                    <select class="form-control" name="role" class="SelectForm" required>
                        <option value=""  selected>Signup as...</option>
                        <option value="doctor" <% out.print(option.equals("doctor") ? "selected" : ""); %>>Doctor</option>
                        <option value="nurse"  <% out.print(option.equals("nurse") ? "selected" : ""); %>>Nurse</option>
                    </select><!--
                </div>-->

            <!-- address -->       
                <div class="formitem">
                    <span><i class="fas fa-map"></i></span>
                    <input required type="text" class="form-control" name="address" placeholder="address" value="<%=address%>"/>
                </div>

            
            <!-- rate -->       
                <div class="formitem">
                    <span><i class="fas fa-money-bill-wave"></i></span>
                    <input required type="text" class="form-control" name="rate" placeholder="rate" value="<%=rate%>"/>
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
                    } else {
                        out.print("");
                    }
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