package com.salebundle.repository;

import com.salebundle.DatabaseConnection;
import com.salebundle.model.Sale;
import org.springframework.stereotype.Repository;

import java.sql.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Repository
public class SaleRepository {
    public static void saveSale(Sale sale) {

        String sql = """
                INSERT INTO Sales
                (sale_date, sale_time, quantity, customer_name,unit_price,
                 total_bill, amount_paid, remaining, paid_status)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.connect(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, sale.getSaleDate());
            statement.setString(2, sale.getSaleTime());
            statement.setInt(3, sale.getQuantity());
            statement.setString(4, sale.getCustomerName());
            statement.setInt(5, sale.getUnitPrice());
            statement.setInt(6, sale.getTotalBill());
            statement.setInt(7, sale.getAmountPaid());
            statement.setInt(8, sale.getRemaining());
            statement.setInt(9, sale.getPaidStatus());

            statement.executeUpdate();

            System.out.println("Sale saved!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Sale> toList(ResultSet set) throws SQLException {
        List<Sale> list = new ArrayList<>();
        while (set.next()) {
            Sale sale = new Sale(set.getInt("id"),set.getString("sale_date"), set.getString("sale_time"), set.getInt("quantity"), set.getString("customer_name"), set.getInt("unit_price"), set.getInt("amount_paid"));
            list.add(sale);
        }
        return list;
    }

    public static List<Sale> findAll() {
        List<Sale> list;
        String sql = """
                        SELECT * FROM Sales;
                """;

        try (Connection connection = DatabaseConnection.connect(); Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(sql);
            list = toList(set);
        } catch (Exception e) {
            list = new ArrayList<>();
            e.printStackTrace();
        }
        return list;
    }

    public static List<Sale> findByCustomerName(String name) {
        List<Sale> list;
        String sql = """
                SELECT * FROM Sales WHERE customer_name like ?;
                """;
        try (Connection connection = DatabaseConnection.connect(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%"+name+"%");
            ResultSet set = statement.executeQuery();
            list = toList(set);
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<>();
        }
        return list;
    }

    public static List<Sale> findByDate(String date) {
        List<Sale> list;
        String sql = """
                SELECT * FROM Sales WHERE sale_date=?;
                """;
        try (Connection connection = DatabaseConnection.connect(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, date);
            ResultSet set = statement.executeQuery();
            list = toList(set);
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<>();
        }
        return list;
    }

    public static List<Sale> findUnpaid() {
        List<Sale> list;
        String sql = """
                        SELECT * FROM Sales WHERE remaining >0;
                """;

        try (Connection connection = DatabaseConnection.connect(); Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(sql);
            list = toList(set);
        } catch (Exception e) {
            list = new ArrayList<>();
            e.printStackTrace();
        }
        return list;
    }

    public static List<Sale> findPaid() {
        List<Sale> list;
        String sql = """
                        SELECT * FROM Sales WHERE remaining =0;
                """;

        try (Connection connection = DatabaseConnection.connect(); Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(sql);
            list = toList(set);
        } catch (Exception e) {
            list = new ArrayList<>();
            e.printStackTrace();
        }
        return list;
    }

    public static boolean updatePayment(int id, int amount, int remaining, int prevPaid){
        int newRemaining = remaining - amount;
        if (newRemaining < 0) {
            return false;
        }
        String sql = """
                UPDATE Sales 
                SET amount_paid=?,remaining=?,paid_status=?
                WHERE id=?;
                """;
        try (Connection connection = DatabaseConnection.connect(); PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, prevPaid + amount);
            statement.setInt(2, newRemaining);
            int paidStatus = (newRemaining == 0) ? 1 : 0;
            statement.setInt(3, paidStatus);
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Sale findById(int id) throws Exception {
        String sql = """
                SELECT * FROM Sales WHERE id=?;
                """;
        Sale ans = null;
        try (Connection connection = DatabaseConnection.connect(); PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            List<Sale> list = toList(set);
            ans = (list.isEmpty()) ? null : list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ans == null) throw new Exception("NO DATA FOUND MATCHING THE PROVIDED ID");
        return ans;
    }

    public static void multipleDelete(List<Integer> ids) {
        for (int id : ids) {
            deleteSale(id);
        }
    }

    public static void deleteSale(int id) {
        String sql = """
                        DELETE FROM Sales WHERE id=?;
                """;
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
