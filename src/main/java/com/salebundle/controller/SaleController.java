package com.salebundle.controller;

import com.salebundle.model.Sale;
import com.salebundle.repository.SaleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class SaleController {
    private final SaleRepository saleRepository;

    public SaleController(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @GetMapping("/sales")
    public List<Sale> getSaleList() {
        return saleRepository.findAll();
    }

    @GetMapping("/sales/unpaid")
    public List<Sale> getUnpaidSales() {
        return saleRepository.findUnpaid();
    }

    @GetMapping("/sales/paid")
    public List<Sale> getPaidSales() {
        return saleRepository.findPaid();
    }

    @GetMapping("/sales/customer/{name}")
    public List<Sale> getSalesByCustomer(@PathVariable String name) {
        return saleRepository.findByCustomerName(name);
    }

    @GetMapping("/sales/customer/date/{date}")
    public List<Sale> getSalesByDate(@PathVariable String date) {
        return saleRepository.findByDate(date);
    }


    @PostMapping("/sales")
    public ResponseEntity<String> saveSale(
            @RequestParam int quantity,
            @RequestParam String customerName,
            @RequestParam int unitPrice,
            @RequestParam int amountPaid) {

        Sale sale = new Sale(
                quantity,
                customerName,
                unitPrice,
                amountPaid
        );

        saleRepository.saveSale(sale);

        return ResponseEntity
                .status(201)
                .body("Sale saved successfully.");
    }

    @DeleteMapping("/sales/{id}")
    public ResponseEntity<String> deleteSale(@PathVariable int id) {

        saleRepository.deleteSale(id);

        return ResponseEntity.ok("Sale deleted successfully.");
    }

    @DeleteMapping("/sales")
    public ResponseEntity<String> deleteSales(@RequestBody List<Integer> ids) {
        saleRepository.multipleDelete(ids);
        return ResponseEntity.ok("Received");
    }

    @PutMapping("/sales/{id}/payment")
    public ResponseEntity<String> updatePayment(
            @PathVariable int id,
            @RequestParam int amount,
            @RequestParam int remaining,
            @RequestParam int prevPaid) {

        boolean success = saleRepository.updatePayment(
                id, amount, remaining, prevPaid
        );

        if (!success) {
            return ResponseEntity
                    .badRequest()
                    .body("Payment cannot be greater than remaining amount.");
        }

        return ResponseEntity.ok("Payment updated successfully.");
    }
}
