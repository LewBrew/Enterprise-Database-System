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
    <title>Data Entry Application</title>
    <link rel="stylesheet" href="../project4.css">
</head>

<body>

<h1 class="dataentry-title">
    Welcome to the Spring 2026 Project 4 Enterprise System
</h1>

<h2 class="dataentry-subtitle">
    Data Entry Application
</h2>

<p class="dataentry-instruction">
    You are connected to the Project 4 Enterprise System database as a
    <span style="color:red; font-weight:bold;">data-entry-level</span> user.
    <br>
    Enter the data values in a form below to add a new record to the corresponding database table.
</p>

<div class="dataentry-container">
    <div class="dataentry-grid">

        <div class="dataentry-panel">
            <h3>Suppliers Record Insert</h3>
            <form action="AddSupplierRecord" method="post">
                <table class="dataentry-table">
                    <tr><td>snum</td><td><input type="text" name="snum"></td></tr>
                    <tr><td>sname</td><td><input type="text" name="sname"></td></tr>
                    <tr><td>status</td><td><input type="text" name="status"></td></tr>
                    <tr><td>city</td><td><input type="text" name="city"></td></tr>
                    <tr>
                        <td colspan="2" style="text-align:center;">
                            <input class="dataentry-button" type="submit" value="Enter Supplier Record Into Database">
                            <input class="dataentry-clear" type="reset" value="Clear Data and Results">
                        </td>
                    </tr>
                </table>
            </form>
        </div>

        <div class="dataentry-panel">
            <h3>Parts Record Insert</h3>
            <form action="AddPartRecord" method="post">
                <table class="dataentry-table">
                    <tr><td>pnum</td><td><input type="text" name="pnum"></td></tr>
                    <tr><td>pname</td><td><input type="text" name="pname"></td></tr>
                    <tr><td>color</td><td><input type="text" name="color"></td></tr>
                    <tr><td>weight</td><td><input type="text" name="weight"></td></tr>
                    <tr><td>city</td><td><input type="text" name="city"></td></tr>
                    <tr>
                        <td colspan="2" style="text-align:center;">
                            <input class="dataentry-button" type="submit" value="Enter Part Record Into Database">
                            <input class="dataentry-clear" type="reset" value="Clear Data and Results">
                        </td>
                    </tr>
                </table>
            </form>
        </div>

        <div class="dataentry-panel">
            <h3>Jobs Record Insert</h3>
            <form action="AddJobRecord" method="post">
                <table class="dataentry-table">
                    <tr><td>jnum</td><td><input type="text" name="jnum"></td></tr>
                    <tr><td>jname</td><td><input type="text" name="jname"></td></tr>
                    <tr><td>numworkers</td><td><input type="text" name="numworkers"></td></tr>
                    <tr><td>city</td><td><input type="text" name="city"></td></tr>
                    <tr>
                        <td colspan="2" style="text-align:center;">
                            <input class="dataentry-button" type="submit" value="Enter Job Record Into Database">
                            <input class="dataentry-clear" type="reset" value="Clear Data and Results">
                        </td>
                    </tr>
                </table>
            </form>
        </div>

        <div class="dataentry-panel">
            <h3>Shipments Record Insert</h3>
            <form action="AddShipmentRecord" method="post">
                <table class="dataentry-table">
                    <tr><td>snum</td><td><input type="text" name="snum"></td></tr>
                    <tr><td>pnum</td><td><input type="text" name="pnum"></td></tr>
                    <tr><td>jnum</td><td><input type="text" name="jnum"></td></tr>
                    <tr><td>quantity</td><td><input type="text" name="quantity"></td></tr>
                    <tr>
                        <td colspan="2" style="text-align:center;">
                            <input class="dataentry-button" type="submit" value="Enter Shipment Record Into Database">
                            <input class="dataentry-clear" type="reset" value="Clear Data and Results">
                        </td>
                    </tr>
                </table>
            </form>
        </div>

    </div>
</div>

<hr>

<div class="execution-title">
    Execution Results:
</div>

<div class="dataentry-results">
    <%= resultHtml %>
</div>

</body>
</html>