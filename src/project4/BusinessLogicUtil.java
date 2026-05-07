package project4;

/*
 Name: Lewis Marte
 Course: CNT 4714 – Spring 2026 – Project Four
 Assignment title: A Three-Tier Distributed Web-Based Application
 Date: April 27, 2026
*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BusinessLogicUtil {

    public static boolean isShipmentInsertOrUpdate(String sqlCommand) {
        if (sqlCommand == null) {
            return false;
        }

        String normalized = sqlCommand.trim().toLowerCase().replaceAll("\\s+", " ");

        return normalized.startsWith("insert into shipments")
                || normalized.startsWith("update shipments");
    }

    public static int runBruteForceBusinessLogic(Connection connection) throws SQLException {
        String businessLogicSql =
                "UPDATE suppliers " +
                        "SET status = status + 5 " +
                        "WHERE snum IN ( " +
                        "    SELECT DISTINCT snum " +
                        "    FROM shipments " +
                        "    WHERE quantity >= 100 " +
                        ")";

        try (PreparedStatement preparedStatement = connection.prepareStatement(businessLogicSql)) {
            return preparedStatement.executeUpdate();
        }
    }
}