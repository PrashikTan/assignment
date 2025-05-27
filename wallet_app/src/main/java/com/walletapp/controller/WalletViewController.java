package com.walletapp.controller;

import com.walletapp.model.Transaction;
import com.walletapp.model.User;
import com.walletapp.repository.UserRepository;
import com.walletapp.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class WalletViewController {

    @Autowired private WalletService walletService;
    @Autowired private UserRepository userRepo;

    @GetMapping("/wallet-ui")
    public String showWallet(Model model, Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        List<Transaction> transactions = walletService.getTransactions(user.getId());

        model.addAttribute("balance", user.getBalance());
        model.addAttribute("transactions", transactions);
        return "wallet";
    }
}
