package com.salebundle.controller;

import com.salebundle.service.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestEmailController {

    private final EmailService emailService;

    public TestEmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/test-email")
    public String testEmail() {

        emailService.sendEmail(
                "azeemsaleem13577@gmail.com",
                "Sale Manager Test",
                "If you received this, email sending works!"
        );

        return "Email sent!";
    }
}