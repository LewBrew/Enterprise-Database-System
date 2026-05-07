package project4;

/*
 Name: Lewis Marte
 Course: CNT 4714 – Spring 2026 – Project Four
 Assignment title: A Three-Tier Distributed Web-Based Application
 Date: April 27, 2026
*/

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/Front-End-Pages/RootUserApp")
public class RootUserServlet extends HttpServlet {

    private Connection getConnection() throws Exception {
        return DBUtil.getConnection(getServletContext(), "root.properties");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String sqlCommand = request.getParameter("sqlCommand");

        if ("Clear Results".equals(action)) {
            request.setAttribute("sqlCommand", sqlCommand == null ? "" : sqlCommand);
            request.setAttribute("resultHtml", "");
            request.getRequestDispatcher("/Front-End-Pages/rootHome.jsp").forward(request, response);
            return;
        }

        if (sqlCommand == null || sqlCommand.trim().isEmpty()) {
            request.setAttribute("sqlCommand", "");
            request.setAttribute("resultHtml", HtmlUtil.errorBox("No SQL command was entered."));
            request.getRequestDispatcher("/Front-End-Pages/rootHome.jsp").forward(request, response);
            return;
        }

        sqlCommand = sqlCommand.trim();

        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement()
        ) {
            String normalized = sqlCommand.toLowerCase();

            if (normalized.startsWith("select")) {
                ResultSet resultSet = statement.executeQuery(sqlCommand);
                String tableHtml = HtmlUtil.resultSetToHtmlTable(resultSet);

                request.setAttribute("resultHtml", tableHtml);
            } else {
                int rowsAffected = statement.executeUpdate(sqlCommand);

                StringBuilder message = new StringBuilder();

                message.append("The statement executed successfully.<br>");
                message.append(rowsAffected).append(" row(s) affected.<br><br>");

                if (BusinessLogicUtil.isShipmentInsertOrUpdate(sqlCommand)) {
                    int supplierRowsUpdated = BusinessLogicUtil.runBruteForceBusinessLogic(connection);

                    message.append("Business Logic Detected! - Updating Supplier Status<br><br>");
                    message.append("Business Logic updated ")
                            .append(supplierRowsUpdated)
                            .append(" supplier status marks.");
                } else {
                    message.append("Business Logic Not Triggered!");
                }

                request.setAttribute("resultHtml", HtmlUtil.successBox(message.toString()));
            }

        } catch (Exception exception) {
            request.setAttribute(
                    "resultHtml",
                    HtmlUtil.errorBox("Error executing the SQL statement:\n" + exception.getMessage())
            );
        }

        request.setAttribute("sqlCommand", sqlCommand);
        request.getRequestDispatcher("/Front-End-Pages/rootHome.jsp").forward(request, response);
    }
}