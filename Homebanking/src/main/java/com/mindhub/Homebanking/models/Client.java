package com.mindhub.Homebanking.models;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @OneToMany(mappedBy="client", fetch= FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    @OneToMany (mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    @OneToMany (mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    private String firstName;
    private String lastName;
    private String email;
    private String Password;
    private ClientType type;

    public Client() {}

    public Client (String first, String last, String email, String password, ClientType type) {

        this.firstName = first;
        this.lastName = last;
        this.email = email;
        this.Password = password;
        this.type = type;

    }

    public Set<Account> getAccounts() {return accounts;}

    public void addAccount (Account account) {
        account.setCliente(this);
        accounts.add(account);
    }
    public Set<ClientLoan> getClientLoan() {return clientLoans;}

    public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);
    }

    public Set<Card> getCards() {return cards;}

    public void addCards(Card card) {
        card.setClient(this);
        cards.add(card);
    }

    public long getId () {return id;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return Password;}
    public void setPassword(String password) {Password = password;}

    public List<Loan> getLoans() {return clientLoans.stream().map(ClientLoan::getLoan).collect(Collectors.toList());}

    public ClientType getType() {return type;}
    public void setType(ClientType type) {this.type = type;}
    
}
