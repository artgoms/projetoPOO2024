package com.projeto.projetoFabinho;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_clientes";
    private static final String USER = "projeto";
    private static final String PASSWORD = "projeto";

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("OK!");
            return conn;
        } catch (SQLException e) {
            System.err.println("DEU PAU: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("SUCESSO!");
        } else {
            System.out.println("FALHOU!");
        }
    }
}
