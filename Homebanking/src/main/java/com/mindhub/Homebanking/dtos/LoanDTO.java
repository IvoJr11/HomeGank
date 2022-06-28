package com.mindhub.Homebanking.dtos;

import com.mindhub.Homebanking.models.Loan;

import java.util.ArrayList;
import java.util.List;

public class LoanDTO {

    private long id;

    private String name;

    private Double maxAmount;

    private List<Integer> payments = new ArrayList<>();
    private Double percent;

    public LoanDTO () {};

    public LoanDTO (Loan loan) {

        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
        this.percent = loan.getPercent();

    }

    public long getId() {return id;}

    public String getName() {return name;}

    public Double getMaxAmount() {return maxAmount;}

    public List<Integer> getPayments() {return payments;}

    public Double getPercent() {return percent;}
}
