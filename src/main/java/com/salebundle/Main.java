package com.salebundle;

import com.salebundle.model.Sale;
import com.salebundle.model.User;
import com.salebundle.repository.SaleRepository;
import com.salebundle.repository.UserRepository;
import com.salebundle.service.AuthService;
import com.salebundle.service.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Main {
    public static void main(String[] args) throws Exception {
        if (DatabaseConnection.connect() != null) System.out.println("Connected to database");
        DatabaseInitializer.initialize();


    }
}
