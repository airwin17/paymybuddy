package com.pmb.paymybuddy.services;


import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmb.paymybuddy.dto.TransactionDTO;
import com.pmb.paymybuddy.exceptions.ConnectionNotFoundException;
import com.pmb.paymybuddy.exceptions.NotEnoughBalanceException;
import com.pmb.paymybuddy.model.Transaction;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repositories.TransactionRepository;
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    public void saveTransaction(Transaction transaction,User user) throws NotEnoughBalanceException, ConnectionNotFoundException {
        
        if(!user.getConnectedUser().contains(transaction.getReceiver())) {
            throw new ConnectionNotFoundException("Connection not found");
        }else if(!user.getConnectedUser().contains(transaction.getReceiver())) {
            throw new NotEnoughBalanceException("Not enough balance");
        }else
            transactionRepository.save(transaction);
    }
    public List<TransactionDTO> findTransactionsForView(User user) {
        List<Transaction> transactions = transactionRepository.findBySenderOrReceiver(user, user);
        List<TransactionDTO> transactionDTOs = new LinkedList<>();
        for(int i = 0; i < transactions.size(); i++) {
            if(transactions.get(i).getSender().getId() == user.getId()) {
                transactionDTOs.add(new TransactionDTO(
                    transactions.get(i).getReceiver().getUsername(), 
                    -transactions.get(i).getAmount(),
                    transactions.get(i).getDescription()));
            }else
                transactionDTOs.add(new TransactionDTO(
                    transactions.get(i).getSender().getUsername(), 
                    transactions.get(i).getAmount(), 
                    transactions.get(i).getDescription()));
        }
        return transactionDTOs;
    } 
}
