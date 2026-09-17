package com.salebundle;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initialize() {
        String sql = """
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
        try (Connection connection = DatabaseConnection.connect();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Database initialized successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
