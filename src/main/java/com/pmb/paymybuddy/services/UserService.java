package com.pmb.paymybuddy.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pmb.paymybuddy.exceptions.ActionNotAllowed;
import com.pmb.paymybuddy.exceptions.EmailAlreadyExistsException;
import com.pmb.paymybuddy.exceptions.UserNotFoundException;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repositories.UserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
@Service
public class UserService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;
    public void save(User user) throws EmailAlreadyExistsException {
        if(userRepository.findUserByEmail(user.getEmail()).isPresent())
            throw new EmailAlreadyExistsException("Email already exists");
        else{
            String encryptedPassword = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
            user.setPassword(encryptedPassword);
            userRepository.save(user);
        }
    }
    public void addConnection(User user, int id2) throws UserNotFoundException,ActionNotAllowed {
        Optional<User> user1 = userRepository.findById(id2);
        if(user1.isPresent()) {
            user1.get().getConnectedUser().add(userRepository.findById(id2).get());
        }else throw new UserNotFoundException("User not found");
    }
    public void updateUser(User Nuser) throws UserNotFoundException{
        Optional<User> user1 = userRepository.findById(Nuser.getId());
        if(user1.isPresent()){
            User user=user1.get();
            user.setEmail(Nuser.getEmail());
            user.setUsername(Nuser.getUsername());
            if(Nuser.getPassword()!=null)
                user.setPassword(encrypPassword(user1.get().getPassword()));
            userRepository.save(user);
        }else
            throw new UserNotFoundException("User not found");
    }
    /*public void deleteConnection(String id1, String id2) {
        userRepository.deleteConnection(id1, id2);
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
    
    public void deleteAll() {
        userRepository.deleteAllUser();
    }*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(username);
        if(user.isPresent())
            return user.get();
        else
            throw new UsernameNotFoundException("User not found");
    }
    public String encrypPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
}
