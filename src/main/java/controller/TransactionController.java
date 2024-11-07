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

	public List<Transaction> balanceInquiryMonth(String cpf, String month, String year) {
		return service.balanceInquiryMonth(cpf, month, year);
	}

	public List<Transaction> balanceInquiryPeriod(String cpf, String startDate, String endDate) {
		return service.balanceInquiryPeriod(cpf, startDate, endDate);
	}

}
