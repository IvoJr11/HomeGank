package com.mindhub.Homebanking.services.implement;

import com.mindhub.Homebanking.dtos.CardDTO;
import com.mindhub.Homebanking.models.Card;
import com.mindhub.Homebanking.models.Client;
import com.mindhub.Homebanking.repositories.CardRepository;
import com.mindhub.Homebanking.repositories.ClientRepository;
import com.mindhub.Homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public Card saveCards(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public Card getCardByNumber(long id) {
       return cardRepository.findById(id).orElse(null);
    }

    @Override
    public Card getCardByNumberString(String number) {
        return cardRepository.findByNumber(number);
    }

}
