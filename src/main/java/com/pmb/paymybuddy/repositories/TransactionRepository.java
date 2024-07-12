package com.pmb.paymybuddy.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pmb.paymybuddy.model.Transaction;
@Repository
public interface TransactionRepository extends CrudRepository<Transaction,Integer> {
    <S extends Transaction> Transaction save(Transaction transaction);
    //List<Transaction> findBySenderOrReceiver(String sender, String resceiver);
}
