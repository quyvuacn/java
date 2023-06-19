package org.aptech.t2109e.jspservlet.config.db;
import org.aptech.t2109e.jspservlet.config.properties.DatabaseProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static DbConnection instance = null;
    private Connection conn = null;

    public DbConnection() {}

    private void init() throws SQLException {
        DatabaseProperties dbProps = new DatabaseProperties();
        try {
            Class.forName(dbProps.getDriver());
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }
        final String DB_URL = dbProps.getUrl();
        final String USER = dbProps.getUsername();
        final String PASS = dbProps.getPassword();
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
        if (conn != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }

    public  Connection getConnection() {
        return conn;
    }

    public static DbConnection getInstance() throws SQLException {
        if (instance != null && !instance.getConnection().isClosed()) {
            return instance;
        } else {
            instance = new DbConnection();
            instance.init();
            return instance;
        }
    }
}
