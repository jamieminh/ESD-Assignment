<%-- 
    Document   : ViewPrescription
    Created on : Jan 21, 2021, 8:36:21 AM
    Author     : LAPTOPVTC.VN
--%>
<%@page import="model.pojo.Prescription"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="model.pojo.Operation"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%
    ArrayList<Prescription> prescriptions = new ArrayList<Prescription>();
    ArrayList<Integer> changed_ids = new ArrayList<Integer>();

    if (request.getAttribute("prescriptions") == null) {
        response.sendRedirect("/ViewPrescription");
    } else {
        prescriptions = (ArrayList<Prescription>) request.getAttribute("prescriptions");
    }
%>

<!DOCTYPE html>
<jsp:include page="/viewer/Header.jsp"/>

<div class="MainContent">
    
    <h2>View your prescription(s)</h2>
    <%
        if (request.getAttribute("changes-made") != null) {
             changed_ids = (ArrayList<Integer>) request.getAttribute("changes-made");

            if ( changed_ids.equals("0"))
                out.print("<div class=\"changes-made\"><em>0 changes made</em></div>");
            else {
                out.print("<div class=\"changes-made\"><em>" + changed_ids.size() + " changes made</em></div>");
            }
        }
    %>
    <body>
       <form action="/ViewPrescription" class="FormTable" method="post" >
        <table id="schedule-table"> 
            <tr>
                    <th style="width: 12%">Prescription ID</th>
                    <th style="width: 17%">Doctor</th>
                    <th style="width: 10%" >Date</th>
                    <th style="width: 8%">Uses</th>
                    <th style="width: 15%" >Description</th>
<!--                <th style="width: 8%">Repeat</th>-->
            </tr>
            <%                if (prescriptions.size() == 0) {
                    out.print("<tr><td colspan=\"8\">Empty.</td></tr>");
                } else {
                    for (Prescription pres : prescriptions) {

                        ///// if op date have passed or op has been cancelled, disable the row
                        // compareTo returns -1/0/1 if less/equal/greater
                        
                        out.print("<td>" + pres.getId() + "</td>");
                        out.print("<td>" + pres.getEmp().getFullName() + "</td>");
                        out.print("<td>" + pres.getDate()+ "</td>");
                        out.print("<td>" + pres.getnUse()+ "</td>");
                        out.print("<td>" + pres.getDescription()+ "</td>");
                        out.print("</tr>");
                    }
                }
            %>
        </table>
    </form>
    </body>
    
    
<jsp:include page="/viewer/Footer.jsp"/>
</html>
