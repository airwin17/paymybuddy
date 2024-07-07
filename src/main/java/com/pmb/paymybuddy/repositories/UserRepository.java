package com.pmb.paymybuddy.repositories;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

import com.pmb.paymybuddy.model.User;
@RepositoryDefinition(domainClass = User.class, idClass = Integer.class)
public interface UserRepository extends JpaRepository<User, Integer> {
    <S extends User> S save(User user);
    Optional<User> findById(Integer id);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
    void delete(User user);
    void deleteAll();
    List<User> findAll();
}
