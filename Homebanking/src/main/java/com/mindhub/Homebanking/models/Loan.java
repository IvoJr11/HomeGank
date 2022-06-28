package com.mindhub.Homebanking.models;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @OneToMany (mappedBy = "loan", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    private String name;
    private Double maxAmount;
    private Double percent;

    @ElementCollection
    @Column(name="payments")
    private List<Integer> payments = new ArrayList<>();

    public Loan () {};

    public Loan (String name, Double maxAmount, List<Integer> payments, Double percent) {

        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.percent = percent;

    }

    public Set<ClientLoan> getClientLoans() {return clientLoans;}

    public void addClientLoan (ClientLoan clientLoan) {
        clientLoan.setLoan(this);
        clientLoans.add(clientLoan);
    }

    public long getId() {return id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Double getMaxAmount() {return maxAmount;}
    public void setMaxAmount(Double maxAmount) {this.maxAmount = maxAmount;}

    public List<Integer> getPayments() {return payments;}
    public void setPayments(List<Integer> payments) {this.payments = payments;}

    public List<Client> Clients() {return clientLoans.stream().map(ClientLoan::getClient).collect(Collectors.toList());}

    public Double getPercent() {return percent;}
    public void setPercent(Double percent) {this.percent = percent;}
}
