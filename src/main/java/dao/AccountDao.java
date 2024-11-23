package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.Account;

public class AccountDao extends GenericDao<Account> {

	public AccountDao() {
		super(Account.class);
	}
	
	public List<Account> listAllByClient(Account account){
		EntityManager em = getEntityManager();
		try {
			List<Account> accounts = em.createQuery("SELECT a FROM Account a INNER JOIN a.client c WHERE c.id = "+account.getClient().getId(), Account.class).getResultList();
			em.close();
			return accounts;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    public Account getAccount(Long id) {
		EntityManager em = getEntityManager();
		try {
			Account account = em.createQuery("FROM Account WHERE id = '"+id+"'", Account.class).getSingleResult();
			em.close();
			return account;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
}
