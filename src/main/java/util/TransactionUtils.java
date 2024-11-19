package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.Transaction;
import model.enums.TransactionType;
import service.TransactionService;

public class TransactionUtils {
	private static TransactionService service = new TransactionService();

	public static boolean transactionValid(Transaction transaction) {
		List<Transaction> transactions = service.listAllById(transaction.getAccount().getId());
		if (TransactionUtils.confirmBalance(transaction, transactions)) {
			System.out.println("Balance not sufficient");
			return true;
		}
		if (transaction.getTransactionType().equals(TransactionType.PIX) && transaction.getOperationValue() > 300) {
			System.out.println("PIX values exceeded the limit");
			return true;
		}
		if (TransactionUtils.withdrawalLimit(transaction, transactions)) {
			System.out.println("Withdrawal limit exceeded");
			return true;
		}
		if (TransactionUtils.timePIX(transaction)) {
			System.out.println("PIX not available at this time");
			return true;
		}
		if (TransactionUtils.operationLimit(transactions)) {
			System.out.println("Daily operation limit exceeded");
			return true;
		}
		if (TransactionUtils.isFraud(transactions)) {
			System.out.println("Fraud detected");
			return true;
		}
		if (TransactionUtils.getBalance(transactions) - transaction.getOperationValue() < 100
				&& !transaction.getTransactionType().equals(TransactionType.DEPOSIT)) {
			System.out.println("Balance less than 100");
		}
		TransactionUtils.transactionFee(transaction);
		return false;
	}

	private static boolean isFraud(List<Transaction> transactions) {
		double total = 0;
		int count = 0;
		for (Transaction transaction : transactions) {
			if (!transaction.getTransactionType().equals(TransactionType.DEPOSIT)) {
				total += transaction.getOperationValue();
				count++;
			}
		}
		if ((total + 1000) > (total / count) && count != 0) {
			return true;
		}
		return false;
	}

	private static boolean operationLimit(List<Transaction> transactions) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date day = null;
		int operationCount = 0;

		try {
			day = formatter.parse(formatter.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for (Transaction t : transactions) {
			Date dayT = null;
			try {
				dayT = formatter.parse(formatter.format(t.getTransactionDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (dayT.equals(day)) {
				operationCount++;
			}
		}
		if (operationCount >= 10) {
			return true;
		}
		return false;
	}

	private static boolean timePIX(Transaction transaction) {
		if (transaction.getTransactionType().equals(TransactionType.PIX)) {
			DateFormat formatter = new SimpleDateFormat("HH:mm");
			String hour = formatter.format(transaction.getTransactionDate());
			if (hour.compareTo("06:00") < 0 || hour.compareTo("22:00") > 0) {
				return true;
			}
		}
		return false;
	}

	private static void transactionFee(Transaction transaction) {
		if (transaction.getTransactionType().equals(TransactionType.WITHDRAWAL)) {
			transaction.setOperationValue(transaction.getOperationValue() + 2);
			System.out.println("Withdrawal fee: 2\nNew value: " + transaction.getOperationValue());
		}
		if (transaction.getTransactionType().equals(TransactionType.PAYMENT)) {
			transaction.setOperationValue(transaction.getOperationValue() + 5);
			System.out.println("Payment fee: 5\nNew value: " + transaction.getOperationValue());
		}
	}

	private static boolean withdrawalLimit(Transaction transaction, List<Transaction> transactions) {
		if (!transaction.getTransactionType().equals(TransactionType.WITHDRAWAL)) 
			return false;
		
		double totalWithdrawal = 0 + transaction.getOperationValue();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date day = null;
		for (Transaction t : transactions) {
			Date dayT = null;
			try {
				day = formatter.parse(formatter.format(transaction.getTransactionDate()));
				dayT = formatter.parse(formatter.format(t.getTransactionDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (t.getTransactionType().equals(TransactionType.WITHDRAWAL)) {
				if (dayT.equals(day)) {
					totalWithdrawal += t.getOperationValue();
				}
			}
		}
		if (totalWithdrawal > 5000) {
			return true;
		}
		return false;
	}

	public static double getBalance(List<Transaction> transactions) {
		double balance = 0;
		for (Transaction transaction : transactions) {
			if (transaction.getTransactionType().equals(TransactionType.DEPOSIT)) {
				balance += transaction.getOperationValue();
			} else {
				balance -= transaction.getOperationValue();
			}
		}
		return balance;
	}

	private static boolean confirmBalance(Transaction transaction, List<Transaction> transactions) {
		if (!transaction.getTransactionType().equals(TransactionType.DEPOSIT)) {
			double balance = TransactionUtils.getBalance(transactions);
			if (balance - transaction.getOperationValue() < 0) {
				return true;
			}
		}
		return false;
	}

	private static void cashback(Transaction transaction) {
		if (transaction.getTransactionType().equals(TransactionType.DEBIT_CARD)) {
						
		}
	}

}
