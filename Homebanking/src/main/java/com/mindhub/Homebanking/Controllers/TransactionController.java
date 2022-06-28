package com.mindhub.Homebanking.Controllers;

import com.lowagie.text.DocumentException;
import com.mindhub.Homebanking.dtos.CardPostnetDTO;
import com.mindhub.Homebanking.dtos.PDFParamsDTO;
import com.mindhub.Homebanking.models.*;
import com.mindhub.Homebanking.services.*;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    ClientService clientService;

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    CardService cardService;

    @Autowired
    PDFGeneratorService pdfGeneratorService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> transaction(Authentication authentication,
    @RequestParam Double amount, @RequestParam String description, @RequestParam String source_number, @RequestParam String destination_number) {

        Account accountDebit = accountService.getAccountByNumber(source_number);
        Account accountCredit = accountService.getAccountByNumber(destination_number);

        if (amount <= 0) {
            return new ResponseEntity<>("Amount can't be 0 or minus", HttpStatus.FORBIDDEN);
        }

        if (description.isEmpty()) {
            return new ResponseEntity<>("Description is empty", HttpStatus.FORBIDDEN);
        }

        if (source_number.isEmpty()) {
            return new ResponseEntity<>("Origin account is empty", HttpStatus.FORBIDDEN);
        }

        if (destination_number.isEmpty()) {
            return new ResponseEntity<>("Destination account is empty", HttpStatus.FORBIDDEN);
        }

        if (source_number.equals(destination_number)) {
            return new ResponseEntity<>("The first account's number is the same of second account's number", HttpStatus.FORBIDDEN);
        }

        if (accountDebit == null) {
            return new ResponseEntity<>("The account does not exist", HttpStatus.FORBIDDEN);
        }

        if (!clientService.getClientCurrent(authentication).getAccounts().stream().map(Account::getNumber).collect(Collectors.toSet()).contains(source_number)) {
            return new ResponseEntity<>("The account does not belong", HttpStatus.FORBIDDEN);
        }

        if (accountCredit == null) {
            return new ResponseEntity<>("The account does not exist", HttpStatus.FORBIDDEN);
        }

        if (accountDebit.getBalance() < amount) {
            return new ResponseEntity<>("The account don't have enough amount", HttpStatus.FORBIDDEN);
        }

        Double debitBalance = (accountDebit.getBalance() - amount);
        Double creditBalance = (accountCredit.getBalance() + amount);

        accountDebit.setBalance(debitBalance);
        accountCredit.setBalance(creditBalance);

        accountService.saveAccount(accountDebit);
        accountService.saveAccount(accountCredit);

        transactionService.saveTransaction(new Transaction(amount, description + " " + accountDebit.getNumber(), LocalDateTime.now(), Type.CREDIT, accountCredit, accountCredit.getBalance()));
        transactionService.saveTransaction(new Transaction(amount, description + " " + accountCredit.getNumber(), LocalDateTime.now(), Type.DEBIT, accountDebit, accountDebit.getBalance()));

        return new ResponseEntity<>("Transaction has been complete", HttpStatus.CREATED);

    }

//    @CrossOrigin(origins = "http://localhost:8080", methods= {RequestMethod.GET,RequestMethod.POST})
    @Transactional
    @PostMapping("/transactions/postnet")
    public ResponseEntity<Object> postnet(@RequestBody CardPostnetDTO cardPostnetDTO) {

        Card card = cardService.getCardByNumberString(cardPostnetDTO.getNumber());
        String cardHolder = card.getCardHolder();
        int cvv = card.getCvv();
        Client client = card.getClient();
        LocalDateTime thruDate = card.getFromDate();
        LocalDateTime dateNow = LocalDateTime.now();
        List<Account> accounts = accountService.getAccountByClient(client).stream().filter(Account::isActive).collect(Collectors.toList());
        accounts.sort(Comparator.comparing(Account::getBalance).reversed());

        if(thruDate.isBefore(dateNow)) {
            return new ResponseEntity<>("The card is expired", HttpStatus.FORBIDDEN);
        }

        if(!client.getCards().stream().map(Card::getNumber).collect(Collectors.toSet()).contains(cardPostnetDTO.getNumber())) {
            return new ResponseEntity<>("The card number does not belong a card of client", HttpStatus.FORBIDDEN);
        }

        if(client.getAccounts().stream().map(Account::getBalance).filter(aDouble -> aDouble >= cardPostnetDTO.getAmount()).collect(Collectors.toSet()).size() < 1) {
            return new ResponseEntity<>("No account has sufficient balance", HttpStatus.FORBIDDEN);
        }

        if(cardPostnetDTO.getNumber().isEmpty()) {
            return new ResponseEntity<>("Card number is empty", HttpStatus.FORBIDDEN);
        }

        if(cardPostnetDTO.getDescription().isEmpty()) {
            return new ResponseEntity<>("Description is empty", HttpStatus.FORBIDDEN);
        }

        if(cardPostnetDTO.getCvv() <= 0) {
            return new ResponseEntity<>("Cvv can't be 0 or minus", HttpStatus.FORBIDDEN);
        }

        if(cardPostnetDTO.getAmount() <= 0) {
            return new ResponseEntity<>("Amount can't be 0 or minus", HttpStatus.FORBIDDEN);
        }

        accounts.get(0).setBalance(accounts.get(0).getBalance() - cardPostnetDTO.getAmount());
        accountService.saveAccount(accounts.get(0));

        transactionService.saveTransaction(new Transaction(cardPostnetDTO.getAmount(), cardPostnetDTO.getDescription(), LocalDateTime.now(), Type.DEBIT, accounts.get(0), accounts.get(0).getBalance()));

        return new ResponseEntity<>("Transaction has been complete", HttpStatus.CREATED);
    }

    @PostMapping("/transactions/create")
    public void createPDF(HttpServletResponse response, Authentication authentication, @RequestBody PDFParamsDTO pdfParamsDTO) throws IOException, DocumentException {

        response.setContentType("application/pdf");
        DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd:hh::mm");
        String currentDate = formatDate.format(new Date());

        String headerKey = "Content-disposition";
        String headerValue = "attachment; filename=Home_Gank_" + currentDate + ".pdf";
        response.setHeader(headerKey, headerValue);

        pdfGeneratorService.PDFGenerator(response, authentication, pdfParamsDTO);

    }
}
