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

@WebServlet("/Front-End-Pages/AddJobRecord")
public class JobInsertServlet extends HttpServlet {

    private Connection getConnection() throws Exception {
        return DBUtil.getConnection(getServletContext(), "dataentry.properties");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String jnum = request.getParameter("jnum");
        String jname = request.getParameter("jname");
        String numworkersText = request.getParameter("numworkers");
        String city = request.getParameter("city");

        try {
            int numworkers = Integer.parseInt(numworkersText.trim());

            try (
                    Connection connection = getConnection();
                    PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO jobs VALUES (?, ?, ?, ?)"
                    )
            ) {
                ps.setString(1, jnum.trim());
                ps.setString(2, jname.trim());
                ps.setInt(3, numworkers);
                ps.setString(4, city.trim());

                ps.executeUpdate();

                String message = "New jobs record: (" +
                        HtmlUtil.escapeHtml(jnum) + ", " +
                        HtmlUtil.escapeHtml(jname) + ", " +
                        numworkers + ", " +
                        HtmlUtil.escapeHtml(city) +
                        ") - successfully entered into database.";

                request.setAttribute("resultHtml", HtmlUtil.successBox(message));
            }

        } catch (Exception exception) {
            request.setAttribute(
                    "resultHtml",
                    HtmlUtil.errorBox("Error inserting job record:\n" + exception.getMessage())
            );
        }

        request.getRequestDispatcher("/Front-End-Pages/dataEntryHome.jsp").forward(request, response);
    }
}