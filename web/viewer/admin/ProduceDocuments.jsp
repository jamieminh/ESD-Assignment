<%-- 
    Document   : ProduceDocuments
    Created on : Dec 16, 2020, 10:21:13 PM
    Author     : Admin
--%>


<%@page import="java.sql.Connection"%>
<%@page import="model.dbHandler.DBBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<jsp:include page="/viewer/Header.jsp"/>

<div class="MainContent">

    <h3>Patients</h3>
    <div id="patientList">

        <div><b><center>ClientID</center></b></div>
        <div><b><center>Full Name</center></b></div>
        <div><b><center>Address</center></b></div>
        <div><b><center>ClientType</center></b></div>
        <div><b><center>Username</center></b></div>

    </div>
    <%
        DBBean db = new DBBean();
        Connection con = (Connection) getServletContext().getAttribute("con");
        db.connect(con);
        String getPatient = "SELECT * FROM APP.CLIENTS FETCH FIRST 100 ROWS ONLY";
        String[][] patientInfo = db.getRecords(getPatient);
//            out.print(patientInfo[0][0]);
        for (String[] pat : patientInfo) {
            out.println("<div id='patientList'>");
            for (String patInf : pat) {
                out.println("<div><center>" + patInf + "</center></div>");
            }
//                out.println("</br>");
            out.println("</div>");
        }
    %>
    <h3>Invoices</h3>
    <form action="/ProduceDocuments" method="post">
        <div style="display: flex">
            <div>Choose month to for displaying invoices:</div>
            <select name="month" >
                <option value="0"></option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
                <option value="8">8</option>
                <option value="9">9</option>
                <option value="10">10</option>
                <option value="11">11</option>
                <option value="12">12</option>
            </select>
            <input type="submit" value="Ok"/>
        </div>

    </form>
    <form>
        <div id="billingList">

            <div><b><center>Employee</center></b></div>
            <div><b><center>Patient</center></b></div>
            <div><b><center>Schedule Type</center></b></div>
            <div><b><center>Consultation Rate</center></b></div>
            <div><b><center>Slots</center></b></div>
            <div><b><center>Date</center></b></div>
            <div><b><center>Time</center></b></div>
            <div><b><center>Charge</center></b></div>

        </div>


        <%
            String month = request.getParameter("month");
            if (month == null || month.equals("0")) {
                String getSche = "SELECT EID,CID,STYPE,NSLOT,SDATE,STIME,SID FROM APP.SCHEDULE WHERE SID !=0";
                String[][] sche = db.getRecords(getSche);
                for (String[] scheInfo : sche) {
                    out.println("<div id='billingList'>");
                    String getEname = "SELECT ENAME,ERATE FROM APP.EMPLOYEES WHERE EID=" + scheInfo[0] + "";
                    String[][] getEmployeeName = db.getRecords(getEname);
                    for (String[] emName : getEmployeeName) {
                        out.println("<div><center>" + emName[0] + "</center></div>");
                    }

                    String getCname = "SELECT CNAME FROM APP.CLIENTS WHERE CID=" + scheInfo[1] + "";
                    String[][] getClientName = db.getRecords(getCname);
                    for (String[] emName : getClientName) {
                        out.println("<div><center>" + emName[0] + "</center></div>");
                    }
                    out.print("<div><center>" + scheInfo[2] + "</center></div>");
                    for (String[] emName : getEmployeeName) {
                        out.println("<div><center>" + emName[1] + "</center></div>");
                    }
                    out.println("<div><center>" + scheInfo[3] + "</center></div>");
                    out.println("<div><center>" + scheInfo[4] + "</center></div>");
                    out.println("<div><center>" + scheInfo[5] + "</center></div>");

                    String getCharge = "SELECT CHARGE FROM APP.BILLING WHERE SID=" + scheInfo[6] + "";
                    String[][] getCha = db.getRecords(getCharge);
                    for (String[] emName : getCha) {
                        out.println("<div><center>" + emName[0] + "</center></div>");
                    }
                    out.println("</div>");
                }
            } else {
                String getSche = "SELECT EID,CID,STYPE,NSLOT,SDATE,STIME,SID FROM APP.SCHEDULE WHERE MONTH(SDATE)=" + month + "";
                String[][] sche = db.getRecords(getSche);
                for (String[] scheInfo : sche) {
                    out.println("<div id='billingList'>");
                    String getEname = "SELECT ENAME,ERATE FROM APP.EMPLOYEES WHERE EID=" + scheInfo[0] + "";
                    String[][] getEmployeeName = db.getRecords(getEname);
                    for (String[] emName : getEmployeeName) {
                        out.println("<div><center>" + emName[0] + "</center></div>");
                    }

                    String getCname = "SELECT CNAME FROM APP.CLIENTS WHERE CID=" + scheInfo[1] + "";
                    String[][] getClientName = db.getRecords(getCname);
                    for (String[] emName : getClientName) {
                        out.println("<div><center>" + emName[0] + "</center></div>");
                    }
                    out.print("<div><center>" + scheInfo[2] + "</center></div>");
                    for (String[] emName : getEmployeeName) {
                        out.println("<div><center>" + emName[1] + "</center></div>");
                    }
                    out.println("<div><center>" + scheInfo[3] + "</center></div>");
                    out.println("<div><center>" + scheInfo[4] + "</center></div>");
                    out.println("<div><center>" + scheInfo[5] + "</center></div>");

                    String getCharge = "SELECT CHARGE FROM APP.BILLING WHERE SID=" + scheInfo[6] + "";
                    String[][] getCha = db.getRecords(getCharge);
                    for (String[] emName : getCha) {
                        out.println("<div><center>" + emName[0] + "</center></div>");
                    }
                    out.println("</div>");
                }
            }
        %>
    </form>
</div>
<jsp:include page="/viewer/Footer.jsp"/>