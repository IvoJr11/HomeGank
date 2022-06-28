package com.mindhub.Homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;
    private String CardHolder;
    private CardColor Color;
    private CardType Type;
    private String number;
    private int Cvv;
    private LocalDateTime ThruDate;
    private LocalDateTime FromDate;
    private boolean active;

    public Card() {}

    public Card(Client client, CardColor color, CardType type, String number, int cvv, LocalDateTime thruDate, LocalDateTime fromDate, boolean active) {

        this.client = client;
        this.CardHolder = client.getFirstName() + " " + client.getLastName();
        this.Color = color;
        this.Type = type;
        this.number = number;
        this.Cvv = cvv;
        this.ThruDate = thruDate;
        this.FromDate = fromDate;
        this.active = active;

    }

    public long getId() {return id;}

    public Client getClient() {return client;}
    public void setClient(Client client) {this.client = client;}

    public String getCardHolder() {return CardHolder;}
    public void setCardHolder(String cardHolder) {CardHolder = cardHolder;}

    public CardColor getColor() {return Color;}
    public void setColor(CardColor color) {Color = color;}

    public CardType getType() {return Type;}
    public void setType(CardType type) {Type = type;}

    public String getNumber() {return number;}
    public void setNumber(String number) {number = number;}

    public int getCvv() {return Cvv;}
    public void setCvv(int cvv) {Cvv = cvv;}

    public LocalDateTime getThruDate() {return ThruDate;}
    public void setThruDate(LocalDateTime thruDate) {ThruDate = thruDate;}

    public LocalDateTime getFromDate() {return FromDate;}
    public void setFromDate(LocalDateTime fromDate) {FromDate = fromDate;}

    public boolean isActive() {return active;}
    public void setActive(boolean active) {this.active = active;}

}
