package com.pmb.paymybuddy.services;

import com.pmb.paymybuddy.exceptions.ConnectionAlreadyExistException;
import com.pmb.paymybuddy.exceptions.ConnectionNotFoundException;
import com.pmb.paymybuddy.exceptions.UserNotFoundException;
import com.pmb.paymybuddy.repositories.intefaces.ConnectionRepository;
import com.pmb.paymybuddy.repositories.repositoriesImpl.ConnectionRepositoryImpl;
import com.pmb.paymybuddy.repositories.repositoriesImpl.UserRepositoryimpl;

public class ConnectionService {
    private ConnectionRepository connectionRepository;
    public ConnectionService() {
        connectionRepository = new ConnectionRepositoryImpl();
    }
    public void saveConnection(String user1, String user2) throws ConnectionAlreadyExistException{
        if(connectionRepository.getConnectionsByUserId(user1).contains(user2) || 
        connectionRepository.getConnectionsByUserId(user2).contains(user1)){
            connectionRepository.saveConnection(user1, user2);
        }else
            throw new ConnectionAlreadyExistException("Connection already exist");
    }

    public void deleteConnection(String id) throws UserNotFoundException{
        
    }
}
