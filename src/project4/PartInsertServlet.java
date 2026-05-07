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

@WebServlet("/Front-End-Pages/AddPartRecord")
public class PartInsertServlet extends HttpServlet {

    private Connection getConnection() throws Exception {
        return DBUtil.getConnection(getServletContext(), "dataentry.properties");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pnum = request.getParameter("pnum");
        String pname = request.getParameter("pname");
        String color = request.getParameter("color");
        String weightText = request.getParameter("weight");
        String city = request.getParameter("city");

        try {
            int weight = Integer.parseInt(weightText.trim());

            try (
                    Connection connection = getConnection();
                    PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO parts VALUES (?, ?, ?, ?, ?)"
                    )
            ) {
                ps.setString(1, pnum.trim());
                ps.setString(2, pname.trim());
                ps.setString(3, color.trim());
                ps.setInt(4, weight);
                ps.setString(5, city.trim());

                ps.executeUpdate();

                String message = "New parts record: (" +
                        HtmlUtil.escapeHtml(pnum) + ", " +
                        HtmlUtil.escapeHtml(pname) + ", " +
                        HtmlUtil.escapeHtml(color) + ", " +
                        weight + ", " +
                        HtmlUtil.escapeHtml(city) +
                        ") - successfully entered into database.";

                request.setAttribute("resultHtml", HtmlUtil.successBox(message));
            }

        } catch (Exception exception) {
            request.setAttribute(
                    "resultHtml",
                    HtmlUtil.errorBox("Error inserting part record:\n" + exception.getMessage())
            );
        }

        request.getRequestDispatcher("/Front-End-Pages/dataEntryHome.jsp").forward(request, response);
    }
}