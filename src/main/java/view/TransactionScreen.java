package view;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import controller.AccountController;
import controller.TransactionController;
import model.Account;
import model.Transaction;
import model.enums.TransactionType;
import service.AccountService;

public class TransactionScreen {

	public static void main(String[] args) {

		TransactionController controller = new TransactionController();
		AccountController accountController = new AccountController();
		Transaction transaction = new Transaction();
		Scanner scan = new Scanner(System.in);

		System.out.println("Enter the account id");
		transaction.setAccount(accountController.getAccount(scan.nextLong()));
		scan.nextLine();
		System.out.println("1. Insert transaction\n2. Balance inquiry");
		int item = scan.nextInt();
		scan.nextLine();
		switch (item) {
			case 1:
				transaction.setTransactionDate(new Date());
				System.out.println("1. Deposit\n2. Withdrawal\n3. Payment\n4. PIX");
				int type = scan.nextInt();
				switch (type) {
					case 1:
						transaction.setTransactionType(TransactionType.DEPOSIT);
						break;

					case 2:
						transaction.setTransactionType(TransactionType.WITHDRAWAL);
						break;

					case 3:
						transaction.setTransactionType(TransactionType.PAYMENT);
						break;

					case 4:
						transaction.setTransactionType(TransactionType.PIX);
						break;

					default:
						break;
				}
				transaction.setOperationValue(20000.0);
				controller.insert(transaction);
				break;
			case 2:
				System.out.println("1. Monthly\n2. Established period");
				int type2 = scan.nextInt();
				scan.nextLine();
				switch (type2) {
					case 1:
						System.out.println("Enter the month: ");
						String month = scan.nextLine();
						System.out.println("Enter the year: ");
						String year = scan.nextLine();
						List<Transaction> transactions = controller.balanceInquiryMonth(transaction.getAccount().getId(), month,year);
						for (Transaction transaction2 : transactions) {
							System.out.println(transaction2.toString());
						}
						break;
					case 2:
						System.out.println("Enter the start day: ");
						int startDay = scan.nextInt();
						System.out.println("Enter the start month: ");
						int startMonth = scan.nextInt();
						System.out.println("Enter the start year: ");
						int startYear = scan.nextInt();
						System.out.println("Enter the end day: ");
						int endDay = scan.nextInt();
						System.out.println("Enter the end month: ");
						int endMonth = scan.nextInt();
						System.out.println("Enter the end year: ");
						int endYear = scan.nextInt();
						String startDate = startYear + "-" + startMonth + "-" + startDay;
						String endDate = endYear + "-" + endMonth + "-" + endDay;
						List<Transaction> transactions2 = controller.balanceInquiryPeriod(transaction.getAccount().getId(), startDate, endDate);
						for (Transaction transaction2 : transactions2) {
							System.out.println(transaction2.toString());
						}
						break;
					default:
						break;
				}
				break;
			default:
				break;
		}

	}

}
