<%-- 
    Document   : CancelSurgery
    Created on : Dec 15, 2020, 11:17:56 PM
    Author     : Jamie
--%>


<%
    String[][] surgeries = new String[1][1];
    // if the session dont have the required info to load the page
    if (session.getAttribute("surgeries") == null) 
        response.sendRedirect("/CancelSurgery");
    else 
        surgeries = (String[][]) session.getAttribute("surgeries");

%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<jsp:include page="/viewer/Header.jsp"/>

<div class="MainContent">

    <h3>Let's Cancel Some Surgeries!!</h3>
    <h3>There are <span class="data-num"><%=surgeries.length%></span> upcoming surgeries.</h3>


    <form action="/CancelSurgery" method="post" class="FormTable">
        <table id="cancel-surgery">
            <tr>
                <th>Surgery ID</th>
                <th>Doctor</th>
                <th>Patient</th>
                <th>Date</th>
                <th>Time</th>
                <th>Cancelled</th>
            </tr>
            <%
                if (surgeries.length == 0) {
                    out.print("<tr><td colspan=\"6\">Empty</td></tr>");
                } 
                else {
                    for (int i = 0; i < surgeries.length; i++) {
                        String[] surgery = surgeries[i];
                        out.print("<tr>");
                        for (String info : surgery) {
                            out.print("<td>" + info + "</td>");
                        }
                        out.print("<td><input type=\"checkbox\" name=\"surg-" + surgery[0] + "\" /></td>");
                        out.print("</tr>");
                    }
                }
            %>
        </table>
        <input type="submit" name="surg-submit" value="Confirm"/>
    </form>

</div>
<jsp:include page="/viewer/Footer.jsp"/>

