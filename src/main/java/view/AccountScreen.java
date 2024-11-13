package view;

import java.util.Date;

import controller.AccountController;
import controller.ClientController;
import model.Account;
import model.enums.AccountType;

public class AccountScreen {
    public static void main(String[] args) {
        AccountController controller = new AccountController();
        ClientController clientController = new ClientController();
        Account account = new Account();

        account.setClient(clientController.getClient((long) 1));
        account.setAccountType(AccountType.CHECKING_ACCOUNT);
        account.setOpenDate(new Date());

        controller.insert(account);
    }
}