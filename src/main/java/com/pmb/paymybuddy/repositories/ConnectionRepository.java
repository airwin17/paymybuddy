package com.pmb.paymybuddy.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;
import com.pmb.paymybuddy.model.Connection;
@RepositoryDefinition(domainClass = Connection.class, idClass = Integer.class)
public interface ConnectionRepository{
    <S extends Connection> Connection save(Connection entity);
    void deleteById1AndId2(int id1,int id2);
    Optional<Connection> findById1AndId2(int id1,int id2);
    List<Connection> findById1(int id);
}
