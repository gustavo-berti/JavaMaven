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
		int type = 3;
		switch (type) {
			case 1:
				transaction.setTransactionType("Deposit");
				break;

			case 2:
				transaction.setTransactionType("Withdrawal");
				break;
			
			case 3:	
				transaction.setTransactionType("Payment");
				break;

			case 4:
				transaction.setTransactionType("PIX");
				break;

			default:
				break;
		}
		transaction.setOperationValue(48.0);
		
		controller.insert(transaction);
	}

}
