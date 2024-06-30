package com.pmb.paymybuddy.repositories.repositoriesImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import com.pmb.paymybuddy.configuration.DatasourceConfig;
import com.pmb.paymybuddy.constent.UserDBconstents;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repositories.intefaces.UserRepository;
@Repository
public class UserRepositoryimpl implements UserRepository {
    DatasourceConfig datasourceConfig = new DatasourceConfig();
    Logger logger = Logger.getLogger(UserRepositoryimpl.class.getName());
    @Override
    public void save(User user) {
        Connection con=null;
        try {
            con = datasourceConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(UserDBconstents.ADD_USER);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.info("Error while saving user");
            throw new RuntimeException(e);
        }finally{
            datasourceConfig.closeConnection(con);
        }
    }

    @Override
    public Optional<User> findUserById(String id) {
        Connection con=null;
        Optional<User> res;
        try {
            con = datasourceConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(UserDBconstents.FIND_USER_BY_ID);
            ps.setString(1, id);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                res = Optional.of(new User(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)));
            }else
                res = Optional.empty();
        } catch (SQLException e) {
            logger.info("Error while finding user");
            throw new RuntimeException(e);
        }finally{
            datasourceConfig.closeConnection(con);
        }
        return res;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        Connection con=null;
        Optional<User> res;
        try {
            con = datasourceConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(UserDBconstents.FIND_USER_BY_USERNAME);
            ps.setString(1, username);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                res = Optional.of(new User(rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)));
            }else
                res = Optional.empty();
        } catch (SQLException e) {
            logger.info("Error while finding user");
            throw new RuntimeException(e);
        }finally{
            datasourceConfig.closeConnection(con);
        }
        return res;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        Connection con=null;
        Optional<User> res;
        try {
            con = datasourceConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(UserDBconstents.FIND_USER_BY_EMAIL);
            ps.setString(1, email);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
                res = Optional.of(new User(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)));
            else
                res = Optional.empty();
        } catch (SQLException e) {
            logger.info("Error while finding user");
            throw new RuntimeException(e);
        }finally{
            datasourceConfig.closeConnection(con);
        }
        return res;
    }

    @Override
    public List<User> findAll() {
        List<User> res=new LinkedList<>();
        Connection con=null;
        try {
            con = datasourceConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(UserDBconstents.FIND_ALL_USER);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                res.add(new User(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)));
            }
        } catch (SQLException e) {
            logger.info("Error while finding all user");
            throw new RuntimeException(e);
        }finally{
            datasourceConfig.closeConnection(con);
        }
        return res;
    }

    @Override
    public void deleteUserById(String id) {
        Connection con=null;
        try {
            con = datasourceConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(UserDBconstents.DELETE_USER);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.info("Error while deleting user");
            throw new RuntimeException(e);
        }finally{
            datasourceConfig.closeConnection(con);
        }
    }

    @Override
    public void updateUser(User user) {
        Connection con=null;
        try {
            con = datasourceConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(UserDBconstents.UPDATE_USER);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.info("Error while updating user");
            throw new RuntimeException(e);
        }finally{
            datasourceConfig.closeConnection(con);
        }
    }
    public void deleteAllUser() {
        Connection con=null;
        try {
            con = datasourceConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(UserDBconstents.DELETE_ALL_USERS);
            ps.executeUpdate();
            PreparedStatement ps2 = con.prepareStatement(UserDBconstents.RESET_AUTO_INCREMENT);
            ps2.executeUpdate();
        } catch (SQLException e) {
            logger.info("Error while deleting all user");
            throw new RuntimeException(e);
        }finally{
            datasourceConfig.closeConnection(con);
        }
    }
}
