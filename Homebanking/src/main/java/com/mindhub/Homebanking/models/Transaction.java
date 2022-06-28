package com.mindhub.Homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "account_id")

    private Account account;
    private Double amount;
    private String description;
    private LocalDateTime date;
    private Type type;
    private Double newAmount;

    public Transaction() {};

    public Transaction (Double amount, String description, LocalDateTime date, Type type, Account account, Double newAmount) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
        this.account = account;
        this.newAmount = newAmount;
    }

    public long getId() {return id;}

    public Double getAmount() {return amount;}
    public void setAmount(Double amount) {this.amount = amount;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public LocalDateTime getDate() {return date;}
    public void setDate(LocalDateTime date) {this.date = date;}

    public Type getType() {return type;}
    public void setType(Type type) {this.type = type;}

    public Account getAccount() {return account;}
    public void setAccount(Account account) {this.account = account;}

    public void setTransaction(Account account) {}

    public Double getNewAmount() {return newAmount;}

    public void setNewAmount(Double newAmount) {this.newAmount = newAmount;}
}
