package view;

import java.util.Date;
import java.util.Scanner;

import controller.AccountController;
import controller.ClientController;
import model.Account;
import model.enums.AccountType;

public class AccountScreen {
    public static void main(String[] args) {
        AccountController controller = new AccountController();
        ClientController clientController = new ClientController();
        Scanner scan = new Scanner(System.in);
        Account account = new Account();

        System.out.println("Enter the client cpf: ");
        account.setClient(clientController.getClientByCpf(scan.nextLine()));
        System.out.println("1. Saving Account\n2. Checking Account");
        int type = scan.nextInt();
        switch (type) {
            case 1:
                account.setAccountType(AccountType.SAVING_ACCOUNT);                
                break;
            
            case 2:
                account.setAccountType(AccountType.CHECKING_ACCOUNT);
                break;

            default:
                break;
        }
        account.setOpenDate(new Date());

        controller.insert(account);
        scan.close();
    }
}