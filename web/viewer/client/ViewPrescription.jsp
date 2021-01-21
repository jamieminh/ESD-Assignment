<%-- 
    Document   : ViewPrescription
    Created on : Jan 21, 2021, 8:36:21 AM
    Author     : LAPTOPVTC.VN
--%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="model.pojo.Operation"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%
    ArrayList<Operation> prescription = new ArrayList<Operation>();
    ArrayList<Integer> changed_ids = new ArrayList<Integer>();

    if (request.getAttribute("prescription") == null) {
        response.sendRedirect("/ViewPrescription");
    } else {
        prescription = (ArrayList<Operation>) request.getAttribute("prescription");
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
    <div class='instructions'>
        <h4>Instructions</h4>
        <p>Greyed-out row means the operation have approved, there can be no changes made.</p>
    </div>
    <body>
       <form action="/ViewPrescription" class="FormTable" method="post" onsubmit="return confirm('Are you sure you want to make these changes?')">
        <table id="schedule-table"> 
            <tr>
                <th style="width: 12%">Type</th>
                <th style="width: 17%">Doctor</th>
                <th style="width: 15%" >Prescription</th>
                <th style="width: 15%" >Date</th>
                <th style="width: 8%">Approved</th>
            </tr>
            <%                if (prescription.size() == 0) {
                    out.print("<tr><td colspan=\"8\">Empty.</td></tr>");
                } else {
                    for (Operation op : prescription) {
                        String checked = op.isIsAproved() ? "checked" : "";    // checkbox cheked state
                        Date today = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                        String op_date_time = op.getDate(); // "yyyy-MM-dd";
                        Date op_Date = formatter.parse(op_date_time);

                        ///// if op date have passed or op has been cancelled, disable the row
                        // compareTo returns -1/0/1 if less/equal/greater
                        String disabled = (op_Date.compareTo(today) != 1 || checked.equals("checked")) ? "disabled" : "";
                        String passed = (op_Date.compareTo(today) != 1 || checked.equals("checked")) ? "passed" : "";
                        String changed = (changed_ids.contains(op.getId())) ? "changed" : "";
                        String className = passed + " " + changed;
                        
                        out.print("<tr id=\"op-" + op.getId() + "\" class=\"" + className + "\">");
                        out.print("<td style=\"text-transform: capitalize\">" + op.getType() + "</td>");
                        out.print("<td>" + op.getEmployee().getFullName() + "</td>");
                        out.print("<td>" + op.getPrescription() + "</td>");
                        out.print("<td>" + op.getDate() + "</td>");
                        out.print("<td><input type=\"checkbox\" " + checked
                                + " name=\"cancel-" + op.getId() + "\" " + disabled + "/></td>");
                        out.print("</tr>");
                    }
                }
            %>
        </table>
        <input type="submit" name="confirm-aprove" id="confirm-cancel-btn" value="Confirm"/>
    </form>
    </body>
    
    
<jsp:include page="/viewer/Footer.jsp"/>
</html>
