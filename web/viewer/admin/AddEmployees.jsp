<%-- 
    Document   : AddEmployees
    Created on : Dec 14, 2020, 7:01:56 PM
    Author     : Jamie
--%>


<%
    String[][] unAuthEmps = new String[1][1];
    if (session.getAttribute("isLoggedIn") != null) {   // if a user is logged in
        if (!session.getAttribute("role").equals("admin")) {    // if the user is NOT admin
            response.sendRedirect("/viewer/Login.jsp");
        }

        // if the session dont have the required info to load the page
        if (session.getAttribute("unAuthStaff") == null || 
                    ((session.getAttribute("unAuthStaff") != null) && session.getAttribute("unAuthStaff").equals("false"))) {
            response.sendRedirect("/AddEmployees");
        } else {
            unAuthEmps = (String[][]) session.getAttribute("unAuthStaff");
        }

    } else { // if a user is NOT logged in
        response.sendRedirect("/viewer/Login.jsp");
    }

//    String[][] unAuthEmps = (String[][]) session.getAttribute("unAuthStaff");

%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<jsp:include page="/viewer/Header.jsp"/>

<div class="MainContent">

    <h3>There are <%=unAuthEmps.length%> new employees registered!</h3>

    <form action="/AddEmployees" method="get">
        <table id="unauth-staff">
            <tr>
                <th>Username</th>
                <th>Full Name</th>
                <th>Address</th>
                <th>Role</th>
                <th>Rate</th>
                <th>Authorzied</th>
            </tr>
            <%
                for (String[] emp : unAuthEmps) {
                    out.print("<tr>");
                    for (String info : emp) {
                        out.print("<td>" + info + "</td>");
                    }
                    out.print("<td><input type=\"checkbox\" name=\"auth-" + emp[0] + "\" /></td>");
                    out.print("</tr>");
                }
            %>
        </table>
        <input type="submit" name="auth-submit" value="Confirm"/>
    </form>

    <%
        out.print(request.getAttribute("success"));
        if (request.getAttribute("success") != null) {
            out.print("UWU");%>
        
            <script type="text/javascript">
                    alert("Successful");
                    location='viewer/admin/AddEmployees.jsp';
            </script> 
            alertName();
    <%    }
    %> 
</div>
<jsp:include page="/viewer/Footer.jsp"/>
