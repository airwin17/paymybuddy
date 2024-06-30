package com.pmb.paymybuddy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pmb.paymybuddy.exceptions.EmailAlreadyExistsException;
import com.pmb.paymybuddy.exceptions.UserNotFoundException;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repositories.intefaces.UserRepository;
import com.pmb.paymybuddy.repositories.repositoriesImpl.UserRepositoryimpl;
@Service
public class UserService {
    private UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepositoryimpl();
    }
    public void save(User user) throws EmailAlreadyExistsException {
        if(userRepository.findUserByEmail(user.getEmail()).isPresent()) 
            throw new EmailAlreadyExistsException("Email already exists");
        else
            userRepository.save(user);
    }
    public Optional<User> findUserById(String id) {
        return userRepository.findUserById(id);
    }
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }
    public void deleteUserById(String id) throws UserNotFoundException {
        if(userRepository.findUserById(id).isPresent())
            userRepository.deleteUserById(id);
        else
            throw new UserNotFoundException("User not found");
    }
    public void updateUser(User user) throws UserNotFoundException{
        if(userRepository.findUserById(user.getId()).isPresent())
            userRepository.updateUser(user);
        else
            throw new UserNotFoundException("User not found");
    }
    public void deleteAll() {
        ((UserRepositoryimpl)userRepository).deleteAllUser();
    }

}
