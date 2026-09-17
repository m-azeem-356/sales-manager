package com.salebundle.service;

import com.salebundle.model.User;
import com.salebundle.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final EmailService emailService;

    public AuthService(EmailService emailService, UserRepository userRepository) {
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    public boolean checkUsername(String username) {
        return userRepository.usernameExists(username);
    }

    public boolean checkEmail(String email) {
        return userRepository.emailExists(email);
    }


    public boolean changePassword(String username, String password) {
        return userRepository.changePassword(username,password);
    }

    public String getEmail(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return "";
        }
        return user.getEmail();
    }

    private int generateCode() {
        SecureRandom random = new SecureRandom();
        return 100000 + random.nextInt(899999);
    }

    public int sendCode(String email) {
        final String subject = "Sale Management System Verification Code";
        int code = generateCode();
        String text = "Your Verification code: " + code;
        emailService.sendEmail(email, subject, text);
        return code;
    }

    public boolean signUp(String username, String password, String email, String role) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String hashedPassword = encoder.encode(password);

        User user = new User(username, email, hashedPassword, role);

        return userRepository.addUser(user);
    }

    public String logIn(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return "Username not found";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(password, user.getPassword())) {
            return user.getRole();
        } else return "Wrong Password or Username";
    }

}
