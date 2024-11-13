package controller;

import java.util.Date;
import java.util.List;

import model.Transaction;
import service.TransactionService;

public class TransactionController {
	
	TransactionService service = new TransactionService();
	
	public Transaction insert(Transaction transaction) {
		return service.insert(transaction);
	}

	public List<Transaction> balanceInquiryMonth(Long id, String month, String year) {
		return service.balanceInquiryMonth(id, month, year);
	}

	public List<Transaction> balanceInquiryPeriod(Long id, String startDate, String endDate) {
		return service.balanceInquiryPeriod(id, startDate, endDate);
	}

}
