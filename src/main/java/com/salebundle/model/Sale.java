package com.salebundle.model;

import org.w3c.dom.ls.LSOutput;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Sale {
    private int id;
    private String saleDate;
    private String saleTime;
    private int quantity;
    private String customerName;
    private int unitPrice;
    private int totalBill;
    private int amountPaid;
    private int remaining;
    private int paidStatus;

    public Sale() {
    }

    public Sale(int quantity, String customerName, int unitPrice, int amountPaid) {

        ZoneId pakistanZone = ZoneId.of("Asia/Karachi");

        this.saleDate = LocalDate.now(pakistanZone).toString();
        this.saleTime = LocalTime.now(pakistanZone)
                .format(DateTimeFormatter.ofPattern("HH:mm"));
        this.quantity = quantity;
        this.customerName = customerName;
        this.unitPrice = unitPrice;
        this.totalBill = quantity * unitPrice;
        this.amountPaid = amountPaid;

        this.remaining = totalBill - amountPaid;
        this.paidStatus = (remaining <= 0) ? 1 : 0;
    }

    public Sale(int id, String saleDate, String saleTime, int quantity, String customerName, int unitPrice, int amountPaid) {

        this.id = id;
        this.saleDate = saleDate;
        this.saleTime = saleTime;
        this.quantity = quantity;
        this.customerName = customerName;
        this.unitPrice = unitPrice;
        this.totalBill = quantity * unitPrice;
        this.amountPaid = amountPaid;

        this.remaining = totalBill - amountPaid;
        this.paidStatus = (remaining <= 0) ? 1 : 0;
    }

    public Sale(String saleDate, String saleTime, int quantity, String customerName, int unitPrice, int amountPaid) {

        this.saleDate = saleDate;
        this.saleTime = saleTime;
        this.quantity = quantity;
        this.customerName = customerName;
        this.unitPrice = unitPrice;
        this.totalBill = quantity * unitPrice;
        this.amountPaid = amountPaid;

        this.remaining = totalBill - amountPaid;
        this.paidStatus = (remaining <= 0) ? 1 : 0;
    }

    public int getId() {
        return id;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public String getSaleTime() {
        return saleTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getTotalBill() {
        return totalBill;
    }

    public int getAmountPaid() {
        return amountPaid;
    }

    public int getRemaining() {
        return remaining;
    }

    public int getPaidStatus() {
        return paidStatus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public void setSaleTime(String saleTime) {
        this.saleTime = saleTime;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setTotalBill(int totalBill) {
        this.totalBill = totalBill;
    }

    public void setAmountPaid(int amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public void setPaidStatus(int paidStatus) {
        this.paidStatus = paidStatus;
    }

    @Override
    public String toString() {
        return "{ Date:" + saleDate + " Time:" + saleTime + " Item:Bundle Quantity:" + quantity + " Customer Name:" + customerName + " Total:" + totalBill + " Paid:" + amountPaid + " Remaining:" + remaining + " }";
    }
}
