package util;

import java.util.List;

import controller.AccountController;
import model.Account;

public class AccountUtils {
    public static AccountController accountController = new AccountController();

    public static boolean totalAccounts(Account account) {
        List<Account> accounts = accountController.listAllByClient(account);
        if(accounts.size() >= 3){
            System.out.println("Client reached the maximum number of accounts");
            return true;
        }
        return false;
    }
    
}