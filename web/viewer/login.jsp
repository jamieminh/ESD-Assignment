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
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" type="stylesheet"/>
    </head>
    <body>
        <center><h1><u>Login</u></h1></center>
        <form action="/ESD-Assignment/Login" method="POST">
            <center>
            <div >
                
                    <table>
               
                   <tr style="margin-bottom: 20px">
                    <th> <label for="username">Username:</label></th>
                    <td><input type="text" class="form-control" name="username"/></td>
                    <% if (request.getAttribute("err") != null) 
                        out.print(request.getAttribute("err"));
                    %>
                </tr>
                <tr>
                    <th> <label for="password">Password:</label> </th>
                    <td><input type="password" class="form-control" name="password" maxlength="16"/></td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" class="btn btn-success" value="Submit"/>
                    </td>
                </tr>
            
                
            </table>
               
   </div>
                 </center>
           
        </form>
       

        
    </body>
</html>
