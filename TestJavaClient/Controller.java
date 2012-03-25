package TestJavaClient;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import com.ib.client.EClientSocket;
import com.ib.client.EWrapper;
import com.ib.client.EWrapperMsgGenerator;
import com.ib.client.Execution;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.ib.client.ScannerSubscription;
import com.ib.client.UnderComp;

/**
 * Controller.java
 * 
 * Handles all market data and connects using an EClientSocket to Trader WorkStation (Interactive Brokers Java API).
 * The Controller is responsible for the main execution of the automated algorithmic trading system.
 * 
 * Execution is controlled via timers and a finite state machine.
 * 
 * @author gkoch, lkjaero is a sexy beast, jwalsh, dmohanty, jhartless, tadams, nlopez
 *
 */

public class Controller extends JFrame implements EWrapper, ActionListener, java.awt.event.WindowListener{

	private static final long serialVersionUID = -1024529428928335335L;
	@SuppressWarnings("unused")
	private final static String ALL_GENERIC_TICK_TAGS = "100,101,104,105,106,107,165,221,225,233,236,258,293,294,295,318"; 	// List of all generic tick tags for use in various commands
	private final int clientId = 1; // Current clientId (default 1, no need to change because this is arbitrary)
	
	// Main client - connects and communicates with Trader Workstation
	private EClientSocket m_client = new EClientSocket(this);

	// Tracking variables
	private int id = 10; // Each order id must be unique in order to determine what orders, commands, etc. generated a particular piece of data received by the program.
	private TradingState currentState; // Tracks the current finite state machine (fsm) state that the algorithmic trading system is in
	
	// Market data object that manages all known data extracted from Interactive Brokers system
	private MarketData marketData;
	
	// Thread for main line of execution of trading system
	private volatile TradingThread tradingThread;
	
	// Frame objects
	private JLabel lblInfo;
	private JButton btnStart, btnStop;
	
	// Adding custom user module
	private CustomUserModule custom;

	/**
	 * The main trading algorithm should be defined here.
	 */
	public class TradingThread extends Thread {
		
		private CustomUserModule custom;
		
		public void run() {
			// While the trading system is still running (systemRunning), execute some algorithms...
			while(tradingThread == Thread.currentThread()){
				if(currentState == TradingState.COMPUTE){
					// Here we'll write the main code to make trading decisions.
					custom.gateKeeper();
					// Now that we're done with the algorithm, we'll go to our wait state.
					transitionState();
				}
				else if(currentState == TradingState.WAIT){
					try {
						TradingThread.sleep(500); // Wait .5 seconds in this state
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					// Now that we're done waiting for the specified time above, we'll go back to our algorithm.
					transitionState();
				}
			}
		}
		
		public void takeCustomUserModule(CustomUserModule c) {
			custom = c;
		}
	}
	
	/**
	 * Immediately stops the current trading system thread from executing.
	 */
	public void stop(){
		tradingThread = null;
	}
	
	protected EClientSocket client() {
		return m_client;
	}
	
	// Controller constructor, responsible for initialization of program.
	protected Controller() {
		setupFrame(); // Set up basic frame with trading program controls (do not remove)
		initialize(); // Initialize all program settings (do not remove)
	}

