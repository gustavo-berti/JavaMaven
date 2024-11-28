package controller;

import model.Client;
import service.BasicService;
import service.ClientService;

public class ClientController implements BasicController<Client>{

    ClientService service = new ClientService();

    public Client insert(Client client) {
        return service.insert(client);
    }

    public Client getClient(Long id) {
        return service.getClient(id);
    }

    public Client getClientByCpf(String nextLine) {
        return service.getClientByCpf(nextLine);
    }

    @Override
    public BasicService<Client> getService() {
        return service;
    }

}
