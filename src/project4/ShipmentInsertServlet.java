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

@WebServlet("/Front-End-Pages/AddShipmentRecord")
public class ShipmentInsertServlet extends HttpServlet {

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/project4",
                "dataentry",
                "dataentry"
        );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String snum = request.getParameter("snum");
        String pnum = request.getParameter("pnum");
        String jnum = request.getParameter("jnum");
        String quantityText = request.getParameter("quantity");

        try {
            int quantity = Integer.parseInt(quantityText.trim());

            try (
                    Connection connection = getConnection();
                    PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO shipments VALUES (?, ?, ?, ?)"
                    )
            ) {
                ps.setString(1, snum.trim());
                ps.setString(2, pnum.trim());
                ps.setString(3, jnum.trim());
                ps.setInt(4, quantity);

                ps.executeUpdate();

                StringBuilder message = new StringBuilder();

                message.append("New shipments record: (")
                        .append(HtmlUtil.escapeHtml(snum)).append(", ")
                        .append(HtmlUtil.escapeHtml(pnum)).append(", ")
                        .append(HtmlUtil.escapeHtml(jnum)).append(", ")
                        .append(quantity)
                        .append(") - successfully entered into database.<br><br>");

                if (quantity >= 100) {
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
                    HtmlUtil.errorBox("Error inserting shipment record:\n" + exception.getMessage())
            );
        }

        request.getRequestDispatcher("/Front-End-Pages/dataEntryHome.jsp").forward(request, response);
    }
}