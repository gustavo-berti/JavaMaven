package service;

import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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

	public List<Transaction> listAllByCpf(String transactionType) {
		return dao.listAllByCpf(transactionType);
	}

    public List<Transaction> balanceInquiryMonth(String cpf, String month, String year) {
		String dateStr = year+"-"+month;
        return dao.balanceInquiryMonth(cpf, dateStr);
    }

	public List<Transaction> balanceInquiryPeriod(String cpf, String startDate, String endDate) {
		return dao.balanceInquiryPeriod(cpf, startDate, endDate);
	}

}
