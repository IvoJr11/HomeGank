package com.mindhub.Homebanking.dtos;

import com.mindhub.Homebanking.models.ClientLoan;

public class ClientLoanDTO {

    private long id;
    private long id_loan;
    private String name;
    private Double amount;
    private int payment;

    public ClientLoanDTO () {};

    public ClientLoanDTO (ClientLoan clientLoan) {

        this.id = clientLoan.getId();
        this.id_loan = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payment = clientLoan.getPayments();

    }

    public long getId() {return id;}

    public long getId_loan() {return id_loan;}

    public String getName() {return name;}

    public Double getAmount() {return amount;}

    public int getPayment() {return payment;}
}
