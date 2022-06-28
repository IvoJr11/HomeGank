package com.mindhub.Homebanking.services.implement;

import com.mindhub.Homebanking.models.ClientLoan;
import com.mindhub.Homebanking.repositories.ClientLoanRepository;
import com.mindhub.Homebanking.services.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanServiceImpl implements ClientLoanService {

    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Override
    public ClientLoan saveClientLoan(ClientLoan clientLoan) {
        return clientLoanRepository.save(clientLoan);
    }
}
