package TestJavaClient;

/**
 * RequestHistoricalDataTransaction.java
 * 
 * @author dmohanty, tadams
 */

public class RequestHistoricalDataTransaction extends Transaction{
	
	private static final long serialVersionUID = -3967679532319941332L;
	
	// Request fields
	String m_symbol;
	SecurityType m_secType;
	String m_exchange;
	String m_primaryExch;
	String m_currency;
	boolean m_includeExpired;
	String m_endDateTime;
	String m_durationStr;
	String m_barSizeSetting;
	BarRequestType m_whatToShow;
	boolean m_useRTH;
	int m_formatDate;
	
	// Returned data fields
	int reqId;
	String date;
	double open;
	double high;
	double low;
	double close;
	int volume;
	int count;
	double WAP;
	boolean hasGaps;
	
	// Constructor (creates a new transaction object with request data used for this transaction)
	public RequestHistoricalDataTransaction(String tickerSymbol, SecurityType secType,
            String endDateTime, String durationStr, String barSizeSetting, 
            BarRequestType whatToShow, String exchange, String primaryExchange, 
            String currency, boolean useRTH, int formatDate, boolean includeExpired){
		transactionType = TransactionType.HISTORICAL_DATA;
		
		m_symbol = tickerSymbol;
		m_secType = secType;
		m_exchange = exchange;
		m_primaryExch = primaryExchange;
		m_currency = currency;
		m_includeExpired = includeExpired;
		
		m_endDateTime = endDateTime;
		m_durationStr = durationStr;
		m_barSizeSetting = barSizeSetting;
		m_whatToShow = whatToShow;
		m_useRTH = useRTH;
		m_formatDate = formatDate;
		
		
	}

	// Getters and setters for transaction object data fields

	public int getReqId() {
		return reqId;
	}

	public void setReqId(int reqId) {
		this.reqId = reqId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getWAP() {
		return WAP;
	}

	public void setWAP(double wAP) {
		WAP = wAP;
	}

	public boolean isHasGaps() {
		return hasGaps;
	}

	public void setHasGaps(boolean hasGaps) {
		this.hasGaps = hasGaps;
	}

	String getM_symbol() {
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

	public String getM_endDateTime() {
		return m_endDateTime;
	}

	public void setM_endDateTime(String m_endDateTime) {
		this.m_endDateTime = m_endDateTime;
	}

	public String getM_durationStr() {
		return m_durationStr;
	}

	public void setM_durationStr(String m_durationStr) {
		this.m_durationStr = m_durationStr;
	}

	public String getM_barSizeSetting() {
		return m_barSizeSetting;
	}

	public void setM_barSizeSetting(String m_barSizeSetting) {
		this.m_barSizeSetting = m_barSizeSetting;
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

	public int getM_formatDate() {
		return m_formatDate;
	}

	public void setM_formatDate(int m_formatDate) {
		this.m_formatDate = m_formatDate;
	}
	
	
}
