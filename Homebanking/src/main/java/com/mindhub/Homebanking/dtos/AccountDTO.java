package com.mindhub.Homebanking.dtos;

import com.mindhub.Homebanking.models.Account;
import com.mindhub.Homebanking.models.AccountType;
import com.mindhub.Homebanking.models.Transaction;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private long id;
    private String Number;
    private Double Balance;
    private LocalDateTime creationDate;
    private Set<TransactionDTO> transactionsDTO = new HashSet<>();
    private boolean active;
    private AccountType type;

    public AccountDTO () {}

    public AccountDTO (Account account) {

        this.id = account.getId();
        this.Number = account.getNumber();
        this.Balance = account.getBalance();
        this.creationDate = account.getCreationDate();
        this.transactionsDTO = account.getTransactions().stream().map(TransactionDTO::new).collect(Collectors.toSet());
        this.active = account.isActive();
        this.type = account.getType();

    }

    public long getId() {return id;}

    public String getNumber() {return Number;}
    public void setNumber(String number) {Number = number;}

    public Double getBalance() {return Balance;}
    public void setBalance(Double balance) {Balance = balance;}

    public LocalDateTime getCreationDate() {return creationDate;}
    public void setCreationDate(LocalDateTime creationDate) {this.creationDate = creationDate;}

    public Set<TransactionDTO> getTransactionsDTO() {return transactionsDTO;}

    public boolean isActive() {return active;}

    public AccountType getType() {return type;}
}
