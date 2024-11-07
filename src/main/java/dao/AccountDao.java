package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Account;

public class AccountDao {
	protected EntityManager em;

	public AccountDao() {
		em = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("nomePU");
		if (em == null) {
			em = emf.createEntityManager();
		}
		return em;
	}
	
	public Account insert(Account account) {
		try {
			em.getTransaction().begin();
			em.persist(account);
			em.getTransaction().commit();
			em.close();
			return null;
		} catch (Exception e) {
			em.getTransaction().rollback();
			em.close();
			
			return null;
		}
	}

	public Account update(Account account) {
		Account bankAccount = null;
		try {
			if (account.getId() != null) {
				em.getTransaction().begin();

				bankAccount = findById(account.getId());
				if (bankAccount != null) {
					em.merge(bankAccount);
				}

				em.getTransaction().commit();
				em.close();
			}
			return bankAccount;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

	public void delete(Long id) {
		try {
			em.getTransaction().begin();
			Account bankAccount = findById(id);
			if (bankAccount != null) {
				em.remove(bankAccount);
			}
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Account findById(Long id) {
		Account account = em.find(Account.class, id);
		em.close();
		return account;
	}
}
