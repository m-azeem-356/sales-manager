package com.salebundle;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initialize() {

        String salesSql = """
                CREATE TABLE IF NOT EXISTS Sales (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    sale_date TEXT NOT NULL,
                    sale_time TEXT NOT NULL,
                    quantity INTEGER NOT NULL,
                    customer_name TEXT NOT NULL,
                    unit_price INTEGER NOT NULL,
                    total_bill INTEGER NOT NULL,
                    amount_paid INTEGER NOT NULL,
                    remaining INTEGER NOT NULL,
                    paid_status INTEGER NOT NULL
                )
                """;

        String userSql = """
                CREATE TABLE IF NOT EXISTS Users (
                    username TEXT PRIMARY KEY,
                    email TEXT NOT NULL UNIQUE,
                    password_hash TEXT NOT NULL,
                    role TEXT NOT NULL
                )
                """;

        try (Connection connection = DatabaseConnection.connect();
             Statement statement = connection.createStatement()) {

            statement.execute(salesSql);
            statement.execute(userSql);

            System.out.println("Database initialized successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}