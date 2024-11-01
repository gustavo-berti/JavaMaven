package controller;

import model.Transaction;
import service.TransactionService;

public class TransactionController {
	
	TransactionService service = new TransactionService();
	
	public Transaction insert(Transaction transaction) {
		return service.insert(transaction);
	}

}
