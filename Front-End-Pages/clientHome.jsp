<%--
 Name: Lewis Marte
 Course: CNT 4714 – Spring 2026 – Project Four
 Assignment title: A Three-Tier Distributed Web-Based Application
 Date: April 27, 2026
--%>

<%
    String sqlCommand = (String) request.getAttribute("sqlCommand");
    String resultHtml = (String) request.getAttribute("resultHtml");

    if (sqlCommand == null) {
        sqlCommand = "";
    }

    if (resultHtml == null) {
        resultHtml = "";
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Client User Application</title>
    <link rel="stylesheet" href="../project4.css">
</head>

<body>

    <h1 style="color: red; font-size: 32px; font-weight: bold; margin-top: 25px;">
        Welcome to the Spring 2026 Project 4 Enterprise System
    </h1>

    <h2 style="color: cyan; font-size: 24px; font-weight: bold;">
        A Servlet/JSP-based Multi-tiered Enterprise Application Using A Tomcat Container
    </h2>

    <p class="sql-instruction">
        You are connected to the Project 4 Enterprise System database as a
        <span style="color: red; font-weight: bold;">client-level</span> user.
        <br>
        Please enter any SQL query or update command in the box below.
    </p>

    <form class="sql-form" action="ClientUserApp" method="post">

        <textarea class="sql-textarea" name="sqlCommand"><%= sqlCommand %></textarea>

        <div class="sql-button-row">
            <input class="execute-button" type="submit" name="action" value="Execute Command">
            <input class="reset-button" type="reset" value="Reset Form">
            <input class="clear-button" type="submit" name="action" value="Clear Results">
        </div>

    </form>

    <p class="results-label">
        All execution results will appear below this line.
    </p>

    <hr>

    <div class="execution-title">
        Execution Results:
    </div>

    <%= resultHtml %>

</body>
</html>