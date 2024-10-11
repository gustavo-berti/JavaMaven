package view;

import java.util.Date;

import controller.AccountController;
import model.Account;

public class AccountScreen {

	public static void main(String[] args) {
		AccountController controller = new AccountController();
		Account account = new Account();
		account.setAccountHolderCpf("123.456.789-00");
		account.setAccountHolderName("Gustavo");
		account.setDescription("Deposito R$1,00 no dia 03/10/2024");
		account.setTransactionDate(new Date());
		account.setTransactionType("Deposito");
		account.setOperationValue(1.0);
		controller.insert(account);
	}

}
