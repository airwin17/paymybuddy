package com.pmb.paymybuddy.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pmb.paymybuddy.dto.TransactionDTO;
import com.pmb.paymybuddy.exceptions.ConnectionNotFoundException;
import com.pmb.paymybuddy.exceptions.NotEnoughBalanceException;
import com.pmb.paymybuddy.model.Transaction;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repositories.TransactionRepository;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    private UserService userService;

    public TransactionService(TransactionRepository transactionRepository, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }
    /**
     * Creates new transaction between two users.if the follwing conditions met:
     * <ul>
     *  <li>the user is connected to the target user</li>
     *  <li>the user has enough balance to make the transaction</li>
     *  <li>the user is not the target user</li>
     * </ul>
     * if the user is the target user, the transaction is saved and the user's balance is updated
     * @param transactiondDto the transaction to be created
     * @param user who makes the transaction
     * @throws NotEnoughBalanceException if the user has not enough balance
     * @throws ConnectionNotFoundException if the user is not connected to the target user
     */
    public void createTransaction(TransactionDTO transactiondDto, User user)
            throws NotEnoughBalanceException, ConnectionNotFoundException {
        User targetUser = userService.findUserByEmail(transactiondDto.getRelationship());
        user = userService.loadConnectionForUser(user);
        double fee = transactiondDto.getAmount() * 0.005;
        Transaction transaction = new Transaction(user, targetUser, transactiondDto.getAmount(),
                transactiondDto.getDescription());
        if (user.getId().intValue() == targetUser.getId().intValue()) {
            transactionRepository.save(transaction);
            userService.addCash(user, transaction.getAmount());
        } else if (!user.getConnectedUser().contains(transaction.getReceiver().getId())) {
            throw new ConnectionNotFoundException("Connection not found");
        } else if (user.getBankAcount().getBalance() <= transaction.getAmount() + fee) {
            throw new NotEnoughBalanceException("Not enough balance");
        } else {
            transactionRepository.save(transaction);
            userService.addCash(user, -transaction.getAmount());
            userService.addCash(user, -fee);
            userService.addCash(targetUser, transaction.getAmount());
        }

    }
    /**
     * Returns the list of all transactions for a given user.
     * @param {@link User} the user to get the transactions for
     * @return the list of all transactions for a given user
     */
    public List<TransactionDTO> getTransactionsForView(User user) {
        List<Transaction> transactions = getTransactions(user);
        List<TransactionDTO> transactionDTOs = new LinkedList<>();
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getSender().getId() != user.getId()) {
                if (transactions.get(i).getSender().getId().intValue() == transactions.get(i).getReceiver().getId()
                        .intValue()) {
                    transactionDTOs.add(new TransactionDTO(
                            transactions.get(i).getReceiver().getUsername(),
                            transactions.get(i).getAmount(),
                            transactions.get(i).getDescription()));
                } else
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
