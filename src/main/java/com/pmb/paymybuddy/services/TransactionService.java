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
    @Autowired
    private UserService userService;

    public void createTransaction(TransactionDTO transactiondDto, User user)
            throws NotEnoughBalanceException, ConnectionNotFoundException {
        User targetUser = userService.findUserByEmail(transactiondDto.getRelationship());
        user=userService.loadConnectionForUser(user);
        double fee=transactiondDto.getAmount()*0.005;
        Transaction transaction = new Transaction(user, targetUser, transactiondDto.getAmount(),transactiondDto.getDescription());
        if (!user.getConnectedUser().contains(transaction.getReceiver().getId())) {
            throw new ConnectionNotFoundException("Connection not found");
        } else if (user.getBankAcount().getBalance() <= transaction.getAmount()+fee) {
            throw new NotEnoughBalanceException("Not enough balance");
        }else if(user.getId()==targetUser.getId()){
            transactionRepository.save(transaction);
            userService.addCash(user, transaction.getAmount());
        } else{
            transactionRepository.save(transaction);
            userService.addCash(user, -transaction.getAmount());
            userService.addCash(user, -fee);
            userService.addCash(targetUser, transaction.getAmount());
        }
           
    }

    public List<TransactionDTO> getTransactionsForView(User user) {
        List<Transaction> transactions = getTransactions(user);
        List<TransactionDTO> transactionDTOs = new LinkedList<>();
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getSender().getId() != user.getId()) {
                transactionDTOs.add(new TransactionDTO(
                        transactions.get(i).getReceiver().getUsername(),
                        -transactions.get(i).getAmount(),
                        transactions.get(i).getDescription()));
            } else
                transactionDTOs.add(new TransactionDTO(
                        transactions.get(i).getSender().getUsername(),
                        transactions.get(i).getAmount(),
                        transactions.get(i).getDescription()));
        }
        return transactionDTOs;
    }
    public List<Transaction> getTransactions(User user) {
        return transactionRepository.findBySenderOrReceiver(user, user);
    }
    
}
