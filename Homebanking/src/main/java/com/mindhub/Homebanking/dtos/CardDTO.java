package com.mindhub.Homebanking.dtos;

import com.mindhub.Homebanking.models.Card;
import com.mindhub.Homebanking.models.CardColor;
import com.mindhub.Homebanking.models.CardType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CardDTO {

    private long id;
    private String CardHolder;
    private CardType Type;
    private CardColor Color;
    private String number;
    private int Cvv;
    private LocalDateTime ThruDate;
    private LocalDateTime FromDate;
    private boolean active;

    public CardDTO () {};

    public CardDTO (Card card) {

        this.id= card.getId();
        this.CardHolder = card.getCardHolder();
        this.Type = card.getType();
        this.Color = card.getColor();
        this.number = card.getNumber();
        this.Cvv = card.getCvv();
        this.ThruDate = card.getThruDate();
        this.FromDate = card.getFromDate();
        this.active = card.isActive();

    }


    public long getId() {return id;}

    public String getCardHolder() {return CardHolder;}

    public CardType getType() {return Type;}

    public CardColor getColor() {return Color;}

    public String getNumber() {return number;}

    public int getCvv() {return Cvv;}

    public LocalDateTime getThruDate() {return ThruDate;}

    public LocalDateTime getFromDate() {return FromDate;}

    public boolean isActive() {return active;}
}
