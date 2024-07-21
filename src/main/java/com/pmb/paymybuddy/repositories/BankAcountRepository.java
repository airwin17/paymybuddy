package com.pmb.paymybuddy.repositories;

import java.util.Optional;



import org.springframework.data.repository.RepositoryDefinition;

import com.pmb.paymybuddy.model.BankAcount;
@RepositoryDefinition(domainClass=BankAcount.class, idClass=Integer.class)
public interface BankAcountRepository {
    <S extends BankAcount> BankAcount save(BankAcount entity);
    void deleteById(Integer id);
    Optional<BankAcount> findById(Integer id);
}
