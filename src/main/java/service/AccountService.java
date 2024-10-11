package service;

import java.util.Date;

import dao.AccountDao;
import model.Account;

public class AccountService {
	AccountDao dao = new AccountDao();
	
	public Account insert(Account account) {
		account.setDescription("Operação de "+account.getTransactionType());
		account.setTransactionDate(new Date());
		return dao.insert(account);
		}
}
