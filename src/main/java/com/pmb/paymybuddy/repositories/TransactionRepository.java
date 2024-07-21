package com.pmb.paymybuddy.repositories;


import java.util.List;

import org.springframework.data.repository.RepositoryDefinition;
import com.pmb.paymybuddy.model.Transaction;
import com.pmb.paymybuddy.model.User;
@RepositoryDefinition(domainClass = Transaction.class, idClass = Integer.class)
public interface TransactionRepository{
    <S extends Transaction> Transaction save(Transaction transaction);
    List<Transaction> findBySenderOrReceiver(User sender, User receiver);
}
