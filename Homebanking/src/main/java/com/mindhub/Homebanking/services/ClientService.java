package com.mindhub.Homebanking.services;

import com.mindhub.Homebanking.dtos.ClientDTO;
import com.mindhub.Homebanking.models.Client;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClientService {

    List<ClientDTO> getClientsDTO();
    Client saveClient(Client client);
    Client getClientCurrent(Authentication authentication);
    ClientDTO getClientDTO(long id);
    Client getClientByEmail(String email);
}
