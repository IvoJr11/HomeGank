package com.mindhub.Homebanking.dtos;
import com.mindhub.Homebanking.models.Account;
import com.mindhub.Homebanking.models.Transaction;
import com.mindhub.Homebanking.models.Type;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class TransactionDTO {

    private long id;
    private Double amount;
    private String description;
    private LocalDateTime date;
    private Type type;
    private Double newAmount;

    public TransactionDTO () {};

    public TransactionDTO (Transaction transaction) {

        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.type = transaction.getType();
        this.newAmount = transaction.getNewAmount();

    }

    public long getId() {return id;}

    public Double getAmount() {return amount;}

    public String getDescription() {return description;}

    public LocalDateTime getDate() {return date;}

    public Type getType() {return type;}

    public Double getNewAmount() {return newAmount;}
}
