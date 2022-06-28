package com.mindhub.Homebanking.repositories;

import com.mindhub.Homebanking.models.Account;
import com.mindhub.Homebanking.models.Card;
import com.mindhub.Homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CardRepository extends JpaRepository <Card, Long> {

    Card findByNumber(String number);

}
