package dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.Transaction;
import model.enums.TransactionType;

public class TransactionDao extends GenericDao<Transaction> {

	public TransactionDao() {
		super(Transaction.class);
	}

	public List<Transaction> listAll() {
		EntityManager em = getEntityManager();
		try {
			List<Transaction> transactions = em.createQuery("from Transaction", Transaction.class).getResultList();
			em.close();
			return transactions;
		} catch (Exception e) {
			em.close();
			e.printStackTrace();
			return null;
		}
		
	}

	public List<Transaction> listAllById(Long id) {
		EntityManager em = getEntityManager();
		try {
			List<Transaction> transactions = em.createQuery("SELECT t FROM Transaction t INNER JOIN t.account a WHERE a.id =  " +id,Transaction.class).getResultList();
			em.close();
			return transactions;
		} catch (Exception e) {
			em.close();
			e.printStackTrace();
			return null;
		}
		
	}

	public List<Transaction> listAllByTransactionType(TransactionType transactionType) {
		EntityManager em = getEntityManager();
		try {
			List<Transaction> transactions = em.createQuery("FROM Transaction WHERE transactionType = '" + transactionType + "'").getResultList();
			em.close();
			return transactions;
		} catch (Exception e) {
			em.close();
			e.printStackTrace();
			return null;
		}
	}
		
	public List<Transaction> balanceInquiryMonth(Long id, String date) {
		EntityManager em = getEntityManager();
		try {
			List<Transaction> transactions = em.createQuery("SELECT t FROM Transaction t INNER JOIN t.account a WHERE a.id = " +id+" AND t.transactionDate BETWEEN '"+ date+"-01' AND '"+date+"-31'", Transaction.class).getResultList();
			em.close();
			return transactions;
		} catch (Exception e) {
			em.close();
			e.printStackTrace();
			return null;
		}
	}

	public List<Transaction> balanceInquiryPeriod(Long id, String startDateStr, String endDateStr) {
		EntityManager em = getEntityManager();
		try {
			List<Transaction> transactions = em.createQuery("SELECT t FROM Transaction t INNER JOIN t.account a WHERE a.id = " +id+" AND t.transactionDate BETWEEN '"+ startDateStr+"' AND '"+endDateStr+"'",Transaction.class).getResultList();
			em.close();
			return transactions;
		} catch (Exception e) {
			em.close();
			e.printStackTrace();
			return null;
		}
	}

}