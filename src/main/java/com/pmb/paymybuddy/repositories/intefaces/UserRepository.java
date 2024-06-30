package com.pmb.paymybuddy.repositories.intefaces;

import java.util.List;
import java.util.Optional;

import com.pmb.paymybuddy.exceptions.EmailAlreadyExistsException;
import com.pmb.paymybuddy.model.User;

public interface UserRepository {
    public void save(User user) throws EmailAlreadyExistsException;
    public Optional<User> findUserById(String id);
    public Optional<User> findUserByUsername(String username);
    public Optional<User> findUserByEmail(String email);
    public List<User> findAll();
    public void deleteUserById(String id);
    public void updateUser(User user);
}
