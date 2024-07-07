package com.pmb.paymybuddy.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.pmb.paymybuddy.configuration.DatasourceConfig;

import com.pmb.paymybuddy.model.Transaction;
@Repository
public class TransactionRepository {
    private DatasourceConfig datasourceConfig = new DatasourceConfig();
    /*public void saveTransaction(Transaction transaction) {
        Connection con=null;
        try {
            con = datasourceConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(TransactionDBconstents.ADD_TRANSACTION);
            ps.setString(1, transaction.getSender());
            ps.setString(2, transaction.getReceiver());
            ps.setDouble(3, transaction.getAmount());
            ps.setString(4, transaction.getDescription());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            datasourceConfig.closeConnection(con);
        }
    }
    public List<Transaction> getAllTransaction(String id) {
        Connection con=null;
        List<Transaction> res=new LinkedList<>();
        try {
            con = datasourceConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(TransactionDBconstents.GET_ALL_TRANSACTION);
            ps.setString(1, id);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                res.add(new Transaction(
                        rs.getString("id"),
                        rs.getString("sender"),
                        rs.getString("receiver"),
                        rs.getDouble("amount"),
                        rs.getString("description")
                        ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            datasourceConfig.closeConnection(con);
        }
        return res;
    }

    public void deleteTransactionsById(String id) {
        Connection con=null;
        try {
            con = datasourceConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(TransactionDBconstents.DELETE_TRANSACTION);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            datasourceConfig.closeConnection(con);
        }
    }
    public void deleteAllTransactions() {
        Connection con=null;
        try {
            con = datasourceConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(TransactionDBconstents.DELETE_ALL_TRANSACTION);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            datasourceConfig.closeConnection(con);
        }
    }/* */
}
