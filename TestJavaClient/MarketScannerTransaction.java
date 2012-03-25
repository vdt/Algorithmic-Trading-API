package TestJavaClient;

import java.util.HashMap;

import com.ib.client.ContractDetails;

/**
 * MarketScannerTransaction.java
 * 
 * @author dmohanty, tadams is a butt bandit.
 */

public class MarketScannerTransaction extends Transaction{
	
	private static final long serialVersionUID = -3668483670762852499L;
	
//	ArrayList<ContractDetails> contractDetails = new ArrayList<ContractDetails>();
	HashMap<Integer, ContractDetails> contractDetails = new HashMap<Integer, ContractDetails>();
	
	// Request fields
	int m_numberOfRows;
	SecurityType m_instrument;
	String m_locationCode;
	String m_scanCode;
	double m_abovePrice;
	double m_belowPrice;
	int m_aboveVolume;
	int m_averageOptionVolumeAbove;
	double m_marketCapAbove;
	double m_marketCapBelow;
	String m_moodyRatingAbove;
	String m_moodyRatingBelow;
	String m_spRatingAbove;
	String m_spRatingBelow;
	String m_maturityDateAbove;
	String m_maturityDateBelow;
	double m_couponRateAbove;
	double m_couponRateBelow;
	String m_excludeConvertible;
	String m_scannerSettingPairs;
	String m_stockTypeFilter;
	
	// Returned data fields
	int rank; 
	String distance;
	String benchmark;
	String projection; 
	String legsStr;
	
	boolean finished = false;
	
	// Constructor (creates a new transaction object with request data used for this transaction)
	public MarketScannerTransaction(int numberOfRows, SecurityType instrument, String locationCode, String scanCode,
			double abovePrice, double belowPrice, int aboveVolume, int averageOptionVolumeAbove, double marketCapAbove,
			double marketCapBelow, String moodyRatingAbove, String moodyRatingBelow, String spRatingAbove, String spRatingBelow, 
			String maturityDateAbove, String maturityDateBelow, double couponRateAbove, double couponRateBelow, 
			String excludeConvertible, String scannerSettingPairs, String stockTypeFilter){
		transactionType = TransactionType.MARKET_SCANNER;
		
		m_numberOfRows = numberOfRows;
		m_instrument = instrument;
		m_locationCode = locationCode;
		m_scanCode = scanCode;
		m_abovePrice = abovePrice;
		m_belowPrice = belowPrice;
		m_aboveVolume = aboveVolume;
		m_averageOptionVolumeAbove = averageOptionVolumeAbove;
		m_marketCapAbove = marketCapAbove;
		m_marketCapBelow = marketCapBelow;
		m_moodyRatingAbove = moodyRatingAbove;
		m_moodyRatingBelow = moodyRatingBelow;
		m_spRatingAbove = spRatingAbove;
		m_spRatingBelow = spRatingBelow;
		m_maturityDateAbove = maturityDateAbove;
		m_maturityDateBelow = maturityDateBelow;
		m_couponRateAbove = couponRateAbove;
		m_couponRateBelow = couponRateBelow;
		m_excludeConvertible = excludeConvertible;
		m_scannerSettingPairs = scannerSettingPairs;
		m_stockTypeFilter = stockTypeFilter;
	}

	public void addContract(ContractDetails c) {
		contractDetails.put(rank, c);
		this.checkIsFinished();
	}
	
	// Getters and setters for transaction object data fields
	
	private void checkIsFinished() {
		for (int i = 0; i <= (m_numberOfRows - 1); i++) {
			if (contractDetails.get((Integer) i) == null) {
				return;
			}
		}
		this.finished = true;
	}

	public boolean isFinished() {
		return finished;
	}

	public int getM_numberOfRows() {
		return m_numberOfRows;
	}

	public void setM_numberOfRows(int m_numberOfRows) {
		this.m_numberOfRows = m_numberOfRows;
	}

	public SecurityType getM_instrument() {
		return m_instrument;
	}

	public void setM_instrument(SecurityType m_instrument) {
		this.m_instrument = m_instrument;
	}

	public String getM_locationCode() {
		return m_locationCode;
	}

	public void setM_locationCode(String m_locationCode) {
		this.m_locationCode = m_locationCode;
	}

	public String getM_scanCode() {
		return m_scanCode;
	}

	public void setM_scanCode(String m_scanCode) {
		this.m_scanCode = m_scanCode;
	}

