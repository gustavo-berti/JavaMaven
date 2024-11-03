package test;

import java.util.Date;
import java.util.List;

import model.Transaction;
import service.TransactionService;
import util.TransactionUtils;

public class Main {
    public static void main(String[] args) {
        Transaction transaction = new Transaction();
        TransactionService service = new TransactionService();
		
		transaction.setAccountHolderCpf("123.456.789-09");
		transaction.setAccountHolderName("Gustavo");
		transaction.setTransactionDate(new Date());
		transaction.setTransactionType("Withdrawal");
		transaction.setOperationValue(200.0);

    }
}
