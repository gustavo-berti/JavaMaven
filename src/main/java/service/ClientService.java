package service;

import dao.ClientDao;
import model.Client;
import util.ClientUtils;
public class ClientService {
    ClientDao dao = new ClientDao();

    public Client insert(Client client) {
        if(!ClientUtils.validateCpf(client.getCpf())) {
            System.out.println("Invalid CPF");
            return null;
        }
        return dao.insert(client);
    }

    public Client getClient(Long id) {
        Client client = dao.getClient(id);
        if (client == null) {
            System.out.println("Client not found");
            return null;            
        }
        return client;
    }

    public Client getClientByCpf(String nextLine) {
        return dao.getClientByCpf(nextLine);
    }

}
