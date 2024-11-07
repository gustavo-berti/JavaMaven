package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.Transaction;
import service.TransactionService;

public class TransactionUtils {
	private static TransactionService service = new TransactionService();

	public static boolean transactionValid(Transaction transaction) {
		List<Transaction> transactions = service.listAllByCpf(transaction.getAccount().getClient().getCpf());
		if (!TransactionUtils.validateCpf(transaction.getAccount().getClient().getCpf())) {
			System.out.println("CPF Invalid");
			return true;
		}
		if (TransactionUtils.confirmBalance(transaction, transactions)) {
			System.out.println("Balance not sufficient");
			return true;
		}
		if (transaction.getTransactionType().equals("PIX") && transaction.getOperationValue() > 300) {
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
		if (TransactionUtils.getBalance(transactions)-transaction.getOperationValue() < 100 && !transaction.getTransactionType().equals("Deposit")) {
			System.out.println("Balance less than 100");
		}
		if (TransactionUtils.isFraud(transactions)) {
			System.out.println("Fraud detected");
			return true;
		}
		TransactionUtils.transactionFee(transaction);
		return false;
	}

	private static boolean isFraud(List<Transaction> transactions){
		double total = 0;
		int count = 0;
		for (Transaction transaction : transactions) {
			if (!transaction.getTransactionType().equals("Deposit")) {
				total += transaction.getOperationValue();
				count++;
			}
		}
		if(total+1000 > total/count && count != 0){
			return false;
		}
		return true;
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
		if(operationCount >= 10) {
			return true;
		}
		return false;
	}

	private static boolean timePIX(Transaction transaction) {
		if (transaction.getTransactionType().equals("PIX")) {
			DateFormat formatter = new SimpleDateFormat("HH:mm");
			String hour = formatter.format(transaction.getTransactionDate());
			if (hour.compareTo("06:00") < 0 || hour.compareTo("22:00") > 0) {
				return true;
			}
		}
		return false;
	}

	private static void transactionFee(Transaction transaction) {
		if (transaction.getTransactionType().equals("Withdrawal")) {
			transaction.setOperationValue(transaction.getOperationValue() + 2);
			System.out.println("Withdrawal fee: 2\nNew value: " + transaction.getOperationValue());
		}
		if (transaction.getTransactionType().equals("Payment")) {
			transaction.setOperationValue(transaction.getOperationValue() + 5);
			System.out.println("Payment fee: 5\nNew value: " + transaction.getOperationValue());
		}
	}

	private static boolean withdrawalLimit(Transaction transaction, List<Transaction> transactions) {
		if (!transaction.getTransactionType().equals("Withdrawal")) {
			return false;
		}
		double totalWithdrawal = 0 + transaction.getOperationValue();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date day = null;
		try {
			day = formatter.parse(formatter.format(transaction.getTransactionDate()));
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
			if (t.getTransactionType().equals("Withdrawal")) {
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

	private static boolean confirmBalance(Transaction transaction, List<Transaction> transactions) {
		if (transaction.getTransactionType().equals("Deposit"))
			return false;
		double balance = getBalance(transactions);
		if (balance - transaction.getOperationValue() < 0) {
			return true;
		}
		return false;
	}

	private static boolean validateCpf(String cpf) {
		cpf = cpf.replace("-", "").replace(".", "");
		int[] intCpf = new int[cpf.length()];
		for (int i = 0; i < cpf.length(); i++) {
			intCpf[i] = cpf.charAt(i) - '0';
		}
		if (validateDigit1(intCpf)) {
			return false;
		}
		if (validateDigit2(intCpf)) {
			return false;
		}
		return true;

	}

	private static boolean validateDigit1(int[] intCpf) {
		int aux = 0;
		for (int i = 1; i < intCpf.length - 1; i++) {
			aux += intCpf[i - 1] * i;
		}
		aux = aux % 11;
		if (aux >= 10) {
			aux = 0;
		}
		if (intCpf[9] != aux) {
			return true;
		}
		return false;
	}

	private static boolean validateDigit2(int[] intCpf) {
		int aux = 0;
		for (int i = 0; i < intCpf.length - 1; i++) {
			aux += intCpf[i] * i;
		}
		aux = aux % 11;
		if (aux >= 10) {
			aux = 0;
		}
		if (intCpf[10] != aux) {
			return true;
		}
		return false;
	}

	private static double getBalance(List<Transaction> transactions) {
		double balance = 0;
		for (Transaction transaction : transactions) {
			if (transaction.getTransactionType().equals("Deposit")) {
				balance += transaction.getOperationValue();
			} else {
				balance -= transaction.getOperationValue();
			}
		}
		return balance;
	}

}
