package view;

import java.util.Date;

import controller.TransactionController;
import model.Transaction;

public class TransactionScreen {

	public static void main(String[] args) {
		TransactionController controller = new TransactionController();
		Transaction transaction = new Transaction();
		
		transaction.setAccountHolderCpf("123.456.789-09");
		transaction.setAccountHolderName("Gustavo");
		transaction.setTransactionDate(new Date());
		transaction.setTransactionType("PIX");
		transaction.setOperationType("Outflow");
		transaction.setOperationValue(300.0);
		transaction.setBalance(500.0);
		
		controller.insert(transaction);
	}

}
