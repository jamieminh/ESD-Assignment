<%-- 
    Document   : PayBills
    Created on : Jan 21, 2021, 12:45:20 PM
    Author     : Jamie
--%>

<%@page import="java.util.ArrayList"%>
<%
    ArrayList<String[]> unpaidBills = new ArrayList<String[]>();
    String changesMade = "";
    // if all relevant info is NOT available
    if (request.getAttribute("unpaid-bills") == null) {
        response.sendRedirect("/PayBills");
    } else {
        unpaidBills = (ArrayList<String[]>) request.getAttribute("unpaid-bills");
    }


%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="/viewer/Header.jsp"/>
<div class="MainContent">   
    <h1>Your unpaid charges</h1>

    <%        
        if (request.getAttribute("changes-made") != null) {
            changesMade = (String) request.getAttribute("changes-made");

            if (changesMade.equals("0")) {
                out.print("<div class=\"changes-made\"><em>You have not made any payment</em></div>");
            } else {
                out.print("<div class=\"changes-made\" style=\"background-color: #59B259;\"><em>"
                        + "You have successfully paid " + changesMade + " charges</em></div>");
            }
        }
    %>


    <div class='instructions' style="margin-bottom: 30px">
        <h4>Instructions</h4>
        <p>These are all you unpaid charges.</p>
        <p>Click on the pay box at the end of a row and click confirm to pay that charge.</p>
    </div>

    <h3>You current have <span style="color: red"><%=unpaidBills.size()%></span> unpaid charges.</h3>

    <form action="/PayBills" class="FormTable" method="post" onsubmit="return confirm('Are you sure you want to make these changes?')">
        <table id="bills-table"> 
            <tr>
                <th style="width: 10%" >Operation Type</th>
                <th style="width: 15%">Practitioner</th>
                <th style="width: 13%">Consult Rate</th>
                <th style="width: 7%">Slot(s)</th>
                <th style="width: 20%">Description</th>
                <th style="width: 13%" >Date</th>
                <th style="width: 12%">Time</th>
                <th style="width: 10%">Charge</th>
                <th style="width: 10%">Pay</th>
            </tr>
            <%
                if (unpaidBills.size() == 0) {
                    out.print("<tr><td colspan=\"9\">There is no schedule.</td></tr>");
                } else {
                    for (String[] bill : unpaidBills) {
                        String checked = bill[9].equals("true") ? "checked" : "";    // checkbox cheked state
                        String disabled = bill[9].equals("true") ? "disabled" : "";

                        // [bid, stype, emp name, rate, slots, description, date, time, charge]
                        out.print("<tr id=\"unpaid-" + bill[0] + "\" class=\"" + disabled + "\">");
                        out.print("<td style=\"text-transform: capitalize\">" + bill[1] + "</td>");      // stype
                        out.print("<td>" + bill[2] + "</td>");      // emp
                        out.print("<td>" + bill[3] + "</td>");      // rate
                        out.print("<td style=\"text-align: center\">" + bill[4] + "</td>");      // nslot
                        out.print("<td>" + bill[5] + "</td>");      // description
                        out.print("<td>" + bill[6] + "</td>");      // date
                        out.print("<td>" + bill[7].substring(0, 5) + "</td>");  // time
                        out.print("<td style=\"text-align: right\">" + bill[8] + "</td>");      // date
                        out.print("<td><input type=\"checkbox\" name=\"pay-"
                                + bill[0] + "\"" + checked + " " + disabled + "/></td>");
                        out.print("</tr>");
                    }
                }
            %>
        </table>
        <input type="submit" name="pay-confirm" id="confirm-cancel-btn" value="Pay"/>
    </form>


</div>
<jsp:include page="/viewer/Footer.jsp"/>