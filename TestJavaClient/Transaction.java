package TestJavaClient;

import java.io.Serializable;

/**
 * Transaction.java
 * 
 * Defines base attributes for a transaction object.
 * 
 * @author dmohanty, tadams, lkjaero, gkoch
 *
 */

public abstract class Transaction implements Serializable {
	private static final long serialVersionUID = 2377281682371140185L;
	TransactionType transactionType;
	Timestamp timestamp;
	
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimeStamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public TransactionType getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	
	
	
}
