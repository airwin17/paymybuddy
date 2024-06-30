package com.pmb.paymybuddy.repositories.repositoriesImpl;

import java.sql.Connection;

import org.springframework.stereotype.Repository;

import com.pmb.paymybuddy.configuration.DatasourceConfig;
import com.pmb.paymybuddy.model.Transaction;
import com.pmb.paymybuddy.repositories.intefaces.TransactionRepository;
@Repository
public class TransactionRepositoryImpl implements TransactionRepository {
    private DatasourceConfig datasourceConfig = new DatasourceConfig();
    @Override
    public void saveTransaction(Transaction transaction) {
        
    }

    @Override
    public Transaction getTransaction(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTransaction'");
    }

    @Override
    public void deleteTransactionById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteTransactionById'");
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTransaction'");
    }

}
