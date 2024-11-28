package controller;

import java.util.List;

import model.Account;
import service.AccountService;
import service.BasicService;

public class AccountController implements BasicController<Account>{

    AccountService service = new AccountService();

    public Account getAccount(Long id) {
        return service.getAccount(id);
    }

    public List<Account> listAllByClient(Account account) {
        return service.listAllByClient(account);
    }

    public Double calculateApprovedLimit(Account account) {
        return service.calculateApprovedLimit(account);
    }

    public Double monthlyIncome(Account account, int months) {
        return service.monthlyIncome(account, months);
    }

    @Override
    public BasicService<Account> getService() {
        return service;
    }

}
