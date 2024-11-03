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
		List<Transaction> transactions = service.listAllByCpf(transaction.getAccountHolderCpf());
		if (!TransactionUtils.validateCpf(transaction.getAccountHolderCpf())) {
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
		return false;
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
