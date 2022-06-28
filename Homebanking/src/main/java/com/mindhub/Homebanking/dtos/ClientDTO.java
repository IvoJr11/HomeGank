package com.mindhub.Homebanking.dtos;

import com.mindhub.Homebanking.models.Card;
import com.mindhub.Homebanking.models.Client;
import com.mindhub.Homebanking.models.ClientLoan;
import com.mindhub.Homebanking.models.ClientType;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {

    private long id;
    private String firstName;
    private String lastName;
    private String email;

    private Set<AccountDTO> accountsDTO = new HashSet<>();
    private Set<ClientLoanDTO> loans = new HashSet<>();
    private Set<CardDTO> cards = new HashSet<>();
    private ClientType type;

    public ClientDTO() {}

    public ClientDTO (Client client) {

        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accountsDTO = client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toSet());
        this.loans = client.getClientLoan().stream().map(ClientLoanDTO::new).collect(Collectors.toSet());
        this.cards = client.getCards().stream().map(CardDTO::new).collect(Collectors.toSet());
        this.type = client.getType();

    }

    public long getId() {return id;}


    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public Set<AccountDTO> getAccountsDTO() {return accountsDTO;}
    public void setAccountsDTO(Set<AccountDTO> accountsDTO) {this.accountsDTO = accountsDTO;}

    public Set<ClientLoanDTO> getLoans() {return loans;}

    public Set<CardDTO> getCards() {return cards;}

    public ClientType getType() {return type;}
}
