package service;

import java.util.Date;
import java.util.List;

import dao.GenericDao;
import dao.TransactionDao;
import model.Account;
import model.Transaction;
import util.TransactionUtils;

public class TransactionService implements BasicService<Transaction> {
	TransactionDao dao = new TransactionDao();

	@Override
	public Transaction insert(Transaction transaction) {
		if (TransactionUtils.transactionValid(transaction))
			return null;
		transaction.setDescription(transaction.getTransactionType() + " Operation");
		transaction.setTransactionDate(new Date());
		return dao.insert(transaction);
	}

	public Transaction insertCashback(Transaction cashback) {
		return dao.insert(cashback);
	}

	public List<Transaction> listAllById(Long id) {
		return dao.listAllById(id);
	}

	public List<Transaction> listAllByTransactionType(Transaction transaction) {
		return dao.listAllByTransactionType(transaction.getTransactionType());
	}

	public List<Transaction> balanceInquiryMonth(Long id, String month, String year) {
		String dateStr = year + "-" + month;
		return dao.balanceInquiryMonth(id, dateStr);
	}

	public List<Transaction> balanceInquiryPeriod(Long id, String startDate, String endDate) {
		return dao.balanceInquiryPeriod(id, startDate, endDate);
	}

	public double getAverangeBalancePeriod(Long id, String startDate, String endDate) {
		List<Transaction> transactions = dao.balanceInquiryPeriod(id, startDate, endDate);
		double total = 0;
		for (Transaction transaction : transactions) {
			total += transaction.getOperationValue();
		}
		return total / transactions.size();
	}

	public double getBalance(Account account) {
		List<Transaction> transactions = dao.listAllById(account.getId());
		return TransactionUtils.getBalance(transactions);
	}

	@Override
	public GenericDao<Transaction> getDao() {
		return dao;
	}

}
