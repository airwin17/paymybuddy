package com.pmb.paymybuddy.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmb.paymybuddy.dto.TransactionDTO;
import com.pmb.paymybuddy.exceptions.ConnectionNotFoundException;
import com.pmb.paymybuddy.exceptions.NotEnoughBalanceException;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.services.TransactionService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @Operation(summary = "Create a new transaction")
    @PostMapping("/saveTransaction")
    public ResponseEntity<String> createTransaction(@RequestBody TransactionDTO transactionDto,
            @AuthenticationPrincipal User user) {
        try {
            transactionService.createTransaction(transactionDto, user);
            return ResponseEntity.ok("Transaction saved");
        } catch (NotEnoughBalanceException e) {
            return ResponseEntity.status(401).build();
        } catch (ConnectionNotFoundException e) {
            return ResponseEntity.status(404).build();
        }
    }
}
