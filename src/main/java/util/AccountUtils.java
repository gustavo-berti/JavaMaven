package util;

import java.util.List;

import model.Account;
import service.AccountService;
import service.TransactionService;

public class AccountUtils {
    public static final double COMPOUND_INTEREST = 0.03;
    public static AccountService accountService = new AccountService();

    public static boolean totalAccounts(Account account) {
        List<Account> accounts = accountService.listAllByClient(account);
        if(accounts.size() >= 3){
            System.out.println("Client reached the maximum number of accounts");
            return true;
        }
        return false;
    }

    public static Double calculateCompoundInterest(Account account, int months) {
        TransactionService transactionService = new TransactionService();
        double income = transactionService.getBalance(account)*(Math.pow((1+COMPOUND_INTEREST), months));      
        return income;
    }
    
}