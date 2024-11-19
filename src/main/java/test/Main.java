package test;

import java.util.Date;

import controller.AccountController;
import controller.ClientController;
import dao.AccountDao;
import dao.TransactionDao;
import model.Account;
import model.enums.AccountType;

public class Main {
	public static void main(String[] args) {
		ClientController clientController = new ClientController();
		AccountController accountController = new AccountController();
		AccountDao dao = new AccountDao();
		TransactionDao transactionDao = new TransactionDao();
		Account account = new Account();
		long id = 1;

		account = dao.getAccount(id);
		double income = accountController.monthlyIncome(account,2);

		System.out.println("Monstly income: " + income);
	}
}
