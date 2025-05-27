package com.walletapp.controller;

import com.walletapp.model.Transaction;
import com.walletapp.service.WalletService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService service;

    public WalletController(WalletService service) {
        this.service = service;
    }

    @GetMapping("/balance")
    public double getBalance(Principal principal) {
        return service.getBalance(getUserIdFromUsername(principal));
    }

    @PostMapping("/add")
    public String addMoney(@RequestParam double amount, Principal principal) {
        service.addMoney(getUserIdFromUsername(principal), amount);
        return "Amount added!";
    }

    @PostMapping("/withdraw")
    public String withdrawMoney(@RequestParam double amount, Principal principal) {
        service.withdrawMoney(getUserIdFromUsername(principal), amount);
        return "Amount withdrawn!";
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactions(Principal principal) {
        return service.getTransactions(getUserIdFromUsername(principal));
    }

    private Long getUserIdFromUsername(Principal principal) {
        // For simplicity assuming user ID = 1 for the demo user
        return 1L;
    }
}
