package com.pmb.paymybuddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "connections")
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "UserID1")
    private int id1;
    @Column(name = "UserID2")
    private int id2;

    public Connection(int id1, int id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

    public Connection() {
    }

    public int getId() {
        return id;
    }

    public int getId1() {
        return id1;
    }

    public int getId2() {
        return id2;
    }

}
