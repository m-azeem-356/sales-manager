package com.salebundle.repository;

import com.salebundle.DatabaseConnection;
import com.salebundle.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class UserRepository {
    public static boolean addUser(User user) {
        String sql = """
                INSERT INTO Users
                (username, email, password_hash, role)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.connect(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());

            statement.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static User findByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return new User(
                            resultSet.getString("username"),
                            resultSet.getString("email"),
                            resultSet.getString("password_hash"),
                            resultSet.getString("role")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static User findByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE username = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return new User(
                            resultSet.getString("username"),
                            resultSet.getString("email"),
                            resultSet.getString("password_hash"),
                            resultSet.getString("role")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean usernameExists(String username) {
        String sql = "SELECT 1 FROM Users WHERE username = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean changePassword(String username, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);

        String sql = """
                UPDATE Users
                SET password_hash = ?
                WHERE username = ?;
                """;

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, hashedPassword);
            statement.setString(2, username);

            statement.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean emailExists(String email) {
        String sql = "SELECT 1 FROM Users WHERE email = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}

