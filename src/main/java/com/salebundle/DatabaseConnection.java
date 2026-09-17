package com.salebundle;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static final String DATABASE_FOLDER =
            System.getProperty("user.home") + "/Downloads/App Data";

    private static final String DATABASE_PATH =
            DATABASE_FOLDER + "/salesData.db";

    private static final String URL =
            "jdbc:sqlite:" + DATABASE_PATH;

    public static Connection connect() {
        try {
            Files.createDirectories(Path.of(DATABASE_FOLDER));

            return DriverManager.getConnection(URL);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}