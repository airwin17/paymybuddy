package com.pmb.paymybuddy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Transaction {
    @Id
    private String id;
    @OneToOne
    private BankAcount sender;
    @OneToOne
    private BankAcount receiver;
    private double amount;
    private String description;
    
    public Transaction(String id, BankAcount sender, BankAcount receiver, double amount, String description) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.description = description;
    }
    public Transaction(BankAcount sender, BankAcount receiver, double amount, String description) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BankAcount getSender() {
        return sender;
    }

    public void setSender(BankAcount sender) {
        this.sender = sender;
    }

    public BankAcount getReceiver() {
        return receiver;
    }

    public void setReceiver(BankAcount receiver) {
        this.receiver = receiver;
    }
    
}
