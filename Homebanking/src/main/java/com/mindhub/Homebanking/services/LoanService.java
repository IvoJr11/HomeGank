package com.mindhub.Homebanking.services;

import com.mindhub.Homebanking.dtos.LoanDTO;
import com.mindhub.Homebanking.models.Loan;

import java.util.List;
import java.util.Set;

public interface LoanService {

    List<LoanDTO> getLoansDTO();
    Loan getLoanById(long id);
    Set<Long> getIds();
    Loan saveLoan(Loan loan);
}
