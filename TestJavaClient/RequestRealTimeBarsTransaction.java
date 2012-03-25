package TestJavaClient;

/**
 * RequestRealTimebarsTransaction.java
 * 
 * @author dmohanty, tadams
 */

public class RequestRealTimeBarsTransaction extends Transaction{
	
	private static final long serialVersionUID = -893593876946638716L;
	
	// Request fields
	String m_symbol;
	SecurityType m_secType;
	String m_exchange;
	String m_primaryExch;
	String m_currency;
	boolean m_includeExpired;
	int m_barSize;
	BarRequestType m_whatToShow;
	boolean m_useRTH;
	
	//Returned data fields
	long time;
	double open;
	double high;
	double low;
	double close;
	long volume;
	double wap;
	int count;
	
	// Constructor (creates a new transaction object with request data used for this transaction)
	public RequestRealTimeBarsTransaction(String tickerSymbol, SecurityType secType,
			String exchange,String primaryExchange,String currency,
			boolean includeExpired,int barSize, BarRequestType whatToShow, 
			boolean useRTH){
		transactionType = TransactionType.REAL_TIME_BARS;
		
		m_symbol = tickerSymbol;
		m_secType = secType;
		m_exchange = exchange;
		m_primaryExch = primaryExchange;
		m_currency = currency;
		m_includeExpired = includeExpired;
		m_barSize = barSize;
		m_whatToShow = whatToShow;
		m_useRTH = useRTH;
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

	public boolean isM_includeExpired() {
		return m_includeExpired;
	}

	public void setM_includeExpired(boolean m_includeExpired) {
		this.m_includeExpired = m_includeExpired;
	}

	public int getM_barSize() {
		return m_barSize;
	}

	public void setM_barSize(int m_barSize) {
		this.m_barSize = m_barSize;
	}

	public BarRequestType getM_whatToShow() {
		return m_whatToShow;
	}

	public void setM_whatToShow(BarRequestType m_whatToShow) {
		this.m_whatToShow = m_whatToShow;
	}

	public boolean isM_useRTH() {
		return m_useRTH;
	}

	public void setM_useRTH(boolean m_useRTH) {
		this.m_useRTH = m_useRTH;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public double getWap() {
		return wap;
	}

	public void setWap(double wap) {
		this.wap = wap;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
}
