package com.kazuki_turismo.api_kazukiTurismo.util;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/kazuki_turismo";
    private static final String USER = "root";
    private static final String PASSWORD = "Kazuki_Turismo."; //Kazuki_Turismo. 

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}