package com.pmb.paymybuddy.services;

import java.util.List;
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
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public void save(User user) throws EmailAlreadyExistsException {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent())
            throw new EmailAlreadyExistsException("Email already exists");
        else {
            String encryptedPassword = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
            user.setPassword(encryptedPassword);
            userRepository.save(user);
        }
    }

    public void addConnection(String email, User logedUser) throws UserNotFoundException,ActionNotAllowed {
        if(logedUser.getEmail().equals(email)) throw new ActionNotAllowed("Action not allowed");
        Optional<User> targetUser = userRepository.findUserByEmail(email);
        if (targetUser.isPresent()) {
            logedUser.getConnectedUser().add(targetUser.get());
            userRepository.save(logedUser);
        } else
            throw new UserNotFoundException("User not found");
    }

    public void updateUser(User Nuser, User user) {
        user.setEmail(Nuser.getEmail());
        user.setUsername(Nuser.getUsername());
        if (!Nuser.getPassword().equals(""))
            user.setPassword(encrypPassword(Nuser.getPassword()));
        userRepository.save(user);
    }
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent())
            return user.get();
        else
            throw new UsernameNotFoundException("User not found");
    }

    public String encrypPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
}
