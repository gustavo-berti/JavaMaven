package controller;

import model.Client;
import service.ClientService;

public class ClientController {

    ClientService clientService = new ClientService();

    public Client insert(Client client) {
        return clientService.insert(client);
    }

    public Client getClient(Long id) {
        return clientService.getClient(id);
    }

    public Client getClientByCpf(String nextLine) {
        return clientService.getClientByCpf(nextLine);
    }

}
