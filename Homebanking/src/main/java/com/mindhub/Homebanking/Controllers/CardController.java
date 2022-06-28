package com.mindhub.Homebanking.Controllers;

import com.mindhub.Homebanking.dtos.CardDTO;
import com.mindhub.Homebanking.models.Card;
import com.mindhub.Homebanking.models.CardColor;
import com.mindhub.Homebanking.models.CardType;
import com.mindhub.Homebanking.models.Client;
import com.mindhub.Homebanking.services.CardService;
import com.mindhub.Homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.mindhub.Homebanking.Utils.Utils.getRandomNumber;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    ClientService clientService;
    @Autowired
    CardService cardService;

    @GetMapping("clients/current/cards")
    public List<CardDTO> getCards(Authentication authentication){
        Client client = clientService.getClientCurrent(authentication);
        return client.getCards().stream().map(CardDTO::new).collect(Collectors.toList());
    }

    @PostMapping("/clients/current/cards")
    private ResponseEntity<Object> CardCreator(
            Authentication authentication, @RequestParam CardType type, @RequestParam CardColor color) {

        Client client = clientService.getClientCurrent(authentication);

        if(client.getCards().stream().filter(card -> (card.getType().equals(type)) && card.isActive()).collect(Collectors.toList()).size() >= 3) {

            return new ResponseEntity<>("The client can't 3 or more cards of this type", HttpStatus.FORBIDDEN);
        }

        String cardNumber = getRandomNumber(1000, 9999) + "-" + getRandomNumber(1000, 9999) + "-" + getRandomNumber(1000, 9999) + "-" + getRandomNumber(1000, 9999);

        cardService.saveCards(new Card(client, color, type, cardNumber, getRandomNumber(100, 999), LocalDateTime.now(), LocalDateTime.now().plusYears(5), true));
        return new ResponseEntity<>("Card has created successfully", HttpStatus.CREATED);
    }

    @PatchMapping("clients/current/cards")
    private ResponseEntity<Object> disableCard(Authentication authentication, @RequestParam long id) {

        Client client = clientService.getClientCurrent(authentication);

        Card card = cardService.getCardByNumber(id);

        if(card == null) {
            return new ResponseEntity<>("The card doesn't exist",HttpStatus.FORBIDDEN);
        }

        if(!client.getCards().contains(card)) {
            return new ResponseEntity<>("The card does not belong to the client", HttpStatus.FORBIDDEN);
        }

        card.setActive(false);

        cardService.saveCards(card);

        return new ResponseEntity<>("Card has been disabled", HttpStatus.CREATED);

    }

}
