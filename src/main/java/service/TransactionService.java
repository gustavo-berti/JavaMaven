package service;

import java.util.Date;
import java.util.List;

import dao.TransactionDao;
import model.Transaction;
import util.TransactionUtils;

public class TransactionService {
	TransactionDao dao = new TransactionDao();

	public Transaction insert(Transaction transaction) {
		if (TransactionUtils.transactionValid(transaction)) {
			return null;
		}
		transaction.setDescription(transaction.getTransactionType() + " Operation");
		transaction.setTransactionDate(new Date());
		return dao.insert(transaction);
	}

	public List<Transaction> listAllByCpf(String transactionType) {
		return dao.listAllByCpf(transactionType);
	}

}
