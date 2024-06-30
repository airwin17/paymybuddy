package com.pmb.paymybuddy.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private List<String> connectedUser;
    public User(String id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        connectedUser = new ArrayList<>();
    }

    public User() {
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(List<String> connectedUser) {
        this.connectedUser = connectedUser;
    }
    
}
