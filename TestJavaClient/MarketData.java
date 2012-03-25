package TestJavaClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * MarketData.java 
 * 
 * Contains all key data object for trading system and manages their functionality.
 * 
 * @author gkoch
 *
 */

public class MarketData implements Serializable{
	private static final long serialVersionUID = -6063074796462263640L;
	private volatile HashMap<Integer,Transaction> transactionMap;
	private volatile ArrayList<Security> securityList;

	public MarketData(){
		transactionMap = new HashMap<Integer, Transaction>();
		securityList = new ArrayList<Security>();
	}
	
	public synchronized ArrayList<Security> getSecurityList() {
		return securityList;
	}

	public synchronized HashMap<Integer, Transaction> getTransactionMap() {
		return transactionMap;
	}
	
}
