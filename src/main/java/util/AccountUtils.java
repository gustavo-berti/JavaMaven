package util;

import java.util.List;

import controller.AccountController;
import controller.TransactionController;
import model.Account;

public class AccountUtils {
    public static final double COMPOUND_INTEREST = 0.03;
    public static AccountController accountController = new AccountController();

    public static boolean totalAccounts(Account account) {
        List<Account> accounts = accountController.listAllByClient(account);
        if(accounts.size() >= 3){
            System.out.println("Client reached the maximum number of accounts");
            return true;
        }
        return false;
    }

    public static Double calculateCompoundInterest(Account account, int months) {
        TransactionController transactionController = new TransactionController();
        double income = transactionController.getBalance(account)*(Math.pow((1+COMPOUND_INTEREST), months));      
        return income;
    }
    
}