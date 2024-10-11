package controller;

import model.Account;
import service.AccountService;

public class AccountController {
	
	AccountService service = new AccountService();
	
	public Account insert(Account account) {
		return service.insert(account);
	}

}
