package com.mindhub.Homebanking.services;

import com.mindhub.Homebanking.models.Account;
import com.mindhub.Homebanking.models.Transaction;

import java.util.List;
import java.util.Set;

public interface TransactionService {

    Transaction saveTransaction(Transaction transaction);

    Set<Transaction> getTransactionsByAccount(String number);
}
