package teste;

import util.TransactionUtils;

public class Main {
	public static void main(String[] args) {
		boolean cpf = TransactionUtils.validateCpf("123.456.789-09");
		if(cpf) {
			System.out.println("Is true");
		}else {
			System.out.println("Is false");
		}
	}
}
