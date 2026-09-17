package com.salebundle.controller;

import com.salebundle.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(
            AuthService authService) {

        this.authService = authService;
    }


    @GetMapping("/user/exist/{username}")
    public boolean exists(@PathVariable String username) {
        return authService.checkUsername(username);
    }

    @GetMapping("/user/email/{email}")
    public boolean emailExists(@PathVariable String email) {
        return authService.checkEmail(email);
    }

    @GetMapping("/reset/password/{username}")
    public int resetPassword(@PathVariable String username) {
        String email = authService.getEmail(username);
        if (email == "") return -1;
        return authService.sendCode(email);
    }

    @GetMapping("/change/Password")
    public boolean changePass(@RequestParam String username, @RequestParam String password) {
        return authService.changePassword(username, password);
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password) {

        return authService.logIn(username, password);
    }

    @PostMapping("/signup")
    public boolean signup(@RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam String role) {
        return authService.signUp(username, password, email, role);
    }

    @GetMapping("/send/code/{email}")
    public int codeSend(@PathVariable String email) {
        return authService.sendCode(email);
    }
}
