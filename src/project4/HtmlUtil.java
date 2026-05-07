package project4;

/*
 Name: Lewis Marte
 Course: CNT 4714 – Spring 2026 – Project Four
 Assignment title: A Three-Tier Distributed Web-Based Application
 Date: April 27, 2026
*/

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class HtmlUtil {

    public static String escapeHtml(String input) {
        if (input == null) {
            return "";
        }

        return input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    public static String resultSetToHtmlTable(ResultSet resultSet) throws SQLException {
        StringBuilder html = new StringBuilder();

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        html.append("<table class='result-table'>");

        html.append("<tr>");
        for (int column = 1; column <= columnCount; column++) {
            html.append("<th>")
                    .append(escapeHtml(metaData.getColumnName(column)))
                    .append("</th>");
        }
        html.append("</tr>");

        while (resultSet.next()) {
            html.append("<tr>");

            for (int column = 1; column <= columnCount; column++) {
                Object value = resultSet.getObject(column);

                html.append("<td>")
                        .append(escapeHtml(value == null ? "NULL" : value.toString()))
                        .append("</td>");
            }

            html.append("</tr>");
        }

        html.append("</table>");

        return html.toString();
    }

    public static String successBox(String message) {
        return "<div class='result-box-success'>" + message + "</div>";
    }

    public static String errorBox(String message) {
        return "<div class='result-box-error'>" + escapeHtml(message) + "</div>";
    }
}