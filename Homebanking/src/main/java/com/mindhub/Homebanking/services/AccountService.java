package com.mindhub.Homebanking.services;

import com.mindhub.Homebanking.dtos.AccountDTO;
import com.mindhub.Homebanking.models.Account;
import com.mindhub.Homebanking.models.Client;

import java.util.List;

public interface AccountService {

    List<AccountDTO> getAccountsDTO();
    Account saveAccount(Account account);
    AccountDTO getAccountDTO(long id);
    List<Account> getAccountByClient(Client client);
    Account getAccountByNumber(String number);
    Account getAccountById(long id);
}
