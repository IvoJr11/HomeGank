package com.mindhub.Homebanking.repositories;

import com.mindhub.Homebanking.models.Account;
import com.mindhub.Homebanking.models.Card;
import com.mindhub.Homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository <Account, Long> {

    public List<Account> findByClient(Client client);

    Account findByNumber(String number);

}
