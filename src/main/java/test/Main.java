package test;

import java.util.Date;

import controller.AccountController;
import controller.ClientController;
import dao.AccountDao;
import dao.TransactionDao;
import model.Account;
import model.Transaction;
import model.enums.AccountType;
import model.enums.TransactionType;
import util.TransactionUtils;

public class Main {
	public static void main(String[] args) {
		AccountController accountController = new AccountController();
		Transaction transaction = new Transaction();
		transaction.setTransactionDate(new Date());
		transaction.setOperationValue(50.0);
		transaction.setTransactionType(TransactionType.DEBIT_CARD);
		transaction.setAccount(accountController.getAccount((long) 1));

		TransactionUtils.cashback(transaction);

	}
}
