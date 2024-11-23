package service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dao.AccountDao;
import model.Account;
import model.enums.AccountType;
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

    public Double calculateApprovedLimit(Account account) {
        TransactionService transactionService = new TransactionService();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String endDate = formatter.format(new Date()).toString();
        Date date = new Date();
        date.setMonth(date.getMonth() - 3);
        String startDate = formatter.format(date).toString();
        double limit = transactionService.getAverangeBalancePeriod(account.getId(), startDate, endDate);
        account.setApprovedLimit(limit);
        return limit;
    }

    public Double monthlyIncome(Account account, int months) {
        if (account.getAccountType().equals(AccountType.CHECKING_ACCOUNT)) {
            double income = AccountUtils.calculateCompoundInterest(account, months);
            return income;
        }
        return null;
    }

}
