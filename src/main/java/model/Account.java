package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="conta")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="operation_value")
	private double operationValue;
	@Column(name="transaction_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date transactionDate;
	@Column(name="description",length = 150, nullable = true, unique = true)
	private String description;
	@Column(name="transaction_type")
	private String transactionType;
	@Column(name="account_holder_name")
	private String accountHolderName;
	@Column(name="account_holder_cpf")
	private String accountHolderCpf;

	public Account() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getOperationValue() {
		return operationValue;
	}

	public void setOperationValue(double operationValue) {
		this.operationValue = operationValue;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public String getAccountHolderCpf() {
		return accountHolderCpf;
	}

	public void setAccountHolderCpf(String accountHolderCpf) {
		this.accountHolderCpf = accountHolderCpf;
	}

}