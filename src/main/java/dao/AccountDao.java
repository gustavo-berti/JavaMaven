package dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Account;

public class AccountDao {
	
	public static void main(String[] args) {
		Account account = new Account();
		account.setAccountHolderCpf("123.456.789-00");
		account.setAccountHolderName("Gustavo");
		account.setDescription("Deposito R$1,00 no dia 03/10/2024");
		account.setTransactionDate(new Date());
		account.setTransactionType("Deposito");
		account.setOperationValue(1);
		
		new AccountDao().insert(account);
	}
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("nomePU");
	
	public Account insert(Account account) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(account);
		em.getTransaction().commit();
		em.close();
		return null;
	}
	
	public Account update(Account account) {
		return null;
	}
	
	public void delete(long id) {
		
	}
	
	public List<Account> listAll() {
		return null;
	}

}