	public double getM_abovePrice() {
		return m_abovePrice;
	}

	public void setM_abovePrice(double m_abovePrice) {
		this.m_abovePrice = m_abovePrice;
	}

	public double getM_belowPrice() {
		return m_belowPrice;
	}

	public void setM_belowPrice(double m_belowPrice) {
		this.m_belowPrice = m_belowPrice;
	}

	public int getM_aboveVolume() {
		return m_aboveVolume;
	}

	public void setM_aboveVolume(int m_aboveVolume) {
		this.m_aboveVolume = m_aboveVolume;
	}

	public int getM_averageOptionVolumeAbove() {
		return m_averageOptionVolumeAbove;
	}

	public void setM_averageOptionVolumeAbove(int m_averageOptionVolumeAbove) {
		this.m_averageOptionVolumeAbove = m_averageOptionVolumeAbove;
	}

	public double getM_marketCapAbove() {
		return m_marketCapAbove;
	}

	public void setM_marketCapAbove(double m_marketCapAbove) {
		this.m_marketCapAbove = m_marketCapAbove;
	}

	public double getM_marketCapBelow() {
		return m_marketCapBelow;
	}

	public void setM_marketCapBelow(double m_marketCapBelow) {
		this.m_marketCapBelow = m_marketCapBelow;
	}

	public String getM_moodyRatingAbove() {
		return m_moodyRatingAbove;
	}

	public void setM_moodyRatingAbove(String m_moodyRatingAbove) {
		this.m_moodyRatingAbove = m_moodyRatingAbove;
	}

	public String getM_moodyRatingBelow() {
		return m_moodyRatingBelow;
	}

	public void setM_moodyRatingBelow(String m_moodyRatingBelow) {
		this.m_moodyRatingBelow = m_moodyRatingBelow;
	}

	public String getM_spRatingAbove() {
		return m_spRatingAbove;
	}

	public void setM_spRatingAbove(String m_spRatingAbove) {
		this.m_spRatingAbove = m_spRatingAbove;
	}

	public String getM_spRatingBelow() {
		return m_spRatingBelow;
	}

	public void setM_spRatingBelow(String m_spRatingBelow) {
		this.m_spRatingBelow = m_spRatingBelow;
	}

	public String getM_maturityDateAbove() {
		return m_maturityDateAbove;
	}

	public void setM_maturityDateAbove(String m_maturityDateAbove) {
		this.m_maturityDateAbove = m_maturityDateAbove;
	}

	public String getM_maturityDateBelow() {
		return m_maturityDateBelow;
	}

	public void setM_maturityDateBelow(String m_maturityDateBelow) {
		this.m_maturityDateBelow = m_maturityDateBelow;
	}

	public double getM_couponRateAbove() {
		return m_couponRateAbove;
	}

	public void setM_couponRateAbove(double m_couponRateAbove) {
		this.m_couponRateAbove = m_couponRateAbove;
	}

	public double getM_couponRateBelow() {
		return m_couponRateBelow;
	}

	public void setM_couponRateBelow(double m_couponRateBelow) {
		this.m_couponRateBelow = m_couponRateBelow;
	}

	public String getM_excludeConvertible() {
		return m_excludeConvertible;
	}

	public void setM_excludeConvertible(String m_excludeConvertible) {
		this.m_excludeConvertible = m_excludeConvertible;
	}

	public String getM_scannerSettingPairs() {
		return m_scannerSettingPairs;
	}

	public void setM_scannerSettingPairs(String m_scannerSettingPairs) {
		this.m_scannerSettingPairs = m_scannerSettingPairs;
	}

	public String getM_stockTypeFilter() {
		return m_stockTypeFilter;
	}

	public void setM_stockTypeFilter(String m_stockTypeFilter) {
		this.m_stockTypeFilter = m_stockTypeFilter;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public HashMap<Integer, ContractDetails> getContractDetails() {
		return contractDetails;
	}

	public void setContractDetails(HashMap<Integer, ContractDetails> contractDetails) {
		this.contractDetails = contractDetails;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getBenchmark() {
		return benchmark;
	}

	public void setBenchmark(String benchmark) {
		this.benchmark = benchmark;
	}

	public String getProjection() {
		return projection;
	}

	public void setProjection(String projection) {
		this.projection = projection;
	}

	public String getLegsStr() {
		return legsStr;
	}

	public void setLegsStr(String legsStr) {
		this.legsStr = legsStr;
	}
	
}
