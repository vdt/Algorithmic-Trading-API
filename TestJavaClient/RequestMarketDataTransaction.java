package TestJavaClient;

/**
 * RequestMarketDataTransaction.java
 * 
 * @author dmohanty, tadams
 */

public class RequestMarketDataTransaction extends Transaction{
	
	private static final long serialVersionUID = -1883395619001477221L;

	// Request fields
	String m_symbol;
	SecurityType m_secType;
	String m_exchange;
	String m_primaryExch;
	String m_currency;
	boolean m_includeExpired;
	boolean m_useSnapshot;
	String m_genericTicks;
	
	//Returned data fields
	int field;
	double price;
	int canAutoExecute;
	
	double impliedVol;
	double delta;
	double optPrice;
	double pvDividend;
	double gamma;
	double vega;
	double theta;
	double undPrice;
	
	int size;
	
	int tickType;
	double value;
	
	int tickerId;
	
	double basisPoints;
	String formattedBasisPoints; 
	double impliedFuture; 
	int holdDays;
	String futureExpiry;
	double dividendImpact; 
	double dividendsToExpiry;
	
	// Constructor (creates a new transaction object with request data used for this transaction)
	public RequestMarketDataTransaction(String tickerSymbol, SecurityType secType, String exchange,
			String primaryExchange, String currency,
			boolean includeExpired, boolean useSnapshot,
			String genericTicks){
		transactionType = TransactionType.MARKET_DATA;
		
		m_symbol = tickerSymbol;
		m_secType = secType;
		m_exchange = exchange;
		m_primaryExch = primaryExchange;
		m_currency = currency;
		m_includeExpired = includeExpired;
		m_useSnapshot = useSnapshot;
		m_genericTicks = genericTicks;
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

	public boolean isM_useSnapshot() {
		return m_useSnapshot;
	}

	public void setM_useSnapshot(boolean m_useSnapshot) {
		this.m_useSnapshot = m_useSnapshot;
	}

	public String getM_genericTicks() {
		return m_genericTicks;
	}

	public void setM_genericTicks(String m_genericTicks) {
		this.m_genericTicks = m_genericTicks;
	}

	public int getField() {
		return field;
	}

	public void setField(int field) {
		this.field = field;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCanAutoExecute() {
		return canAutoExecute;
	}

	public void setCanAutoExecute(int canAutoExecute) {
		this.canAutoExecute = canAutoExecute;
	}

	public double getImpliedVol() {
		return impliedVol;
	}

	public void setImpliedVol(double impliedVol) {
		this.impliedVol = impliedVol;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	public double getOptPrice() {
		return optPrice;
	}

	public void setOptPrice(double optPrice) {
		this.optPrice = optPrice;
	}

	public double getPvDividend() {
		return pvDividend;
	}

	public void setPvDividend(double pvDividend) {
		this.pvDividend = pvDividend;
	}

	public double getGamma() {
		return gamma;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

	public double getVega() {
		return vega;
	}

	public void setVega(double vega) {
		this.vega = vega;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

	public double getUndPrice() {
		return undPrice;
	}

	public void setUndPrice(double undPrice) {
		this.undPrice = undPrice;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTickType() {
		return tickType;
	}

	public void setTickType(int tickType) {
		this.tickType = tickType;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getTickerId() {
		return tickerId;
	}

	public void setTickerId(int tickerId) {
		this.tickerId = tickerId;
	}

	public double getBasisPoints() {
		return basisPoints;
	}

	public void setBasisPoints(double basisPoints) {
		this.basisPoints = basisPoints;
	}

	public String getFormattedBasisPoints() {
		return formattedBasisPoints;
	}

	public void setFormattedBasisPoints(String formattedBasisPoints) {
		this.formattedBasisPoints = formattedBasisPoints;
	}

	public double getImpliedFuture() {
		return impliedFuture;
	}

	public void setImpliedFuture(double impliedFuture) {
		this.impliedFuture = impliedFuture;
	}

	public int getHoldDays() {
		return holdDays;
	}

	public void setHoldDays(int holdDays) {
		this.holdDays = holdDays;
	}

	public String getFutureExpiry() {
		return futureExpiry;
	}

	public void setFutureExpiry(String futureExpiry) {
		this.futureExpiry = futureExpiry;
	}

	public double getDividendImpact() {
		return dividendImpact;
	}

	public void setDividendImpact(double dividendImpact) {
		this.dividendImpact = dividendImpact;
	}

	public double getDividendsToExpiry() {
		return dividendsToExpiry;
	}

	public void setDividendsToExpiry(double dividendsToExpiry) {
		this.dividendsToExpiry = dividendsToExpiry;
	}
	
}