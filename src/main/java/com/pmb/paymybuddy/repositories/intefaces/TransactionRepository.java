package com.pmb.paymybuddy.repositories.intefaces;

import com.pmb.paymybuddy.model.Transaction;

public interface TransactionRepository {
    public void saveTransaction(Transaction transaction);

    public Transaction getTransaction(String id);

    public void deleteTransactionById(String id);

    public void updateTransaction(Transaction transaction);
}
