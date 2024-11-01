package util;

public class TransactionUtils {
	public static boolean validateCpf(String cpf) {
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
		for (int i = 1; i < intCpf.length-1; i++) {
			aux += intCpf[i-1]*i;
		}
		aux = aux%11;
		if(aux>=10) {
			aux=0;
		}
		if(intCpf[9] != aux) {
			return true;
		}
		return false;
	}
	
	private static boolean validateDigit2(int[] intCpf) {
		int aux = 0;
		for (int i = 0; i < intCpf.length-1; i++) {
			aux += intCpf[i]*i;
		}
		aux = aux%11;
		if(aux>=10) {
			aux=0;
		}
		if(intCpf[10] != aux) {
			return true;
		}
		return false;
	}
}
