package ru.kafpin.kafedraais.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {
    private static final String DB_HOST = "127.0.0.1";
    private static final String DB_PORT = "5432";
    private static final String DB_NAME = "kafedra_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "";

    private static Connection dbConnection;

    public static Connection getConnection() {
        if (dbConnection == null) {
            try {
                String connectionString = "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
                dbConnection = DriverManager.getConnection(connectionString, DB_USER, DB_PASSWORD);
            } catch (SQLException e) {
                System.err.println("Ошибка подключения к базе данных!");
                e.printStackTrace();
            }
        }
        return dbConnection;
    }
}