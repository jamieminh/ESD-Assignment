<%-- 
    Document   : Profile
    Created on : Dec 28, 2020, 6:12:02 PM
    Author     : Jamie
--%>

<%
    String postcode = "", address = "", fullName = "", type = "", id = "", username = "";
    String[] addresses = null;
    String no_address_class = "";
//    String[] addresses = new String[]{"Hello there", "This is just a test, This is just a test, This is just a test, This is just a test"};

    // if all relevant info is NOT available
    if (session.getAttribute("fetched") == null) {
        response.sendRedirect("/Profile");
    } else {
        fullName = (String) session.getAttribute("fullName");
        address = (String) session.getAttribute("address");
        if (address.equals("null")) {
            address = "Please provide your address";
            no_address_class = "no-address";
        }
        type = (String) session.getAttribute("clientType");
        id = (String) session.getAttribute("clientId");
        username = (String) session.getAttribute("username");
    }

    if (request.getAttribute("addresses") != null) {
        addresses = (String[]) request.getAttribute("addresses");
    }

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="/viewer/Header.jsp"/>
<div class="MainContent">   
    <div class="Forms" id="change-info-forms">        
        <div id="change-info">
            <form method="post" action="/Profile">
                <div class="form-item">
                    <label>Your Client ID</label>
                    <input type="text" name="user-id" value="<%=id%>" disabled />
                </div>

                <div class="form-item">    
                    <label>Your Username </label>
                    <input type="text" name="username" value="<%=username%>" disabled/>
                </div>

                <div class="form-item">
                    <label>Your Full Name </label>
                    <input type="text" name="fullName" id="fullName" value="<%=fullName%>" disabled />
                </div>

                <div class="form-item">
                    <label>Your type </label>
                    <select name="user-type" id="user-type" disabled>
                        <option value="private" <% out.print(type.equals("private") ? "selected" : "");%> >Private</option>
                        <option value="nhs"     <% out.print(type.equals("nhs") ? "selected" : "");%>>NHS</option>
                    </select>
                </div>

                <div class="form-item">
                    <label>Your Address </label>
                    <textarea name="current-address" id="current-address" class="<%=no_address_class%>" value="<%=address%>" disabled><%out.print(address.replaceAll(",\\s+", ",\n")); %></textarea>
                </div>

                <% if (addresses != null) {
                        out.print("<div class=\"form-item\" id=\"addresses-form-item\">");
                        out.print("<select id=\"address\" name=\"address\" required>");
                        out.print("<option value=\"\">Select your address</option>");
                        for (String addr : addresses) {
                            out.print("<option value=\"" + addr + "\">" + addr + "</option>");
                        }

                        out.print("</select>");
                        out.print("</div>");
                    }%>

                <div class="control-buttons">
                    <button type="button" id="toggle-edit" onClick="enableEdit()">Edit</button></br>
                    <input type="submit" name="submit" id="save-changes" value="Save Changes" style="visibility: hidden"/>
                </div>
            </form>
        </div>

        <div id="postcode-lookup" style="visibility: hidden">
            <form method="post" action="/PostcodeLookup">
                <div class="form-item">
                    <label> Enter postcode: </label>
                    <input type="text" name="postcode" id="postcode-search" value="<%=postcode%>" class="edit-address"/> 
                </div>
                <div>
                    <input type="submit" value="Search Address"/>
                </div>
            </form>
        </div>        
    </div>


    <script type="text/javascript">
        let fullName = document.getElementById("fullName").value;
        let userType = document.getElementById("user-type").value;

        // when addresses forwarded by the servlet are found (jsp page is reloaded)
        if (document.getElementById("address") !== null) {
            ableChanges()
        }

        // toggle "Edit" and "Cancel" button
        function enableEdit() {
            if (event.target.innerHTML === "Edit") {
                ableChanges()
            } else if (event.target.innerHTML === "Cancel") {
                document.getElementById("toggle-edit").innerHTML = "Edit";
                document.getElementById("change-info-forms").classList.remove("edit-on");

                document.getElementById("fullName").disabled = true;
                document.getElementById("user-type").disabled = true;

                document.getElementById("save-changes").style.visibility = "hidden";
                document.getElementById("postcode-lookup").style.visibility = "hidden";
                document.getElementById("address").style.visibility = "hidden";

                document.getElementById("fullName").value = fullName;
                document.getElementById("user-type").value = userType;
                document.getElementById("postcode-search").value = "";
                document.getElementById("addresses-form-item").remove();
            }
        }

        function ableChanges() {
            document.getElementById("toggle-edit").innerHTML = "Cancel";
            document.getElementById("change-info-forms").classList.add("edit-on");

            document.getElementById("fullName").disabled = false;
            document.getElementById("user-type").disabled = false;

            document.getElementById("postcode-lookup").style.visibility = "visible";
            document.getElementById("save-changes").style.visibility = "visible";
        }

    </script>
</div>
<jsp:include page="/viewer/Footer.jsp"/>

