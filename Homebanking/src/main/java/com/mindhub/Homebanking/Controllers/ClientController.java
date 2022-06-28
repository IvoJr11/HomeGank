package com.mindhub.Homebanking.Controllers;

import com.mindhub.Homebanking.dtos.ClientDTO;
import com.mindhub.Homebanking.models.Account;
import com.mindhub.Homebanking.models.AccountType;
import com.mindhub.Homebanking.models.Client;
import com.mindhub.Homebanking.models.ClientType;
import com.mindhub.Homebanking.services.AccountService;
import com.mindhub.Homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.Homebanking.Utils.Utils.getRandomNumber;


@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/clients")
    public List<ClientDTO> getAll(){

        return clientService.getClientsDTO();

    }

    @PostMapping("/clients")
    public ResponseEntity<Object> register(

            @RequestParam String firstName, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }

        if (clientService.getClientByEmail(email) !=  null) {

            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);

        }

        Client client = clientService.saveClient(new Client(firstName, lastName, email, passwordEncoder.encode(password), ClientType.CLIENT));
        accountService.saveAccount(new Account("VIN-" + getRandomNumber(10000000, 99999999), 0.0, LocalDateTime.now(), client, true, AccountType.SAVING));

        return new ResponseEntity<>("The Client has been created", HttpStatus.CREATED);

    }

    @GetMapping("clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){

        return clientService.getClientDTO(id);

    }

    @GetMapping("/clients/current")
    public ClientDTO getCurrent(Authentication authentication) {

        return new ClientDTO(clientService.getClientCurrent(authentication));

    }

}
