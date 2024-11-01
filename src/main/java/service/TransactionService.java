package service;

import java.util.Date;

import dao.TransactionDao;
import model.Transaction;
import util.TransactionUtils;

public class TransactionService {
	TransactionDao dao = new TransactionDao();

	public Transaction insert(Transaction transaction) {
		if (!TransactionUtils.validateCpf(transaction.getAccountHolderCpf())) {
			System.out.println("CPF Invalid");
			return null;
		}
		if (transaction.getOperationType().equals("Outflow") && transaction.getBalance() - transaction.getOperationValue() < 0) {
			System.out.println("Balance not sufficient");
			return null;
		}
		if (transaction.getTransactionType().equals("PIX") && transaction.getOperationValue() > 300) {
			System.out.println("PIX values exceeded the limit");
			return null;
		}
		transaction.setDescription(transaction.getOperationType() + ": " + transaction.getOperationValue() + "\n"
				+ transaction.getTransactionType() + " Operation");

		transaction.setTransactionDate(new Date());
		return dao.insert(transaction);
	}
}
