package com.walletapp.service;

import com.walletapp.model.Transaction;
import com.walletapp.model.User;

import java.util.List;

public interface WalletService {
    User getUser(Long userId);
    void addMoney(Long userId, double amount);
    void withdrawMoney(Long userId, double amount);
    List<Transaction> getTransactions(Long userId);
    double getBalance(Long userId);

}
