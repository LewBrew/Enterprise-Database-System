package project4;

/*
 Name: Lewis Marte
 Course: CNT 4714 – Spring 2026 – Project Four
 Assignment title: A Three-Tier Distributed Web-Based Application
 Date: April 27, 2026
*/

import jakarta.servlet.ServletContext;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtil {

    public static Connection getConnection(ServletContext context, String propertiesFileName) throws Exception {
        Properties properties = new Properties();

        String fullPath = context.getRealPath("/WEB-INF/conf/" + propertiesFileName);

        try (FileInputStream fileInputStream = new FileInputStream(fullPath)) {
            properties.load(fileInputStream);
        }

        String url = properties.getProperty("MYSQL_DB_URL");
        String username = properties.getProperty("MYSQL_DB_USERNAME");
        String password = properties.getProperty("MYSQL_DB_PASSWORD");

        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(url, username, password);
    }
}