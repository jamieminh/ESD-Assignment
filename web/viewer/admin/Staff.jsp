<%-- 
    Document   : Staff
    Created on : Dec 14, 2020, 7:01:56 PM
    Author     : Jamie
--%>


<%@page import="model.pojo.Employee"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<Employee> staffs = new ArrayList<Employee>();
    ArrayList<String[]> newChanges = new ArrayList<String[]>();
    boolean hasChange = false;
    String postcode = "", staffUname = "";
//    String[] addresses = null;
    String no_address_class = "";
    String[] addresses = new String[]{"Hello there", "This is just a test, This is just a test, This is just a test, This is just a test"};

    if (session.getAttribute("staffs") == null) {
        response.sendRedirect("/Staff");
    } else {
        staffs = (ArrayList<Employee>) session.getAttribute("staffs");
        if (request.getAttribute("newChanges") != null) {
           newChanges = (ArrayList<String[]>) request.getAttribute("newChanges");
           hasChange = true;
        }
    }
    
    if (request.getAttribute("addresses") != null) {
        addresses = (String[]) request.getAttribute("addresses");
        postcode = (String) request.getAttribute("postcode");
        staffUname = (String) request.getAttribute("staff-uname");        
    }
%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<jsp:include page="/viewer/Header.jsp"/>

<div class="MainContent">

    <h3>There are <span class="data-num"><%=staffs.size()%></span> employee(s) in SmartCare.</h3>
    
    <%
        if (hasChange) 
            out.print("<div class=\"changes-made\"><em>" + newChanges.size() + " changes made</em></div>");
        else
            out.print("<div class=\"changes-made\"><em>0 changes made</em></div>");
    %>
    
    
    <div class='instructions'>
        <h4>Instructions</h4>
        <p>Only address, rate and authorization status can be changed.</p>
        <p>To change Address: lookup an address using postcode. <em style="color: red">Only one</em> postcode can be looked up at a time.</p>
        <p>Once addresses have been found, choose one and click 'Confirm' to submit.</p>
        <p>An employee's rate cannot go beyond &#163;100/slot .</p>
    </div>
    <form action="/Staff" method="get" class="FormTable" onsubmit="return confirm('Do you really want to make these changes?');">
        <table id="staff-table">
            <tr>
                <th style="width: 7%" >Staff ID</th>
                <th style="width: 11%">Username</th>
                <th style="width: 15%">Full Name</th>
                <th style="width: 28%">Address</th>
                <th style="width: 10%">Role</th>
                <th style="width: 9%" >Rate (&#163;/slot)</th>
                <th style="width: 10%">New Rate (&#163;/slot)</th>
                <th style="width: 10%">Authorzied</th>
            </tr>
            <%
                if (staffs.size() == 0) {
                    out.print("<tr><td colspan=\"7\">No record available.</td></tr>");
                } else {
                    for (Employee emp : staffs) {
                        String inputClass = "";
                        String checked = emp.isAuthorized() ? "checked" : "";    // checkbox cheked state
                        if (!newChanges.isEmpty() && newChanges.get(0)[0].equals(emp.getUsername())) {
                            inputClass = "changed";
                            newChanges.remove(0);
                        }
                        String postcodeValue = staffUname.equals(emp.getUsername()) ? postcode : "";
                        out.print("<tr class=\"" + inputClass + "\">");
                        out.print("<td>" + emp.getId() + "</td>");
                        out.print("<td>" + emp.getUsername() + "</td>");
                        out.print("<td>" + emp.getFullName() + "</td>");%>
            <td >
                <p><%=emp.getAddress()%></p>
                <div class="staff-postcode-search">
                    <input type="text" name="postcode-<%=emp.getUsername()%>" 
                           id="<%=emp.getUsername()%>" oninput="onInputChange()" 
                           value="<%=postcodeValue%>" placeholder="Enter postcode..."/>
                    <button type="button" onclick="searchPostcode()">Find</button>
                </div>
                    <% if (addresses != null && emp.getUsername().equals(staffUname)) {
                        out.print("<select id=\"staff-address-\"" + emp.getUsername() +" name=\"address-" + emp.getUsername() + "\" class=\"address-select\" required>");
                        out.print("<option value=\"\">Select your address</option>");
                        for (String addr : addresses) {
                            out.print("<option value=\"" + addr + "\">" + addr + "</option>");
                        }

                        out.print("</select>");
                    }%>


            </td>
            <%          out.print("<td>" + emp.getRole() + "</td>");
                        out.print("<td>" + emp.getRate() + "</td>");
                        out.print("<td><input type=\"number\" name=\"price-" + emp.getUsername() + "\" min=\"1\" max=\"100\"  /></td>");
                        out.print("<td><input type=\"checkbox\" " + checked + " name=\"auth-" + emp.getUsername() + "\" /></td>");
                        out.print("</tr>");
                    }
                }
            %>
        </table>
        <div class="staff-buttons">
            <input type="submit" name="staff-submit" value="Confirm"/>
        </div>

    </form>

    <form method="post" action="/PostcodeLookup" style="visibility: hidden">
        <input type="text" id="main-postcode" name="postcode" />
        <input type="text" id="staff-username" name="postocode-from-staff" />
        <input type="submit" name="submit" id="search-postcode"/>
    </form>

    <script type="text/javascript">
        let postcode_search = document.getElementsByClassName("postcode-search");

        // when addresses forwarded by the servlet are found (jsp page is reloaded)
        
        function onInputChange() {
            let value_typed = event.target.value;
            console.log(event.target.id);
            document.getElementById("main-postcode").value = value_typed;
            document.getElementById("staff-username").value = event.target.id;
        }

        function searchPostcode() {
            document.getElementById("search-postcode").click();
        }

    </script>

</div>
<jsp:include page="/viewer/Footer.jsp"/>
