package com.pmb.paymybuddy.repositories.repositoriesImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.pmb.paymybuddy.configuration.DatasourceConfig;
import com.pmb.paymybuddy.constent.ConnectionDBconstents;
import com.pmb.paymybuddy.repositories.intefaces.ConnectionRepository;

public class ConnectionRepositoryImpl implements ConnectionRepository {
    private DatasourceConfig datasourceConfig;
    public ConnectionRepositoryImpl() {
        this.datasourceConfig = new DatasourceConfig();
    }
    @Override
    public void saveConnection(String senderId, String receiverId) {
        Connection con=null;
        try {
            con = datasourceConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(ConnectionDBconstents.ADD_CONNECTION);
            ps.setString(1, senderId);
            ps.setString(2, receiverId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            datasourceConfig.closeConnection(con);
        }
    }

    @Override
    public void deleteConnection(String id) {
        Connection con=null;
        try {
            con = datasourceConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(ConnectionDBconstents.DELETE_CONNECTION);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            datasourceConfig.closeConnection(con);
        }
    }

    @Override
    public void deleteAllConnections() {
        Connection con=null;
        try {
            con = datasourceConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(ConnectionDBconstents.DELETE_ALL_CONNECTIONS);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            datasourceConfig.closeConnection(con);
        }
    }

    @Override
    public void deleteAllUserConnections(String userId) {
       Connection con=null;
        try {
            con = datasourceConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(ConnectionDBconstents.DELETE_ALL_USER_CONNECTIONS);
            ps.setString(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            datasourceConfig.closeConnection(con);
        }
    }

    @Override
    public void resetAutoIncrement() {
        Connection con=null;
        try {    
            con = datasourceConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(ConnectionDBconstents.RESET_AUTO_INCREMENT);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            datasourceConfig.closeConnection(con);
        }
    }
    @Override
    public Set<String> getConnectionsByUserId(String userId) {
        Connection con=null;
        Set<String> res = new HashSet<>();
        try {
            con = datasourceConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(ConnectionDBconstents.FIND_CONNECTION);
            ps.setString(1, userId);
            ps.setString(2, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if(rs.getString(2).equals(userId)) 
                    res.add(rs.getString(3));
                else
                    res.add(rs.getString(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            datasourceConfig.closeConnection(con);
        }
        return res;
    }
}
