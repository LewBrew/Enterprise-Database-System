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
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/authenticate")
public class AuthenticationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (
                Connection connection = DBUtil.getConnection(getServletContext(), "systemapp.properties");
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT login_username FROM usercredentials WHERE login_username = ? AND login_password = ?"
                )
        ) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                if ("root".equals(username)) {
                    response.sendRedirect("Front-End-Pages/rootHome.jsp");
                } else if ("client".equals(username)) {
                    response.sendRedirect("Front-End-Pages/clientHome.jsp");
                } else if ("dataentry".equals(username)) {
                    response.sendRedirect("Front-End-Pages/dataEntryHome.jsp");
                } else if ("theaccountant".equals(username)) {
                    response.sendRedirect("Front-End-Pages/accountantHome.jsp");
                } else {
                    response.sendRedirect("Front-End-Pages/errorpage.html");
                }
            } else {
                response.sendRedirect("Front-End-Pages/errorpage.html");
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            response.sendRedirect("Front-End-Pages/errorpage.html");
        }
    }
}