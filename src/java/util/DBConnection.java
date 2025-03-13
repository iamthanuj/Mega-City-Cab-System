package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/mega_city_cab_test?autoReconnect=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    // Private constructor to prevent instantiation
    private DBConnection() {}

    // Singleton instance method
    public static DBConnection getInstance() {
        return SingletonHelper.INSTANCE;
    }

    // Static nested class for Bill Pugh Singleton
    private static class SingletonHelper {
        private static final DBConnection INSTANCE = new DBConnection();
    }

    // Always return a fresh connection
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver only once
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL driver", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
