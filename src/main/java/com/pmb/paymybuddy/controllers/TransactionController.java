package com.pmb.paymybuddy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.pmb.paymybuddy.exceptions.ConnectionNotFoundException;
import com.pmb.paymybuddy.exceptions.NotEnoughBalanceException;
import com.pmb.paymybuddy.model.Transaction;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.services.TransactionService;
@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @PostMapping("/saveTransaction")
    public ResponseEntity<String> saveTransaction(@RequestBody Transaction transaction,@AuthenticationPrincipal User user) {
        try {
            transactionService.saveTransaction(transaction,user);
            return ResponseEntity.ok("Transaction saved");
        } catch (NotEnoughBalanceException e) {
            return ResponseEntity.status(401).build();
        }catch (ConnectionNotFoundException e) {
            return ResponseEntity.status(404).build();
        }
    }
}
