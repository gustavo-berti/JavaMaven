package view;

import controller.ClientController;
import model.Client;

public class ClientScreen{
    public static void main(String[] args) {
        ClientController controller = new ClientController();
        Client client = new Client();

        client.setName("Gustavo");
        client.setCpf("123.456.789-09");
        
        controller.insert(client);
    }
}