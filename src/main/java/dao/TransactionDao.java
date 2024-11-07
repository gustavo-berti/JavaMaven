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

	public List<Transaction> listAll() {
		try {
			List<Transaction> transactions = em.createQuery("from Transaction").getResultList();
			em.close();
			return transactions;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public List<Transaction> listAllByCpf(String accountHolderCpf) {
		try {
			List<Transaction> transactions = em.createQuery("FROM Transaction WHERE accountHolderCpf = '" + accountHolderCpf+"'").getResultList();
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

	private Transaction findById(Long id) {
		Transaction transaction = em.find(Transaction.class, id);
		em.close();
		return transaction;
	}

		
	public List<Transaction> balanceInquiryMonth(String accountHolderCpf, String date) {
		try {
			List<Transaction> transactions = em.createQuery("FROM Transaction WHERE accountHolderCpf = '" + accountHolderCpf+"' AND transaction_date BETWEEN '"+ date+"-01' AND '"+date+"-31'").getResultList();
			em.close();
			return transactions;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Transaction> balanceInquiryPeriod(String accountHolderCpf, String startDateStr, String endDateStr) {
		try {
			List<Transaction> transactions = em.createQuery("FROM Transaction WHERE accountHolderCpf = '" + accountHolderCpf+"' AND transaction_date BETWEEN '"+ startDateStr+"' AND '"+endDateStr+"'").getResultList();
			em.close();
			return transactions;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}