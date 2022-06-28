package com.mindhub.Homebanking.Controllers;

import com.mindhub.Homebanking.dtos.LoanApplicationDTO;
import com.mindhub.Homebanking.dtos.LoanDTO;
import com.mindhub.Homebanking.models.*;
import com.mindhub.Homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RequestMapping("/api")
@RestController
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    AccountService accountService;

    @Autowired
    ClientService clientService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    ClientLoanService clientLoanService;

    @GetMapping("/loans")
    public List<LoanDTO> getAll(){

        return loanService.getLoansDTO();

    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createLoan(Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO) {

        Client client = clientService.getClientCurrent(authentication);

        String destination_account_number = loanApplicationDTO.getDestination_account();
        int payments = loanApplicationDTO.getPayments();
        Double amount = loanApplicationDTO.getAmount();

        Loan currentLoan = loanService.getLoanById(loanApplicationDTO.getId());
        Double maxAmount = currentLoan.getMaxAmount();
        Long typeLoan = currentLoan.getId();

        Account currentAccount = accountService.getAccountByNumber(destination_account_number);

        if(client.getLoans().stream().filter(loan -> loan.getId() == loanApplicationDTO.getId()).collect(Collectors.toSet()).size() > 0) {
            return new ResponseEntity<>("The client has already applied for this loan", HttpStatus.FORBIDDEN);
        }

        if (destination_account_number.isEmpty()) {
            return new ResponseEntity<>("Destination account empty", HttpStatus.FORBIDDEN);
        }

        if (amount <= 0 || amount.toString().equals("")) {
            return new ResponseEntity<>("Amount can't be equal or minus to 0", HttpStatus.FORBIDDEN);
        }

        if (payments <= 0) {
            return new ResponseEntity<>("Payments can't be equal or minus to 0", HttpStatus.FORBIDDEN);
        }

        if (!loanService.getIds().contains(typeLoan)) {
            return new ResponseEntity<>("The loan doesn't exist", HttpStatus.FORBIDDEN);
        }

        if (maxAmount < amount) {
            return new ResponseEntity<>("The requested amount exceeds the maximum allowed", HttpStatus.FORBIDDEN);
        }

        if (!currentLoan.getPayments().contains(payments)) {
            return new ResponseEntity<>("Requested quotas are not a valid option", HttpStatus.FORBIDDEN);
        }

        if (accountService.getAccountByNumber(destination_account_number) == null) {
            return new ResponseEntity<>("The account doesn't exist", HttpStatus.FORBIDDEN);
        }

        if (!client.getAccounts().stream().map(Account::getNumber).collect(Collectors.toSet()).contains(destination_account_number)) {
            return new ResponseEntity<>("The client don't have that account", HttpStatus.FORBIDDEN);
        }

        Double amountPercent = amount + (amount * (currentLoan.getPercent()/100));
        currentAccount.setBalance(currentAccount.getBalance() + amount);
        accountService.saveAccount(currentAccount);

        transactionService.saveTransaction(new Transaction(amount, destination_account_number + " " + "Loan aprovved", LocalDateTime.now(),  Type.CREDIT, currentAccount, currentAccount.getBalance()));

        clientLoanService.saveClientLoan(new ClientLoan(amountPercent, payments, currentLoan, client));

        return new ResponseEntity<>("Loan has been complete", HttpStatus.CREATED);
    }

    @PostMapping("/loans/create")
    public ResponseEntity<Object> createNewLoan(Authentication authentication, @RequestBody LoanDTO newLoanDTO) {

        Client client = clientService.getClientCurrent(authentication);

        if(client.getType() != ClientType.ADMIN) {
            return new ResponseEntity<>("Only admins can create loans", HttpStatus.FORBIDDEN);
        }

        if(newLoanDTO.getName().isEmpty()) {
            return new ResponseEntity<>("Name of loan is empty", HttpStatus.FORBIDDEN);
        }
        if(newLoanDTO.getMaxAmount() <= 0) {
            return new ResponseEntity<>("Max amount can't be 0 or minus", HttpStatus.FORBIDDEN);
        }
        if(newLoanDTO.getPayments().size() == 0) {
            return new ResponseEntity<>("Payments can't be empty", HttpStatus.FORBIDDEN);
        }
        if(newLoanDTO.getPercent() <=0) {
            return new ResponseEntity<>("The percent can't be 0 or minus", HttpStatus.FORBIDDEN);
        }

        loanService.saveLoan(new Loan(newLoanDTO.getName(), newLoanDTO.getMaxAmount(), newLoanDTO.getPayments(), newLoanDTO.getPercent()));

        return new ResponseEntity<>("Loan has been created successfully", HttpStatus.CREATED);
    }

}
