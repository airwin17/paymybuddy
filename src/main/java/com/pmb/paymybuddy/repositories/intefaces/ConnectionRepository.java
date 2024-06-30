package com.pmb.paymybuddy.repositories.intefaces;

import java.util.Set;

public interface ConnectionRepository {
    public void saveConnection(String senderId, String receiverId);
    public void deleteConnection(String id) ;
    public void deleteAllConnections();
    public void deleteAllUserConnections(String userId);
    public void resetAutoIncrement();
    public Set<String> getConnectionsByUserId(String userId);
}
