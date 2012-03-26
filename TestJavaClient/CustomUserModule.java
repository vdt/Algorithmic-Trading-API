package TestJavaClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * 
 * @author lkjaero11 is a sexy stud = original + revised
 * @author jhartless = revised and updated
 *  
 * A class to specify the trading algorithm.
 * simplifies user interaction instead of messing w/ controller
 *
 */
public class CustomUserModule {
	private Controller controller;
	private TimeKeeper timeKeeper;
		
	private PriorityQueue<Integer> queue;
	
	
	/**
	 * The main algorithm that makes trades goes here.
	 * called from gateKeeper
	 */
	public void mainAlgorithm(Transaction transaction) {
		
		startAlgorithm();
		
		endAlgorithm();
	}
	
	/**
	 * Run whenever a timer is called. Should be used to query data every so often.
	 * Has example timer functions set up here.
	 */
	public void timerCalled(String source) {
		if (source.equals("Check Volumes")) {// if we are checking volumes
			for (int i = 0; i <= holdings.size() - 1; i++) {// for our holding's list
				Holding h = holdings.get(i);// a holding object is the one specified at a certain index
				String symbol = h.transaction.m_symbol;// create a String variable to retrieve the symbol from that holding object
				String action = h.transaction.m_action;// create a String variable to retrieve the current action on our holding object
				controller.reqRealTimeBars(symbol, SecurityType.STK, "CBOE", "CBOE", "USD", false, 0, null, true);// controller req real time bars for our company's symbol
				if (h.openingVolume == (volumes.get(symbol) * 2)) {// checking current volume to opening volume.  If 2x opening volume
					if (action.equals("BUY")) {// if our current action is a buy for our asset
						holdings.remove(h);// remove it from our list
						// place an order to sell that object
						controller.placeOrder(symbol, SecurityType.STK, "", 0.0, "", "", "CBOE", "CBOE", "USD", "", false, "", "", "SELL", 500, "MKT", 0.0, 0.0, "", "", false);
					}
					else if (action.equals("SELL")) {// if our current action is a sell for our asset
						holdings.remove(h);// remove it from our list
						// place an order to buy that object
						controller.placeOrder(symbol, SecurityType.STK, "", 0.0, "", "", "CBOE", "CBOE", "USD", "", false, "", "", "BUY", 500, "MKT", 0.0, 0.0, "", "", false);
					}
				}
			}
		}
	}
	
	/**
	 * A space to set up timers, started whenever the user hits the start button.
	 * An example timer is set up already.
	 */
	public void setUpTimers() {
		timeKeeper.scheduleAction(60, "Check Volumes");// check volumes every 60 seconds || every minute || every hour??
	}
	
	/**
	 * @param time
	 * an action occurs based on a specific time.  allows us to run certain methods at specified time
	 */
	public void scheduledEvent(String time) {
		System.out.println(time);
		
		if (time.equals("08:35") || time.equals("8:35") || time.equals("12:33")){//for the time @ 8:35 a.m.			
			//request historical data for S&P 500 retrieving yesterdaysClose
			controller.reqHistData("BXO", SecurityType.IND, timeKeeper.getYesterdaysClose(), "1 D", "1 day", BarRequestType.TRADES, "CBOE", "CBOE", "USD", true, 1, false);
		}
		
		if (time.equals("9:00") || time.equals("09:00") || time.equals("12:34")) {//for the time @ 9:00 a.m.
			//request Market Data for the S&P 500 (BXO)			
			controller.reqMktData("BXO", SecurityType.IND,"CBOE","CBOE","USD", false, true, "");

			// Buy
			if (yesterdaysClose < todaysOpen){// if the close is lower than open
				buying = true;// set buying to true
				// market scan the TOP % Gainers (1)
				controller.reqScannerSubscription(10, SecurityType.STK, "STK.US.MAJOR", "TOP_PERC_GAIN", 0.0, 5000.0, 0, 0, 0.0, 1000000000.0, "", "", "", "", "", "", 0.0, 0.0, "", "Annual,true", "ALL");
			}
			else{//current price is higher than the close
				shortSelling = true;// set selling to true
				// market scan the TOP % Losers (1)
				controller.reqScannerSubscription(10, SecurityType.STK, "STK.US.MAJOR", "TOP_PERC_LOSE", 0.0, 5000.0, 0, 0, 0.0, 1000000000.0, "", "", "", "", "", "", 0.0, 0.0, "", "Annual,true", "ALL");
			}
		}
		
		if (time.equals("14:30")) {// if the time equals 2:30 (close to market closing time)
			if (buying = true) {// for all those bought
				sellAll();// sell all
				System.out.println("Selling all holdings.");// inform us of our action to sell all holdings
			}
			else if (shortSelling = true) {// for all those sold
				buyAll();// buy all
				System.out.println("Buying all shorted stocks.");// inform us of our action to buy all holdings
			}
		}
		if (time.equals("15:01")) {// at a minute after close (anytime after the market closes)
			// Clear all temporary data in our lists so we can reset them in the morning
			holdings.clear();
			volumes.clear();
			
			// Reset variables
			yesterdaysClose = 0.0;
			todaysOpen = 0.0;
			buying = false;
			shortSelling = false;

		}
	}

