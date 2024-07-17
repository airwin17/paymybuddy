package com.pmb.paymybuddy.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String email;
    @OneToOne
    @PrimaryKeyJoinColumn(name = "bank_acount_id")
    private BankAcount bankAcount;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "connections", joinColumns = @JoinColumn(name = "userID1"), inverseJoinColumns = @JoinColumn(name = "userID2"))
    private Set<User> connectedUser;

    public User(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        connectedUser = new HashSet<>();
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
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

    public Set<User> getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(Set<User> connectedUser) {
        this.connectedUser = connectedUser;
    }

    public BankAcount getBankAcount() {
        return bankAcount;
    }

    public void setBankAcount(BankAcount bankAcount) {
        this.bankAcount = bankAcount;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(() -> "USER");
        return authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            User user = (User) o;
            if (user.getEmail().equals(email)) {
                return true;
            } else
                return false;
        } else
            return false;
    }

}
