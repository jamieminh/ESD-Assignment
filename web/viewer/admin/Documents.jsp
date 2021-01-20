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
   
    <h3>Invoices</h3>
    <form action="/ProduceDocuments" method="post">
        <div style="display: flex">
            <div>Choose month to for displaying invoices:</div>
            <select name="month" >
                <option value="0">See all</option>
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
            <input type="submit" name="billing-month" value="Ok"/>
        </div>

    </form>
    <form>
        <div id="patientList">

            <div><b><center>Employee</center></b></div>
            <div><b><center>Patient</center></b></div>
            <div><b><center>Schedule Type</center></b></div>
            <div><b><center>Consultation Rate</center></b></div>
            <div><b><center>Slots</center></b></div>
            <div><b><center>Date</center></b></div>
            <div><b><center>Time</center></b></div>
            <div><b><center>Charge</center></b></div>

        </div>


        
    </form>
</div>
<jsp:include page="/viewer/Footer.jsp"/>