	/**
	 * Run when the user hits the start button.
	 */
	public void start() {
		
	}
	
	/**
	 * Run when the user hits the stop button.
	 */
	public void stop() {

	}
	
	/**
	 *told to sell in regards to previous actions from above 
	 */
	public void sellAll() {
		for (int i = 0; i <= holdings.size() - 1; i++) {// for all elements in our holdings list
			String symbol = holdings.get(i).transaction.m_symbol;// create a string variable based on our list's index retrieval specifying the company symbol
			holdings.remove(i);// remove it from our list
			// sell 500 shares of the stock
			controller.placeOrder(symbol, SecurityType.STK, "", 0.0, "", "", "CBOE", "CBOE", "USD", "", false, "", "", "SELL", 500, "MKT", 0.0, 0.0, "", "", false);
		}
	}
	
	/**
	 * told to buy in regards to previous actions from above
	 */
	public void buyAll() {
		for (int i = 0; i <= holdings.size() - 1; i++) {// for all elements in our holdings list
			String symbol = holdings.get(i).transaction.m_symbol;// create a string variable based on our list's index retrieval specifying the company symbol
			holdings.remove(i);// remove it from our list
			// buy 500 shares of the stock
			controller.placeOrder(symbol, SecurityType.STK, "", 0.0, "", "", "CBOE", "CBOE", "USD", "", false, "", "", "BUY", 500, "MKT", 0.0, 0.0, "", "", false);
		}
	}
	
	/**
	 * a holding class is a inner class variable which will allow us to update and retrieve certain values which need to be constantly check
	 * @author lkjaero, jhartless
	 *
	 */
	private class Holding{
		//composed of a transaction object and a long openingVolume
		public PlaceOrderTransaction transaction;
		public long openingVolume;
		
		//upon initializing creating a holding object, set the transaction, the openVolume, and the closeVolume
		private Holding (PlaceOrderTransaction t, long volume){
			transaction = t;
			openingVolume = volume;
		}
	}
	
	/**
	 * ==========================================================================================
	 * Implementation details. Don't edit anything below here unless you know what you're doing.
	 * ==========================================================================================
	 */
	public CustomUserModule(Controller c) {
		controller = c;
		timeKeeper = new TimeKeeper(this);
		queue = new PriorityQueue<Integer>();
		
	}
	
	public void stopTimers() {
		timeKeeper.stopTimers();
	}
	
	public void startButtonPressed() {
		this.start();
		this.setUpTimers();
	}
	
	public void marketDataEnd(int id) {
		queue.add(new Integer(id));
	}
	
	public void scannerDataEnd(int id) {
		queue.add(new Integer(id));
	}
	
	public void historicalDataEnd(int id) {
		queue.add(new Integer(id));
	}
	
	public void realTimeBarsEnd(int id) {
		queue.add(new Integer(id));
	}
	
	public void placeOrderEnd(int id) {
		queue.add(new Integer(id));
	}
	
	boolean inUse;
	
	public void gateKeeper() {
		if(!inUse) {
			Transaction t = controller.getMarketData().getTransactionMap().get(queue.poll());
			if (t != null) {
				if (t.getTransactionType() == TransactionType.MARKET_SCANNER) {
					MarketScannerTransaction mst = (MarketScannerTransaction) t;
					if (mst.isFinished() != true) {
						return;
					}
				}
				mainAlgorithm(t);
			}
		}
	}
	
	private void startAlgorithm() {
		inUse = true;
	}
	
	private void endAlgorithm() {
		inUse = false;
	}
}
