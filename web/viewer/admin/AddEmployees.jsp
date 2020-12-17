<%-- 
    Document   : AddEmployees
    Created on : Dec 14, 2020, 7:01:56 PM
    Author     : Jamie
--%>


<%
    String[][] unAuthEmps = new String[1][1];

    if (session.getAttribute("unAuthStaff") == null) 
        response.sendRedirect("/AddEmployees");
    else 
        unAuthEmps = (String[][]) session.getAttribute("unAuthStaff");
    

//    String username = null;
//    String sessionID = null;
//    Cookie[] cookies = request.getCookies();
//    if (cookies != null) {
//        for (Cookie cookie : cookies) {
//            if (cookie.getName().equals("user"))
//                username = cookie.getValue();
//            if (cookie.getName().equals("JSESSIONID"))
//                sessionID = cookie.getValue();
//        }
//    }

%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<jsp:include page="/viewer/Header.jsp"/>

<div class="MainContent">

    <h3>There are <span class="data-num"><%=unAuthEmps.length%></span> new employee(s) registered!</h3>

    <form action="/AddEmployees" method="post" class="FormTable">
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
                if (unAuthEmps.length == 0) {
                    out.print("<tr><td colspan=\"6\">Empty</td></tr>");
                } else {
                    for (String[] emp : unAuthEmps) {
                        out.print("<tr>");
                        for (String info : emp) {
                            out.print("<td>" + info + "</td>");
                        }
                        out.print("<td><input type=\"checkbox\" name=\"auth-" + emp[0] + "\" /></td>");
                        out.print("</tr>");
                    }
                }
            %>
        </table>
        <input type="submit" name="auth-submit" value="Confirm"/>
    </form>

</div>
<jsp:include page="/viewer/Footer.jsp"/>
