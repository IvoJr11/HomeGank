package com.mindhub.Homebanking.services.implement;

import com.mindhub.Homebanking.models.Account;
import com.mindhub.Homebanking.models.Transaction;
import com.mindhub.Homebanking.repositories.TransactionRepository;
import com.mindhub.Homebanking.services.AccountService;
import com.mindhub.Homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountService accountService;

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Set<Transaction> getTransactionsByAccount(String number) {
        return accountService.getAccountByNumber(number).getTransactions();
    }
}
