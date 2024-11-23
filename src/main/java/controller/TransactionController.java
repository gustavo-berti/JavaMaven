package controller;

import java.util.List;

import model.Account;
import model.Transaction;
import service.TransactionService;

public class TransactionController {
	
	TransactionService service = new TransactionService();
	
	public Transaction insert(Transaction transaction) {
		return service.insert(transaction);
	}

	public Transaction update(Transaction transaction) {
		return service.update(transaction);
	}

	public List<Transaction> balanceInquiryMonth(Long id, String month, String year) {
		return service.balanceInquiryMonth(id, month, year);
	}

	public List<Transaction> balanceInquiryPeriod(Long id, String startDate, String endDate) {
		return service.balanceInquiryPeriod(id, startDate, endDate);
	}

	public double getAverangeBalancePeriod(Long id, String startDate, String endDate) {
		return service.getAverangeBalancePeriod(id, startDate, endDate);
	}

    public double getBalance(Account account) {
        return service.getBalance(account);
    }

	public List<Transaction> listAllById(Long id) {
		return service.listAllById(id);
	}

	public List<Transaction> listAllByTransactionType(Transaction transaction) {
		return service.listAllByTransactionType(transaction);
	}

	public Transaction insertCashback(Transaction cashback) {
		return service.insertCashback(cashback);
	}

}
