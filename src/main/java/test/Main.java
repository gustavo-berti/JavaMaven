package test;

import java.util.Date;

import controller.ClientController;
import dao.AccountDao;
import model.Account;
import model.enums.AccountType;

public class Main {
	public static void main(String[] args) {
		ClientController clientController = new ClientController();
		AccountDao dao = new AccountDao();
		Account account = new Account();

		account.setClient(clientController.getClient((long) 1));
        account.setAccountType(AccountType.CHECKING_ACCOUNT);
        account.setOpenDate(new Date());

		dao.listAllByClient(account);
	}
}
