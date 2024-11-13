package service;

import java.util.List;

import dao.AccountDao;
import model.Account;
import util.AccountUtils;

public class AccountService {
    AccountDao dao = new AccountDao();

    public Account insert(Account account) {
        if(AccountUtils.totalAccounts(account)) {
            return null;
        }
        return dao.insert(account);
    }

    public Account getAccount(Long id) {
        Account account = dao.getAccount(id);
        if (account == null) {
            System.out.println("Account not found");
            return null;            
        }
        return account;
    }

    public List<Account> listAllByClient(Account account) {
        return dao.listAllByClient(account);
    }

}
