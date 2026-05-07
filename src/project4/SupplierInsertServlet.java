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

@WebServlet("/Front-End-Pages/AddSupplierRecord")
public class SupplierInsertServlet extends HttpServlet {

    private Connection getConnection() throws Exception {
        return DBUtil.getConnection(getServletContext(), "dataentry.properties");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String snum = request.getParameter("snum");
        String sname = request.getParameter("sname");
        String statusText = request.getParameter("status");
        String city = request.getParameter("city");

        try {
            int status = Integer.parseInt(statusText.trim());

            try (
                    Connection connection = getConnection();
                    PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO suppliers VALUES (?, ?, ?, ?)"
                    )
            ) {
                ps.setString(1, snum.trim());
                ps.setString(2, sname.trim());
                ps.setInt(3, status);
                ps.setString(4, city.trim());

                ps.executeUpdate();

                String message = "New suppliers record: (" +
                        HtmlUtil.escapeHtml(snum) + ", " +
                        HtmlUtil.escapeHtml(sname) + ", " +
                        status + ", " +
                        HtmlUtil.escapeHtml(city) +
                        ") - successfully entered into database.";

                request.setAttribute("resultHtml", HtmlUtil.successBox(message));
            }

        } catch (Exception exception) {
            request.setAttribute(
                    "resultHtml",
                    HtmlUtil.errorBox("Error inserting supplier record:\n" + exception.getMessage())
            );
        }

        request.getRequestDispatcher("/Front-End-Pages/dataEntryHome.jsp").forward(request, response);
    }
}