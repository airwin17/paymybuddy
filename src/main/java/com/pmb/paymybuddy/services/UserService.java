package com.pmb.paymybuddy.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

    private UserRepository userRepository;

    private BankAcountRepository bankAcountRepository;

    private ConnectionRepository connectionRepository;

    public UserService(UserRepository userRepository, BankAcountRepository bankAcountRepository,
            ConnectionRepository connectionRepository) {
        this.userRepository = userRepository;
        this.bankAcountRepository = bankAcountRepository;
        this.connectionRepository = connectionRepository;
    }
    /**
     * Creates a new user if the email is not already used
     * @param {@link User} user the user to be created
     * @throws EmailAlreadyExistsException if the email is already used
     */
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
    /**
     * Adds a connection between the loged user and the target user
     * @param email the email of the target user
     * @param {@link User} loged user
     * @throws UserNotFoundException if the target user is not found
     * @throws ActionNotAllowed if the loged user tries to connect to himself
     * @throws ConnectionAlreadyExistException if the connection already exists
     */
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
    /**
     * Updates the user's information
     * @param Nuser the new user information
     * @param user the user to be updated
     */
    public void updateUser(User Nuser, User user) {
        user.setEmail(Nuser.getEmail());
        user.setUsername(Nuser.getUsername());
        if (!Nuser.getPassword().equals(""))
            user.setPassword(encrypPassword(Nuser.getPassword()));
        userRepository.save(user);
    }
    /**
     * Finds a user by email
     * @param email the email of the user
     * @return the user if found, null otherwise
     */
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
    /**
     * Adds cash to the user's account
     * @param user the user to add cash to
     * @param amount the amount to be added to the user's account, can be negative
     */
    public void addCash(User user, double amount) {
        user.getBankAcount().setBalance(user.getBankAcount().getBalance() + amount);
        bankAcountRepository.save(user.getBankAcount());
        userRepository.save(user);
    }
    /**
     * Sets the user's account balance to the given amount
     * @param user the user to set the balance for
     * @param amount the amount to set the balance to
     */
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
    /**
     * Encrypts the password
     * @param password the password to be encrypted
     * @return the encrypted password
     */
    public String encrypPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
    /**
     * Loads the connections for a given user
     * @param user the user to load the connections for
     * @return the {@link User} with the connections loaded
     */
    public User loadConnectionForUser(User user) {
        List<Connection> connections = connectionRepository.findById1(user.getId());
        for (Connection connection : connections) {
            user.getConnectedUser().add(connection.getId2());
        }
        return user;
    }
    /**
     * Returns the list of connected users
     * @param users the list of connected users
     * @return the list of connected users
     */
    public List<UserDto> getConnectedUser(Set<Integer> users) {
        List<UserDto> userDtos = new LinkedList<>();
        for (int id : users) {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                UserDto userDto = new UserDto();
                userDto.setUsername(user.get().getUsername());
                userDto.setEmail(user.get().getEmail());
                userDtos.add(userDto);
            }
        }
        return userDtos;
    }
}
