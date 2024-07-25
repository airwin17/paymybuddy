package com.pmb.paymybuddy.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pmb.paymybuddy.dto.UserDto;
import com.pmb.paymybuddy.exceptions.ActionNotAllowed;
import com.pmb.paymybuddy.exceptions.ConnectionAlreadyExistException;
import com.pmb.paymybuddy.exceptions.EmailAlreadyExistsException;
import com.pmb.paymybuddy.exceptions.UserNotFoundException;
import com.pmb.paymybuddy.model.BankAcount;
import com.pmb.paymybuddy.model.Connection;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repositories.BankAcountRepository;
import com.pmb.paymybuddy.repositories.ConnectionRepository;
import com.pmb.paymybuddy.repositories.UserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BankAcountRepository bankAcountRepository;
    @Autowired
    private ConnectionRepository connectionRepository;

    public void createUser(User user) throws EmailAlreadyExistsException {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent())
            throw new EmailAlreadyExistsException("Email already exists");
        else {
            String encryptedPassword = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
            user.setPassword(encryptedPassword);
            user.setBankAcount(bankAcountRepository.save(new BankAcount(0)));
            userRepository.save(user);
        }
    }

    public void addConnection(String email, User logedUser)
            throws UserNotFoundException, ActionNotAllowed, ConnectionAlreadyExistException {
        if (logedUser.getEmail().equals(email))
            throw new ActionNotAllowed("Action not allowed");
        Optional<User> targetUserNO = userRepository.findUserByEmail(email);

        if (targetUserNO.isPresent()) {
            User targetUser = loadConnectionForUser(targetUserNO.get());
            if (!logedUser.getConnectedUser().contains(targetUser.getId())) {
                connectionRepository.save(new Connection(logedUser.getId(), targetUser.getId()));
            } else
                throw new ConnectionAlreadyExistException("Action not allowed");
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

    public User findUserByEmail(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent())
            return loadConnectionForUser(user.get());
        else
            return null;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void addCash(User user, double amount) {
        user.getBankAcount().setBalance(user.getBankAcount().getBalance() + amount);
        bankAcountRepository.save(user.getBankAcount());
        userRepository.save(user);
    }

    public void setcash(User user, int amount) {
        user.getBankAcount().setBalance(amount);
        bankAcountRepository.save(user.getBankAcount());
        userRepository.save(user);
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

    public User loadConnectionForUser(User user) {
        List<Connection> connections = connectionRepository.findById1(user.getId());
        for (Connection connection : connections) {
            user.getConnectedUser().add(connection.getId2());
        }
        return user;
    }
    public List<UserDto> getConnectedUser(Set<Integer> users) {
        List<UserDto> userDtos = new LinkedList<>();
        for (int id : users) {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                UserDto userDto=new UserDto();
                userDto.setUsername(user.get().getUsername());
                userDto.setEmail(user.get().getEmail());
                userDtos.add(userDto);
            }
        }
        return userDtos;
    }
}
