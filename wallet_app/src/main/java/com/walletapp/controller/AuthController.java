package com.walletapp.controller;

import com.walletapp.model.User;
import com.walletapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // returns login.html from templates
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register"; // returns register.html
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                                @RequestParam String email,
                                @RequestParam String password,
                                Model model) {
        if (userRepo.findByEmail(email).isPresent()) {
            return "redirect:/register?error";
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setBalance(0.0);
        userRepo.save(user);

        return "redirect:/login?success";
    }
}
