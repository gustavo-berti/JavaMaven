package controller;

import java.util.List;

import model.Account;
import service.AccountService;

public class AccountController {

    AccountService accountService = new AccountService();

    public Account insert(Account account) {
        return accountService.insert(account);
    }

    public Account getAccount(Long id) {
        return accountService.getAccount(id);
    }

    public List<Account> listAllByClient(Account account) {
        return accountService.listAllByClient(account);
    }

}
