package com.walletapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walletapp.model.Transaction;
import com.walletapp.model.User;
import com.walletapp.repository.TransactionRepository;
import com.walletapp.repository.UserRepository;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired private UserRepository userRepo;
    @Autowired private TransactionRepository txnRepo;

    @Override
    public User getUser(Long userId) {
        return userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void addMoney(Long userId, double amount) {
        User user = getUser(userId);
        user.setBalance(user.getBalance() + amount);
        userRepo.save(user);
        txnRepo.save(new Transaction(null, userId, amount, "CREDIT", LocalDateTime.now()));
    }
    
    @Override
    public double getBalance(Long userId) {
        return getUser(userId).getBalance();
    }


    @Override
    public void withdrawMoney(Long userId, double amount) {
        User user = getUser(userId);
        if (user.getBalance() >= amount) {
            user.setBalance(user.getBalance() - amount);
            userRepo.save(user);
            txnRepo.save(new Transaction(null, userId, amount, "DEBIT", LocalDateTime.now()));
        }
    }

    @Override
    public List<Transaction> getTransactions(Long userId) {
        return txnRepo.findByUserIdOrderByTimestampDesc(userId);
    }
}
