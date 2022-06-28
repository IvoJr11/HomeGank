package com.mindhub.Homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.time.LocalDateTime;

@Entity
public class Account {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @OneToMany (mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;
    private String number;
    private Double Balance;
    private LocalDateTime creationDate;
    private boolean active;
    private AccountType type;

    public Account() {}

    public Account (String Number, Double Balance, LocalDateTime date, Client client, boolean active, AccountType type) {

        this.number = Number;
        this.Balance = Balance;
        this.creationDate = date;
        this.client = client;
        this.active = active;
        this.type = type;

    }

    public LocalDateTime getCreationDate() {return creationDate;}

    public String getNumber () {return number;}
    public void setNumber(String number) {this.number = number;}

    public Double getBalance() {return Balance;}
    public void setBalance(Double balance) {Balance = balance;}

    public long getId () {return id;}

    public Client getCliente () {return client;}
    public void setCliente(Client client) {this.client = client;}

    public Set<Transaction> getTransactions() {return transactions;}
    public boolean isActive() {return active;}
    public void setActive(boolean active) {this.active = active;}

    public AccountType getType() {return type;}
    public void setType(AccountType type) {this.type = type;}
};