	/**
	 * Sets up a JFrame with start/stop controls for trading algorithm execution.
	 * In other words, it defines the GUI and its interface.
	 */
	private void setupFrame() {
		this.setVisible(true);
		this.setSize(new Dimension(400, 80));
		this.setLocation(500, 400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addWindowListener(this);
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		// Set up frame layout
		FlowLayout flow = new FlowLayout();
		this.setLayout(flow);
		
		// Set up labels and buttons for GUI
		lblInfo = new JLabel("Connected to TWS. Select action:");
		btnStart = new JButton("Start");
		btnStart.addActionListener(this);
		btnStop = new JButton("Stop");
		btnStop.addActionListener(this);
		
		// Add all labels and buttons to the frame
		this.add(lblInfo);
		this.add(btnStart);
		this.add(btnStop);
	}
	
	/**
	 * Initializes all basic functionality for the program.
	 */
	private void initialize() {
		currentState = TradingState.COMPUTE; // Start fsm at computation (action) state
		
		custom = new CustomUserModule(this); // Creates the custom user module.
		
		connect(clientId); // Connect to Trader Workstation as client #1 (this is arbitrary, can be anything as long as this is the only program connecting to Trader Workstation on this machine)
		
		marketData = new MarketData();

		System.out.println("Trading system activated.");
//		timer.setInitialDelay(0); // Delay in ms before beginning first timer event after timer is started, adjust if needed.
	}
	
	/**
	 * Defines the actions to be performed before the automated trading system is shut down.
	 */
	private void onClose(){
		System.out.println("Java shutting down. Closing down trading system and disconnecting from TWS.");
		
		// Serialize (save) MarketData object to the MarketData txt file
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		
		String path = "Logs/MarketData" + dateFormat.format(date);
		try {
		    File file = new File(path);

		    // Create file if it does not exist
		    boolean success = file.createNewFile();
		    if (success) {
		        // File did not exist and was created
		    } else {
		        // File already exists
		    }
		} catch (IOException e) {
			System.err.println("Error writing log for " + dateFormat.format(date));
		}
		
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(path);
			out = new ObjectOutputStream(fos);
			out.writeObject(marketData);
			out.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		// Safely disconnect from Trader Workstation (TWS)
		disconnect();
	}

	/**
	 *  Default connect method, do not change. Replicates 'leaving the connect IP blank' from the GUI version of the demo program.
	 */
	private void connect(int clientId) {
		String host = System.getProperty("jts.host"); // default host name, do not change
		host = host != null ? host : "";
		m_client.eConnect(host, 7496, clientId); // uses port 7496, ensure this port is free if you have connection problems
	}
	
	/**
	 *  Default disconnect method, do not change.
	 *  @note: DO NOT USE IF DATA RECEIPTS ARE PENDING FROM TRADER WORKSTATION, OR DATA WILL BE CUT OFF.
	 *  May safely use without loss of data if no data is being transmitted.
	 */
	private void disconnect() {
		System.out.println("Client " + clientId + " disconnected from TWS.");
		m_client.eDisconnect();
	}
	
	/**
	 * Safely increments the global id for method calling so that it does not need to be done explicitly in each block of code.
	 */
	private void updateGlobalId(){
		id++;
	}
	
	/**
	 * Finite state machine transition model, defining the transitions between program states.
	 */
	private void transitionState(){
		switch(currentState){
			case WAIT:
				currentState = TradingState.COMPUTE;
				break;
			case COMPUTE:
				currentState = TradingState.WAIT;
				break;
		}
	}
	
	/*
	 * Implemented methods from ActionListener interface to perform frame operations.
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnStart){
			System.out.println("Program started.");
			tradingThread = new TradingThread();
			tradingThread.takeCustomUserModule(custom);
			tradingThread.start();
			
			custom.startButtonPressed();
		}
		else if(e.getSource() == btnStop){
			System.out.println("Program stopped");
			custom.stopTimers();
			custom.stop();
			stop(); // Immediately stops the execution loop for the trading system
		}
		
	}
	
	/*
	 * Helper methods - perform various desired functions for automated trading system
	 */

	/**
	 * Requests market data from Trader Workstation for a security of the specified type with
	 * ticker symbol as specified. useSnapshot determines if a snapshot or stream will be fetched.
	 * 
	 */
	public void reqMktData(String tickerSymbol, SecurityType secType, String exchange, String primaryExchange, String currency, boolean includeExpired, boolean useSnapshot,
			String genericTicks) {
		updateGlobalId(); // Must be incremented, do not change.
		
		// Set up a default contract using specified ticker symbol
		Contract contract = new Contract();
		// set contract fields
		contract.m_symbol = tickerSymbol;
		contract.m_secType = secType.toString();
		contract.m_exchange = exchange;
		contract.m_primaryExch = primaryExchange;
		contract.m_currency = currency;
		contract.m_includeExpired = includeExpired;

		RequestMarketDataTransaction marketDataTransaction = new RequestMarketDataTransaction (tickerSymbol, secType, exchange, primaryExchange,  currency,	includeExpired, useSnapshot, genericTicks);
		
		marketDataTransaction.setTimeStamp(new Timestamp());
		// ID is used as a key to return a placeOrderTransaction type.
		// Placed in transactionMap HashMap (see MarketData.java) with put method
		marketData.getTransactionMap().put(id, marketDataTransaction);
		
		// Request market data from Trader Workstation using specified settings
		m_client.reqMktData(id, contract, genericTicks, useSnapshot);
	}
	
	/**
	 * Requests real time bars from Trader Workstation using various parameters.
	 * This method uses all parameters.
	 * 
	 */
	public void reqRealTimeBars(String tickerSymbol, SecurityType secType, String exchange, String primaryExchange, String currency, boolean includeExpired, int barSize, BarRequestType whatToShow, boolean useRTH){
		updateGlobalId(); // Must be incremented, do not change.
		
		// Set up a default contract using specified ticker symbol
		Contract contract = new Contract();
		contract.m_symbol = tickerSymbol;
		contract.m_secType = secType.toString();
		contract.m_exchange = exchange;
		contract.m_primaryExch = primaryExchange;
		contract.m_currency = currency;
		contract.m_includeExpired = includeExpired;
		
		RequestRealTimeBarsTransaction realTimeBarsTransaction = new RequestRealTimeBarsTransaction(tickerSymbol, secType, exchange, primaryExchange, currency, includeExpired, barSize, whatToShow, useRTH);
		
		realTimeBarsTransaction.setTimeStamp(new Timestamp());
		// ID is used as a key to return a placeOrderTransaction type.
		// Placed in transactionMap HashMap (see MarketData.java) with put method
		marketData.getTransactionMap().put(id, realTimeBarsTransaction);
		
		// Request real time bars from Trader Workstation using specified settings
		m_client.reqRealTimeBars(id, contract, barSize, whatToShow.toString(), useRTH);
	}
	
	/**
	 * Requests historical data from Trader Work Station of the specified type.
	 * This method uses all parameters.
	 */
	
	public void reqHistData(String tickerSymbol, SecurityType secType,
            String endDateTime, String durationStr, String barSizeSetting, 
            BarRequestType whatToShow, String exchange, String primaryExchange, 
            String currency, boolean useRTH, int formatDate, boolean includeExpired){
		updateGlobalId(); // Must be incremented, do not change.
		
		// Set up a new contract
		Contract contract = new Contract();
		contract.m_symbol = tickerSymbol;
		contract.m_secType = secType.toString();
		contract.m_exchange = exchange;
		contract.m_primaryExch = primaryExchange;
		contract.m_currency = currency;
		contract.m_includeExpired = includeExpired;
		
		// convert rth from boolean to integer for reqHistoricalData method call
		int rth = 0;
		if(useRTH) rth = 1;
		
		RequestHistoricalDataTransaction historicalDataTransaction = new RequestHistoricalDataTransaction (tickerSymbol, secType, endDateTime, durationStr, barSizeSetting,
	            whatToShow, exchange, primaryExchange, currency, useRTH, formatDate, includeExpired);
		
		historicalDataTransaction.setTimeStamp(new Timestamp());
		// ID is used as a key to return a placeOrderTransaction type.
		// Placed in transactionMap HashMap (see MarketData.java) with put method
		marketData.getTransactionMap().put(id, historicalDataTransaction);
		
		// Request historical data from Trader WorkStation using specified settings
		m_client.reqHistoricalData(id, contract, endDateTime, durationStr, barSizeSetting, whatToShow.toString(), rth, formatDate);
	}
	
	/**
	 * Requests a market scanner stream with the specified parameters.
	 * This method uses all possible parameters.
	 */
	public void reqScannerSubscription(int numberOfRows, SecurityType instrument, String locationCode, String scanCode,
			double abovePrice, double belowPrice, int aboveVolume, int averageOptionVolumeAbove, double marketCapAbove,
			double marketCapBelow, String moodyRatingAbove, String moodyRatingBelow, String spRatingAbove, String spRatingBelow, 
			String maturityDateAbove, String maturityDateBelow, double couponRateAbove, double couponRateBelow, 
			String excludeConvertible, String scannerSettingPairs, String stockTypeFilter) {
		updateGlobalId(); // Must be incremented, do not change.
		
		// Set up a new scanner subscription
		ScannerSubscription sb = new ScannerSubscription();
		sb.numberOfRows(numberOfRows);
	    sb.instrument(instrument.toString());
	    sb.locationCode(locationCode);
	    sb.scanCode(scanCode);
	    sb.abovePrice(abovePrice);
	    sb.belowPrice(belowPrice);
	    sb.aboveVolume(aboveVolume);
	    sb.averageOptionVolumeAbove(averageOptionVolumeAbove);
	    sb.marketCapAbove(marketCapAbove);
	    sb.marketCapBelow(marketCapBelow);
	    sb.moodyRatingAbove(moodyRatingAbove);
	    sb.moodyRatingBelow(moodyRatingBelow);
	    sb.spRatingAbove(spRatingAbove);
	    sb.spRatingBelow(spRatingBelow);
	    sb.maturityDateAbove(maturityDateAbove);
	    sb.maturityDateBelow(maturityDateBelow);
	    sb.couponRateAbove(couponRateAbove);
	    sb.couponRateBelow(couponRateBelow);
	    sb.excludeConvertible(excludeConvertible);
	    sb.scannerSettingPairs(scannerSettingPairs);
	    sb.stockTypeFilter(stockTypeFilter);
	    
	    MarketScannerTransaction marketScannerTransaction = new MarketScannerTransaction(numberOfRows, instrument, locationCode, scanCode, abovePrice, belowPrice, aboveVolume, averageOptionVolumeAbove,
	    						marketCapAbove, marketCapBelow, moodyRatingAbove, moodyRatingBelow,	spRatingAbove, spRatingBelow, maturityDateAbove, maturityDateBelow, couponRateAbove,
	    						couponRateBelow, excludeConvertible, scannerSettingPairs, stockTypeFilter);
	    
	    marketScannerTransaction.setTimeStamp(new Timestamp());
		// ID is used as a key to return a placeOrderTransaction type.
		// Placed in transactionMap HashMap (see MarketData.java) with put method
		marketData.getTransactionMap().put(id, marketScannerTransaction);
	    
		// Request scanner subscription from Trader Workstation using specified settings
		m_client.reqScannerSubscription(id, sb);
	}
	
	/**
	 * places an order, assumes that the connection is already established.
	 * 
	 * @param symbol is the ticker symbol (Google is GOOG, etc)
	 * @param sectype is the type of security (STK OPT FUT IND FOP CASH BAG)
	 * @param expiry is the date on which the OPTION expires
	 * @param strike is the strike price for the OPTION
	 * @param right is the right you're asking for for the OPTION (CALL PUT)
	 * @param multiplier String
	 * @param exchange String
	 * @param primaryExch String
	 * @param currency String
	 * @param localSymbol String
	 * @param includeExpired boolean
	 * @param secIdType String
	 * @param secId String
	 * @param action is the action of the order (BUY SELL)
	 * @param quantity is the amount of shares or contracts you are purchasing
	 * @param orderType String
	 * @param price is the price of each SHARE, be it options or stocks
	 * @param auxPrice double
	 * @param goodAfterTime String
	 * @param goodTillDate String
	 * @param whatIf is a boolean that is true for a What If? order and false to place an order
	 */
	public void placeOrder(String symbol, SecurityType secType, String expiry, 	double strike, String right, String multiplier,
			String exchange, String primaryExch, String currency, String localSymbol, boolean includeExpired, String secIdType, String secId,
			String action, int quantity, String orderType, double price, double auxPrice, String goodAfterTime, String goodTillDate, boolean whatIf) {
		updateGlobalId();
		
		// Create a new contract and a new order
		Contract c = new Contract();
		Order o = new Order();
		
		c.m_conId = 0;
		c.m_symbol = symbol;
		c.m_secType = secType.toString();
		c.m_expiry = expiry;
		c.m_strike = strike;
		c.m_right = right;
		c.m_multiplier = multiplier; //(secType.toString().equals("STK"))? "" : "100";
		c.m_exchange = exchange; //"SMART";
		c.m_primaryExch = primaryExch; //(secType.toString().equals("STK"))? "ISLAND" : "CBOE";
		c.m_currency = currency; //"USD";
		c.m_localSymbol = localSymbol;//"";
		c.m_includeExpired = includeExpired;//false;
		c.m_secIdType = secIdType;//"";
		c.m_secId = secId;//"";
		
		o.m_action = action;
		o.m_totalQuantity = quantity;
		o.m_orderType = orderType;//"LMT";
		o.m_lmtPrice = price;
		o.m_auxPrice = auxPrice;//0.0;
		o.m_goodAfterTime = goodAfterTime;//"";
		o.m_goodTillDate = goodTillDate;//"";
		o.m_whatIf = whatIf;
		
		PlaceOrderTransaction placeOrderTransaction = new PlaceOrderTransaction(symbol, secType, expiry, strike, right, action, quantity, price, whatIf);
		
		placeOrderTransaction.setTimeStamp(new Timestamp());
		// ID is used as a key to return a placeOrderTransaction type.
		// Placed in transactionMap HashMap (see MarketData.java) with put method
		System.out.println(marketData);
		marketData.getTransactionMap().put(id, placeOrderTransaction);
		
		m_client.placeOrder(id, c, o);
	}

	/*
     * Implemented methods from EWrapper interface to perform functions upon receiving pieces of data.
     * 
     * The methods listed below are all required to handle various possible responses by the EReader, 
     * which handles the receipt of all data that is read in by the socket (EClientSocket m_client)
     * You may leave any of these methods blank, but the ones corresponding to data received back from Trader
     * Workstation as a result any commands that you intend to use must be filled out or else nothing will be 
     * done with that data.
     * 
     * If the methods from the interface are not all included, the file will not compile because this
     * violates the interface contract with StockController.java
     */
	
	/**
	 * This is one of the methods called when data is returned from TWS for market data requests.
	 */
	public void tickPrice(int tickerId, int field, double price,
			int canAutoExecute) {
		// received price tick
		String msg = EWrapperMsgGenerator.tickPrice(tickerId, field, price,
				canAutoExecute);
		System.out.println(msg);
		
		RequestMarketDataTransaction rmdt = (RequestMarketDataTransaction)(marketData.getTransactionMap().get(tickerId));
		rmdt.setField(field);
		rmdt.setPrice(price);
		rmdt.setCanAutoExecute(canAutoExecute); 
		
	}

	/**
	 * Handles data returned from TWS by an option computation action.
	 */
	public void tickOptionComputation(int tickerId, int field,
			double impliedVol, double delta, double optPrice,
			double pvDividend, double gamma, double vega, double theta,
			double undPrice) {
		// received computation tick
		String msg = EWrapperMsgGenerator.tickOptionComputation(tickerId,
				field, impliedVol, delta, optPrice, pvDividend, gamma, vega,
				theta, undPrice);
		System.out.println(msg);
		
		RequestMarketDataTransaction rmdt = (RequestMarketDataTransaction)(marketData.getTransactionMap().get(tickerId));
		rmdt.setField(field);
		rmdt.setImpliedVol(impliedVol);
		rmdt.setDelta(delta);
		rmdt.setOptPrice(optPrice);
		rmdt.setPvDividend(pvDividend);
		rmdt.setGamma(gamma);
		rmdt.setVega(vega);
		rmdt.setTheta(theta);
		rmdt.setUndPrice(undPrice); 
	}

	/**
	 * This is one of the methods called when data is returned from TWS for market data requests.
	 */
	public void tickSize(int tickerId, int field, int size) {
		// received size tick
		String msg = EWrapperMsgGenerator.tickSize(tickerId, field, size);
		System.out.println(msg);
		
		RequestMarketDataTransaction rmdt = (RequestMarketDataTransaction)(marketData.getTransactionMap().get(tickerId));
		rmdt.setField(field);
		rmdt.setSize(size); 
	}

	/**
	 * This is one of the methods called when data is returned from TWS for market data requests.
	 */
	public void tickGeneric(int tickerId, int tickType, double value) {
		// received generic tick
		String msg = EWrapperMsgGenerator
				.tickGeneric(tickerId, tickType, value);
		System.out.println(msg);
		
		RequestMarketDataTransaction rmdt = (RequestMarketDataTransaction)(marketData.getTransactionMap().get(tickerId));
		rmdt.setTickType(tickType);
		rmdt.setValue(value); 
	}

	/**
	 * Returns a timestamp for returned market data.
	 */
	public void tickString(int tickerId, int tickType, String value) {
		// received String tick
		String msg = EWrapperMsgGenerator.tickString(tickerId, tickType, value);
		System.out.println(msg);
	}

	/**
	 * Handles end of a piece of data retrieved as part of a market snapshot from TWS.
	 */
	public void tickSnapshotEnd(int tickerId) {
		String msg = EWrapperMsgGenerator.tickSnapshotEnd(tickerId);
		System.out.println(msg);
		custom.marketDataEnd(tickerId);
	}

	/**
	 * Handles incoming data returned from TWS from a market scan request.
	 */
	public void scannerData(int reqId, int rank,
			ContractDetails contractDetails, String distance, String benchmark,
			String projection, String legsStr) {
		String msg = EWrapperMsgGenerator.scannerData(reqId, rank,
				contractDetails, distance, benchmark, projection, legsStr);
		System.out.println(msg);
		
		MarketScannerTransaction mst = (MarketScannerTransaction)(marketData.getTransactionMap().get(reqId));
		mst.setRank(rank);
		mst.addContract(contractDetails);
		mst.setDistance(distance);
		mst.setBenchmark(benchmark);
		mst.setProjection(projection);
		mst.setLegsStr(legsStr); 
	}

	/**
	 * Handles the indicator for the end of a market scan returned from TWS.
	 */
	public void scannerDataEnd(int reqId) {
		String msg = EWrapperMsgGenerator.scannerDataEnd(reqId);
		System.out.println(msg);
		custom.scannerDataEnd(reqId);
	}

	/**
	 * Handles data returned from TWS resulting from historical data requests.
	 */
	@Override
	public void historicalData(int reqId, String date, double open,
			double high, double low, double close, int volume, int count,
			double WAP, boolean hasGaps) {
		 String msg = EWrapperMsgGenerator.historicalData(reqId, date, open, high, low, close, volume, count, WAP, hasGaps);
		 System.out.println(msg);
		 
		 RequestHistoricalDataTransaction rHDT = (RequestHistoricalDataTransaction)(marketData.getTransactionMap().get(reqId));
			rHDT.setDate(date);
			rHDT.setOpen(open);
			rHDT.setHigh(high);
			rHDT.setLow(low);
			rHDT.setClose(close);
			rHDT.setVolume(volume);
			rHDT.setCount(count);
			rHDT.setWAP(WAP);
			rHDT.setHasGaps(hasGaps);
			
		if(date.startsWith("finished")) {
			custom.historicalDataEnd(reqId);
		}
	}

	/**
	 * Handles real time bar data returned from TWS as a result of requests for such data.
	 */
	@Override
	public void realtimeBar(int reqId, long time, double open, double high,
			double low, double close, long volume, double wap, int count) {
		String msg = EWrapperMsgGenerator.realtimeBar(reqId, time, open, high, low, close, volume, wap, count);
		System.out.println(msg);
		
		RequestRealTimeBarsTransaction rRTBT= (RequestRealTimeBarsTransaction)(marketData.getTransactionMap().get(reqId));
		rRTBT.setTime(time);
		rRTBT.setOpen(open);
		rRTBT.setHigh(high);
		rRTBT.setLow(low);
		rRTBT.setClose(close);
		rRTBT.setVolume(volume);
		rRTBT.setWap(wap);
		rRTBT.setCount(count);
		 
		m_client.cancelRealTimeBars(reqId);
		custom.realTimeBarsEnd(reqId);
	}
	
	/**
	 * This is one of the methods called when data is returned from TWS for market data requests.
	 */
	@Override
	public void tickEFP(int tickerId, int tickType, double basisPoints,
			String formattedBasisPoints, double impliedFuture, int holdDays,
			String futureExpiry, double dividendImpact, double dividendsToExpiry) {
		String msg = EWrapperMsgGenerator.tickEFP(tickerId, tickType, basisPoints, formattedBasisPoints, impliedFuture, holdDays, futureExpiry, dividendImpact, dividendsToExpiry);
		System.out.println(msg);
		
		RequestMarketDataTransaction rMDT= (RequestMarketDataTransaction)(marketData.getTransactionMap().get(tickerId));
		rMDT.setTickType(tickType);
		rMDT.setBasisPoints(basisPoints);
		rMDT.setFormattedBasisPoints(formattedBasisPoints);
		rMDT.setImpliedFuture(impliedFuture);
		rMDT.setHoldDays(holdDays);
		rMDT.setFutureExpiry(futureExpiry);
		rMDT.setDividendImpact(dividendImpact);
		rMDT.setDividendsToExpiry(dividendsToExpiry);
	}

	/**
	 * This is one of the methods called when data is returned from TWS for market data requests.
	 */
	@Override
	public void orderStatus(int orderId, String status, int filled,
			int remaining, double avgFillPrice, int permId, int parentId,
			double lastFillPrice, int clientId, String whyHeld) {
		String msg = EWrapperMsgGenerator.orderStatus(orderId, status, filled, remaining, avgFillPrice, permId, parentId, lastFillPrice, clientId, whyHeld);
		System.out.println(msg);
		custom.placeOrderEnd(orderId);
	}
	
	// Error message methods, do not edit.
	public void error(Exception e) {
		e.printStackTrace(System.err);
	}

	public void error(String str) {
		System.err.println(str);
	}

	public void error(int id, int errorCode, String errorMsg) {
		System.err.println(errorMsg);
	}

	public void connectionClosed() {
		System.err.println("--------------------- CLOSED ---------------------");
	}
	
	// Unimplemented methods start here, do not edit unless adding extra functionality.

	@Override
	public void openOrder(int orderId, Contract contract, Order order,
			OrderState orderState) {	
	}
	@Override
	public void openOrderEnd() {	
	}
	@Override
	public void contractDetails(int reqId, ContractDetails contractDetails) {	
	}
	@Override
	public void contractDetailsEnd(int reqId) {	
	}
	@Override
	public void scannerParameters(String xml) {
	}
	@Override
	public void currentTime(long time) {
	}
	@Override
	public void fundamentalData(int reqId, String data) {
	}
	@Override
	public void deltaNeutralValidation(int reqId, UnderComp underComp) {
	}
	@Override
	public void updateAccountValue(String key, String value, String currency,
			String accountName) {
	}
	@Override
	public void updatePortfolio(Contract contract, int position,
			double marketPrice, double marketValue, double averageCost,
			double unrealizedPNL, double realizedPNL, String accountName) {
	}
	@Override
	public void updateAccountTime(String timeStamp) {
	}
	@Override
	public void accountDownloadEnd(String accountName) {
	}
	@Override
	public void bondContractDetails(int reqId, ContractDetails contractDetails) {
	}
	@Override
	public void execDetails(int reqId, Contract contract, Execution execution) {
	}
	@Override
	public void execDetailsEnd(int reqId) {
	}
	@Override
	public void updateMktDepth(int tickerId, int position, int operation,
			int side, double price, int size) {
	}
	@Override
	public void updateMktDepthL2(int tickerId, int position,
			String marketMaker, int operation, int side, double price, int size) {
	}
	@Override
	public void updateNewsBulletin(int msgId, int msgType, String message,
			String origExchange) {
	}
	@Override
	public void managedAccounts(String accountsList) {
	}
	@Override
	public void receiveFA(int faDataType, String xml) {
	}
	
	/*
	 *  WindowListener interface contract methods, do not exit
	 */

	@Override
	public void windowClosing(WindowEvent e) {
		onClose();
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
	}
	@Override
	public void windowActivated(WindowEvent e) {	
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
	}
	@Override
	public void windowIconified(WindowEvent e) {
	}
	@Override
	public void windowOpened(WindowEvent e) {	
	}

	// Do not use
	@Override
	public void nextValidId(int orderId) {
	}

	public MarketData getMarketData() {
		return marketData;
	}
}
