package com.mindhub.Homebanking.services;

import com.mindhub.Homebanking.dtos.CardDTO;
import com.mindhub.Homebanking.models.Card;
import org.springframework.security.core.Authentication;


public interface CardService {

    Card saveCards(Card card);

    Card getCardByNumber(long id);

    Card getCardByNumberString(String number);
}
