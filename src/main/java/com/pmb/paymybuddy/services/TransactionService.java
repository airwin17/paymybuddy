package com.pmb.paymybuddy.services;


import java.util.List;

import com.pmb.paymybuddy.exceptions.UserNotFoundException;
import com.pmb.paymybuddy.model.Transaction;
import com.pmb.paymybuddy.repositories.TransactionRepository;

public class TransactionService {
    TransactionRepository transactionRepository;
    UserService userService;
    /*public TransactionService() {
        this.transactionRepository = new TransactionRepository();
        this.userService = new UserService();
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.saveTransaction(transaction);
    }

    public List<Transaction> getAllTransaction(String id) {
        return transactionRepository.getAllTransaction(id);
    }

    public void deleteTransactionsById(String id) throws UserNotFoundException {
        if(userService.findUserById(id).isPresent()){
            transactionRepository.deleteTransactionsById(id);
        }else{
            throw new UserNotFoundException("User not found");
        }
    }
    public void deleteAllTransactions() {
        transactionRepository.deleteAllTransactions();
    }   */
}
