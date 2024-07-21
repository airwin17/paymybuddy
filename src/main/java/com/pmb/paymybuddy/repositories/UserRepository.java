package com.pmb.paymybuddy.repositories;

import java.util.List;
import java.util.Optional;


import org.springframework.data.repository.RepositoryDefinition;
import com.pmb.paymybuddy.model.User;
@RepositoryDefinition(domainClass = User.class, idClass = Integer.class)
public interface UserRepository {
    <S extends User> User save(User user);
    Optional<User> findById(Integer id);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
    void deleteUserByEmail(String user);
    void deleteAll();
    List<User> findAll();
    void delete(User user);
}
