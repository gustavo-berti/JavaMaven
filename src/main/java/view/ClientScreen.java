package view;

import java.util.Scanner;

import controller.ClientController;
import model.Client;

public class ClientScreen{
    public static void main(String[] args) {
        ClientController controller = new ClientController();
        Client client = new Client();
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the client name: ");
        client.setName(scan.nextLine());
        System.out.println("Enter the client cpf: ");
        client.setCpf(scan.nextLine());
        scan.close();
        controller.insert(client);
    }
}