package com.mindhub.Homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "loan_id")
    private Loan loan;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "client_id")
    private Client client;

    public Double amount;
    public int payments;

    public ClientLoan () {};

    public ClientLoan (Double amount, int payments, Loan loan, Client client) {

        this.amount = amount;
        this.payments = payments;
        this.loan = loan;
        this.client = client;

    }

    public long getId() {return id;}

    public Client getClient() {return client;}
    public void setClient(Client client) {}

    public Loan getLoan() {return loan;}
    public void setLoan(Loan loan) {}

    public Double getAmount() {return amount;}
    public void setAmount(Double amount) {this.amount = amount;}

    public int getPayments() {return payments;}
    public void setPayments(int payments) {this.payments = payments;}

}