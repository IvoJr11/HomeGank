package com.mindhub.Homebanking.dtos;

import com.mindhub.Homebanking.models.ClientLoan;
import com.mindhub.Homebanking.models.Transaction;

public class LoanApplicationDTO {

    private long id;

    private Double amount;

    private int payments;

    private String destination_account;

    public LoanApplicationDTO() {};

    public LoanApplicationDTO (long id, Double amount, int payments, String destination_account) {

        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.destination_account = destination_account;

    }

    public long getId() {return id;}

    public Double getAmount() {return amount;}

    public int getPayments() {return payments;}

    public String getDestination_account() {return destination_account;}
}
