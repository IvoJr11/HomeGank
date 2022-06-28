package com.mindhub.Homebanking.Controllers;

import com.mindhub.Homebanking.dtos.AccountDTO;
import com.mindhub.Homebanking.models.Account;
import com.mindhub.Homebanking.models.AccountType;
import com.mindhub.Homebanking.models.CardType;
import com.mindhub.Homebanking.models.Client;
import com.mindhub.Homebanking.services.AccountService;
import com.mindhub.Homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.Homebanking.Utils.Utils.getRandomNumber;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAll(){
        return accountService.getAccountsDTO();
    }

    @GetMapping("accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){

        return accountService.getAccountDTO(id);

    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication, @RequestParam AccountType type) {

        Client client = clientService.getClientCurrent(authentication);
        List <Account> accounts = accountService.getAccountByClient(client).stream().filter(Account::isActive).collect(Collectors.toList());

        if(type == null) {
            return new ResponseEntity<>("Account type is empty", HttpStatus.FORBIDDEN);
        }

        if (accounts.size()>= 3) {
            return new ResponseEntity<>("The client can't have more of 3 accounts", HttpStatus.FORBIDDEN);
        }

        accountService.saveAccount(new Account("VIN-" + getRandomNumber(0, 99999999), 0.0, LocalDateTime.now(), client, true, type));
        return new ResponseEntity<>("Account created", HttpStatus.CREATED);
    }

    @PatchMapping("/clients/current/accounts")
    public ResponseEntity<Object> desactiveAccount(Authentication authentication, @RequestParam long id) {

        Client client = clientService.getClientCurrent(authentication);

        Account account = accountService.getAccountById(id);

        if(account == null) {
            return new ResponseEntity<>("The account doesn't exist",HttpStatus.FORBIDDEN);
        }

        if(!client.getAccounts().contains(account)) {
            return new ResponseEntity<>("The account does not belong to the client", HttpStatus.FORBIDDEN);
        }

        if(account.getBalance() > 0) {
            return new ResponseEntity<>("The account has money, you can't eliminate it", HttpStatus.FORBIDDEN);
        }
        account.setActive(false);

        accountService.saveAccount(account);

        return new ResponseEntity<>("The account has been eliminated", HttpStatus.CREATED);
    }
}
