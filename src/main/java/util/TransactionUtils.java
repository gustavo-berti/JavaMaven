package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
		if (TransactionUtils.isFraud(transactions, transaction)) {
			System.out.println("Fraud detected");
			return true;
		}
		if (TransactionUtils.getBalance(transactions) - transaction.getOperationValue() < 100
				&& !transaction.getTransactionType().equals(TransactionType.DEPOSIT)) {
			System.out.println("Balance less than 100");
		}
		if (transaction.getTransactionType().equals(TransactionType.DEBIT_CARD)) {
			TransactionUtils.cashback(transaction);
		}
		TransactionUtils.transactionFee(transaction);
		return false;
	}

	private static boolean isFraud(List<Transaction> transactions, Transaction transaction) {
		double total = 0;
		int count = 0;
		for (Transaction t : transactions) {
			if (!t.getTransactionType().equals(TransactionType.DEPOSIT)) {
				total += t.getOperationValue();
				count++;
			}
		}
		double average = total / count;
		if (average + 1000 < transaction.getOperationValue()) {
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

	public static void cashback(Transaction transaction) {
		Date startOfNextMonth = getStartOfNextMonth(transaction.getTransactionDate());
		double cashbackValue = transaction.getOperationValue() * 0.002;
		Transaction cashback = transaction;
		cashback.setTransactionType(TransactionType.CASHBACK);		
		cashback.setDescription(cashback.getTransactionType() + " Operation");
	
		List<Transaction> transactions = service.listAllByTransactionType(cashback);
	
		for (Transaction t : transactions) {
			if (isWithinRange(t.getTransactionDate(), transaction.getTransactionDate(), startOfNextMonth)) {
				cashback.setOperationValue(t.getOperationValue() + cashbackValue);
				cashback.setId(t.getId());
				cashback.setTransactionDate(startOfNextMonth);
				service.update(cashback);
				return;
			}
		}
	
		cashback.setOperationValue(cashbackValue);
		cashback.setTransactionDate(startOfNextMonth);
		service.insertCashback(cashback);
	}
	
	private static Date getStartOfNextMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	
	private static boolean isWithinRange(Date dateToCheck, Date startDate, Date endDate) {
		return dateToCheck.after(startDate) && dateToCheck.before(endDate);
	}

}
