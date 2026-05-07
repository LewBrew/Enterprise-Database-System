package project4;

/*
 Name: Lewis Marte
 Course: CNT 4714 – Spring 2026 – Project Four
 Assignment title: A Three-Tier Distributed Web-Based Application
 Date: April 27, 2026
*/

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

@WebServlet("/Front-End-Pages/AccountantUserApp")
public class AccountantUserServlet extends HttpServlet {

    private Connection getConnection() throws Exception {
        return DBUtil.getConnection(getServletContext(), "accountant.properties");
    }

    private String getProcedureCall(String report) {
        if ("maxStatus".equals(report)) {
            return "{CALL Get_The_Maximum_Status_Of_All_Suppliers()}";
        }

        if ("sumWeights".equals(report)) {
            return "{CALL Get_The_Sum_Of_All_Parts_Weights()}";
        }

        if ("totalShipments".equals(report)) {
            return "{CALL Get_The_Total_Number_Of_Shipments()}";
        }

        if ("jobMostWorkers".equals(report)) {
            return "{CALL Get_The_Name_Of_The_Job_With_The_Most_Workers()}";
        }

        if ("supplierStatusList".equals(report)) {
            return "{CALL List_The_Name_And_Status_Of_All_Suppliers()}";
        }

        return null;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("Clear Results".equals(action)) {
            request.setAttribute("resultHtml", "");
            request.getRequestDispatcher("/Front-End-Pages/accountantHome.jsp").forward(request, response);
            return;
        }

        String report = request.getParameter("report");
        String procedureCall = getProcedureCall(report);

        if (procedureCall == null) {
            request.setAttribute("resultHtml", HtmlUtil.errorBox("No valid accountant report was selected."));
            request.getRequestDispatcher("/Front-End-Pages/accountantHome.jsp").forward(request, response);
            return;
        }

        try (
                Connection connection = getConnection();
                CallableStatement callableStatement = connection.prepareCall(procedureCall)
        ) {
            boolean hasResultSet = callableStatement.execute();

            if (hasResultSet) {
                ResultSet resultSet = callableStatement.getResultSet();
                request.setAttribute("resultHtml", HtmlUtil.resultSetToHtmlTable(resultSet));
            } else {
                request.setAttribute("resultHtml", HtmlUtil.successBox("The stored procedure executed successfully."));
            }

        } catch (Exception exception) {
            request.setAttribute(
                    "resultHtml",
                    HtmlUtil.errorBox("Error executing accountant report:\n" + exception.getMessage())
            );
        }

        request.getRequestDispatcher("/Front-End-Pages/accountantHome.jsp").forward(request, response);
    }
}