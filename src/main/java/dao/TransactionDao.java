package dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Transaction;

public class TransactionDao {
	protected EntityManager em;

	public TransactionDao() {
		em = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("nomePU");
		if (em == null) {
			em = emf.createEntityManager();
		}
		return em;
	}

	public Transaction insert(Transaction transaction) {
		try {
			em.getTransaction().begin();
			em.persist(transaction);
			em.getTransaction().commit();
			em.close();
			return null;
		} catch (Exception e) {
			em.getTransaction().rollback();
			em.close();
			
			return null;
		}
	}

	public Transaction update(Transaction transaction) {
		Transaction bankTransaction = null;
		try {
			if (transaction.getId() != null) {
				em.getTransaction().begin();

				bankTransaction = findById(transaction.getId());
				if (bankTransaction != null) {
					bankTransaction.setDescription(transaction.getDescription());
					em.merge(bankTransaction);
				}

				em.getTransaction().commit();
				em.close();
			}
			return bankTransaction;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

	public void delete(Long id) {
		try {
			em.getTransaction().begin();
			Transaction bankTransaction = findById(id);
			if (bankTransaction != null) {
				em.remove(bankTransaction);
			}
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Transaction findById(Long id) {
		Transaction transaction = em.find(Transaction.class, id);
		em.close();
		return transaction;
	}

	public List<Transaction> listAll() {
		try {
			List<Transaction> transactions = em.createQuery("from Transaction", Transaction.class).getResultList();
			em.close();
			return transactions;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public List<Transaction> listAllById(Long id) {
		try {
			List<Transaction> transactions = em.createQuery("SELECT t FROM Transaction t INNER JOIN t.account a WHERE a.id =  " +id,Transaction.class).getResultList();
			em.close();
			return transactions;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public List<Transaction> listAllByTransactionType(String transactionType) {
		try {
			List<Transaction> transactions = em.createQuery("FROM Transaction WHERE transactionType = '" + transactionType+"'").getResultList();
			em.close();
			return transactions;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
		
	public List<Transaction> balanceInquiryMonth(Long id, String date) {
		try {
			List<Transaction> transactions = em.createQuery("SELECT t FROM Transaction t INNER JOIN t.account a WHERE a.id = " +id+" AND t.transactionDate BETWEEN '"+ date+"-01' AND '"+date+"-31'", Transaction.class).getResultList();
			em.close();
			return transactions;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Transaction> balanceInquiryPeriod(Long id, String startDateStr, String endDateStr) {
		try {
			List<Transaction> transactions = em.createQuery("SELECT t FROM Transaction t INNER JOIN t.account a WHERE a.id = " +id+" AND t.transactionDate BETWEEN '"+ startDateStr+"' AND '"+endDateStr+"'",Transaction.class).getResultList();
			em.close();
			return transactions;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}