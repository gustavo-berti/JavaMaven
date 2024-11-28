package controller;

import java.util.List;

import model.Account;
import model.Transaction;
import service.BasicService;
import service.TransactionService;

public class TransactionController implements BasicController<Transaction>{
	
	TransactionService service = new TransactionService();

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

	@Override
	public BasicService<Transaction> getService() {
		return service;
	}

}
