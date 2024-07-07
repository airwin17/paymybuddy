package com.pmb.paymybuddy.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmb.paymybuddy.exceptions.UserNotFoundException;
import com.pmb.paymybuddy.model.Transaction;
import com.pmb.paymybuddy.services.TransactionService;
@RestController
public class TransactionController {
    /*TransactionService transactionService;
    public TransactionController() {
        transactionService = new TransactionService();
    }
    @PostMapping("/saveTransaction")
    public ResponseEntity<String> saveTransaction(Transaction transaction) {
        transactionService.saveTransaction(transaction);
        return ResponseEntity.ok("Transaction saved");
    }
    @DeleteMapping("/deleteTransactions")
    public ResponseEntity<String> deleteTransactionsById(String id) {
        try {
            transactionService.deleteTransactionsById(id);
            return ResponseEntity.ok("Transactions deleted");
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/deleteAllTransactions")
    public ResponseEntity<String> deleteAllTransactions() {
        transactionService.deleteAllTransactions();
        return ResponseEntity.ok("Transactions deleted");
    }*/
}
