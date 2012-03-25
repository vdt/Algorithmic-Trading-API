package TestJavaClient;

import java.util.ArrayList;

/**
 * Security.java
 * 
 * Data object containing crucial information for a security with its current transaction history.
 * 
 * @author jhartless, nlopez
 *
 */

public class Security{
	
	private String symbol; // Tracks the security 
	private SecurityType securityType;// Stores one of seven security types for this security (STK, OPT, FUT, IND, FOP, CASH, BAG)

	private ArrayList<Integer> marketDataHistory;//ArrayList for Request Market Data
	private ArrayList<Integer> realTimeBarsHistory;//ArrayList for request Real time Bars 
	private ArrayList<Integer> marketScannerHistory;//ArrayList for Market Scanner
	private ArrayList<Integer> placeOrderHistory;//ArrayList  for Place Order
	private ArrayList<Integer> historicalDataHistory;//ArrayList for request History Data
	
	public Security(SecurityType newSecurityType, String tickerSymbol){
		//sets our variables to be our incoming parameters
		securityType = newSecurityType;
		symbol = tickerSymbol;
		marketDataHistory = new ArrayList <Integer>();
		realTimeBarsHistory = new ArrayList <Integer>();
		marketScannerHistory = new ArrayList <Integer>();
		placeOrderHistory = new ArrayList <Integer>();
		historicalDataHistory = new ArrayList <Integer>();
	}

	/**
	 *prints a toString for visual verification
	 *prints out security type and global id
	 */
	public String toString(){
		return "SecurityType: " + securityType + "\nSymbol: " + symbol;
	}
	
	/**
	 * generate getters and setters for arrayLists and class variables
	 * @return
	 */

	public ArrayList<Integer> getMarketDataHistory() {
		return marketDataHistory;
	}


	public void setMarketDataHistory(ArrayList<Integer> marketDataHistory) {
		this.marketDataHistory = marketDataHistory;
	}


	public ArrayList<Integer> getRealTimeBarsHistory() {
		return realTimeBarsHistory;
	}


	public void setRealTimeBarsHistory(ArrayList<Integer> realTimeBarsHistory) {
		this.realTimeBarsHistory = realTimeBarsHistory;
	}


	public ArrayList<Integer> getMarketScannerHistory() {
		return marketScannerHistory;
	}


	public void setMarketScannerHistory(ArrayList<Integer> marketScannerHistory) {
		this.marketScannerHistory = marketScannerHistory;
	}


	public ArrayList<Integer> getPlaceOrderHistory() {
		return placeOrderHistory;
	}


	public void setPlaceOrderHistory(ArrayList<Integer> placeOrderHistory) {
		this.placeOrderHistory = placeOrderHistory;
	}


	public ArrayList<Integer> getHistoricalDataHistory() {
		return historicalDataHistory;
	}


	public void setHistoricalDataHistory(ArrayList<Integer> historicalDataHistory) {
		this.historicalDataHistory = historicalDataHistory;
	}

	public SecurityType getSecurityType() {
		return securityType;
	}


	public void setSecurityType(SecurityType securityType) {
		this.securityType = securityType;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
