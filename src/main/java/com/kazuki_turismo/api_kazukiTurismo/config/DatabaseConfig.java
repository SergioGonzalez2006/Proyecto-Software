package com.kazuki_turismo.api_kazukiTurismo.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/kazuki_turismo_sch";
    private static final String USER = "root";
    private static final String PASS = "Sg821271.*";
    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }
}