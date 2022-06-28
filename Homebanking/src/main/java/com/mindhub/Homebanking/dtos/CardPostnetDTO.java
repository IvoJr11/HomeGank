package com.mindhub.Homebanking.dtos;

import com.mindhub.Homebanking.models.Card;
import com.mindhub.Homebanking.models.Transaction;

public class CardPostnetDTO {

    public String number;
    public int cvv;
    public double amount;
    public String description;

    public CardPostnetDTO() {};

    public CardPostnetDTO(Card card, Transaction transaction) {

        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();

    }

    public String getNumber() {return number;}

    public int getCvv() {return cvv;}

    public double getAmount() {return amount;}

    public String getDescription() {return description;}

}
