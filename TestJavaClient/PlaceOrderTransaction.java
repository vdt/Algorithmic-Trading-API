package TestJavaClient;

/**
 * PlaceOrderTransaction.java
 * 
 * @author dmohanty, tadams
 */

public class PlaceOrderTransaction extends Transaction{
	
	private static final long serialVersionUID = 8388633346372454474L;
	
	// Request fields
	String m_symbol;
	SecurityType m_secType;
	String m_expiry;
	double m_strike;
	String m_right;
	String m_multiplier;
	String m_exchange;
	String m_primaryExch;
	String m_currency;
	String m_localSymbol;
	boolean m_includeExpired;
	String m_secIdType;
	String m_secId;
	
	// Returned data fields
	String m_action;
	int m_totalQuantity;
	String m_orderType;
	double m_lmtPrice;
	double m_auxPrice;
	String m_goodAfterTime;
	String m_goodTillDate;
	boolean m_whatIf;
	
	// Constructor (creates a new transaction object with request data used for this transaction)
	public PlaceOrderTransaction(String symbol, SecurityType secType, String expiry, 
			double strike, String right, String action, int quantity, double price,
			boolean whatIf){
		transactionType = TransactionType.PLACE_ORDER;
		
		m_symbol = symbol;
		m_secType = secType;
		m_expiry = expiry;
		m_strike = strike;
		m_right = right;
		m_multiplier = (secType.toString().equals("STK"))? "" : "100";
		m_exchange = "SMART";
		m_primaryExch = (secType.toString().equals("STK"))? "ISLAND" : "CBOE";
		m_currency = "USD";
		m_localSymbol = "";
		m_includeExpired = false;
		m_secIdType = "";
		m_secId = "";
		
		m_action = action;
		m_totalQuantity = quantity;
		m_orderType = "LMT";
		m_lmtPrice = price;
		m_auxPrice = 0.0;
		m_goodAfterTime = "";
		m_goodTillDate = "";
		m_whatIf = whatIf;
	}

	// Getters and setters for transaction object data fields
	
	public String getM_symbol() {
		return m_symbol;
	}

	public void setM_symbol(String m_symbol) {
		this.m_symbol = m_symbol;
	}

	public SecurityType getM_secType() {
		return m_secType;
	}

	public void setM_secType(SecurityType m_secType) {
		this.m_secType = m_secType;
	}

	public String getM_expiry() {
		return m_expiry;
	}

	public void setM_expiry(String m_expiry) {
		this.m_expiry = m_expiry;
	}

	public double getM_strike() {
		return m_strike;
	}

	public void setM_strike(double m_strike) {
		this.m_strike = m_strike;
	}

	public String getM_right() {
		return m_right;
	}

	public void setM_right(String m_right) {
		this.m_right = m_right;
	}

	public String getM_multiplier() {
		return m_multiplier;
	}

	public void setM_multiplier(String m_multiplier) {
		this.m_multiplier = m_multiplier;
	}

	public String getM_exchange() {
		return m_exchange;
	}

	public void setM_exchange(String m_exchange) {
		this.m_exchange = m_exchange;
	}

	public String getM_primaryExch() {
		return m_primaryExch;
	}

	public void setM_primaryExch(String m_primaryExch) {
		this.m_primaryExch = m_primaryExch;
	}

	public String getM_currency() {
		return m_currency;
	}

	public void setM_currency(String m_currency) {
		this.m_currency = m_currency;
	}

	public String getM_localSymbol() {
		return m_localSymbol;
	}

	public void setM_localSymbol(String m_localSymbol) {
		this.m_localSymbol = m_localSymbol;
	}

	public boolean isM_includeExpired() {
		return m_includeExpired;
	}

	public void setM_includeExpired(boolean m_includeExpired) {
		this.m_includeExpired = m_includeExpired;
	}

	public String getM_secIdType() {
		return m_secIdType;
	}

	public void setM_secIdType(String m_secIdType) {
		this.m_secIdType = m_secIdType;
	}

	public String getM_secId() {
		return m_secId;
	}

	public void setM_secId(String m_secId) {
		this.m_secId = m_secId;
	}

	public String getM_action() {
		return m_action;
	}

	public void setM_action(String m_action) {
		this.m_action = m_action;
	}

	public int getM_totalQuantity() {
		return m_totalQuantity;
	}

	public void setM_totalQuantity(int m_totalQuantity) {
		this.m_totalQuantity = m_totalQuantity;
	}

	public String getM_orderType() {
		return m_orderType;
	}

	public void setM_orderType(String m_orderType) {
		this.m_orderType = m_orderType;
	}

	public double getM_lmtPrice() {
		return m_lmtPrice;
	}

	public void setM_lmtPrice(double m_lmtPrice) {
		this.m_lmtPrice = m_lmtPrice;
	}

	public double getM_auxPrice() {
		return m_auxPrice;
	}

	public void setM_auxPrice(double m_auxPrice) {
		this.m_auxPrice = m_auxPrice;
	}

	public String getM_goodAfterTime() {
		return m_goodAfterTime;
	}

	public void setM_goodAfterTime(String m_goodAfterTime) {
		this.m_goodAfterTime = m_goodAfterTime;
	}

	public String getM_goodTillDate() {
		return m_goodTillDate;
	}

	public void setM_goodTillDate(String m_goodTillDate) {
		this.m_goodTillDate = m_goodTillDate;
	}

	public boolean isM_whatIf() {
		return m_whatIf;
	}

	public void setM_whatIf(boolean m_whatIf) {
		this.m_whatIf = m_whatIf;
	}	
	
}
