<%--
 Name: Lewis Marte
 Course: CNT 4714 – Spring 2026 – Project Four
 Assignment title: A Three-Tier Distributed Web-Based Application
 Date: April 27, 2026
--%>

<%
    String resultHtml = (String) request.getAttribute("resultHtml");

    if (resultHtml == null) {
        resultHtml = "";
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Accountant User Application</title>
    <link rel="stylesheet" href="../project4.css">
</head>

<body>

<h1 class="accountant-title">
    Welcome to the Spring 2026 Project 4 Enterprise System
</h1>

<h2 class="accountant-subtitle">
    A Servlet/JSP-based Multi-tiered Enterprise Application Using A Tomcat Container
</h2>

<p class="sql-instruction">
    You are connected to the Project 4 Enterprise System database as an
    <span style="color:#00ff00; font-weight:bold;">accountant-level</span> user.
    <br>
    Please select the operation you would like to perform from the list below.
</p>

<form action="AccountantUserApp" method="post">

    <div class="accountant-panel">

        <div class="accountant-option">
            <input type="radio" name="report" value="maxStatus" checked>
            <span>Get The Maximum Status Value Of All Suppliers</span>
            <br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Returns a maximum value.
        </div>

        <div class="accountant-option">
            <input type="radio" name="report" value="sumWeights">
            <span>Get The Total Weight Of All Parts</span>
            <br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Returns a sum.
        </div>

        <div class="accountant-option">
            <input type="radio" name="report" value="totalShipments">
            <span>Get The Total Number Of Shipments</span>
            <br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Returns the current number of shipments in total.
        </div>

        <div class="accountant-option">
            <input type="radio" name="report" value="jobMostWorkers">
            <span>Get The Name And Number Of Workers Of The Job With The Most Workers</span>
            <br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Returns two values.
        </div>

        <div class="accountant-option">
            <input type="radio" name="report" value="supplierStatusList">
            <span>List The Name And Status Of Every Supplier</span>
            <br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Returns a list of supplier names with their current status.
        </div>

        <div class="accountant-buttons">
            <input class="accountant-execute" type="submit" name="action" value="Execute Command">
            <input class="accountant-clear" type="submit" name="action" value="Clear Results">
        </div>

    </div>

</form>

<hr>

<div class="execution-title">
    Execution Results:
</div>

<%= resultHtml %>

</body>
</html>