package service;

import java.util.Date;
import java.util.List;

import dao.TransactionDao;
import model.Transaction;
import util.TransactionUtils;

public class TransactionService {
	TransactionDao dao = new TransactionDao();

	public Transaction insert(Transaction transaction) {
		if (TransactionUtils.transactionValid(transaction)) {
			return null;
		}
		transaction.setDescription(transaction.getTransactionType() + " Operation");
		transaction.setTransactionDate(new Date());
		return dao.insert(transaction);
	}

	public List<Transaction> listAllById(Long id) {
		return dao.listAllById(id);
	}

    public List<Transaction> balanceInquiryMonth(Long id, String month, String year) {
		String dateStr = year+"-"+month;
        return dao.balanceInquiryMonth(id, dateStr);
    }

	public List<Transaction> balanceInquiryPeriod(Long id, String startDate, String endDate) {
		return dao.balanceInquiryPeriod(id, startDate, endDate);
	}

}
