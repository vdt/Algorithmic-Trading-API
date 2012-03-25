package TestJavaClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TimeKeeper {
	private ArrayList<CustomTimer> timers; // List of periodic action timers
	private CustomUserModule custom;
	private CustomTimer scheduleTimer;
	private String currentTime;
	
	private final int SECOND = 1000;
	
	private class TimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String source = e.getSource().toString();
			custom.timerCalled(source);
		}
	}
	private class MinuteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			DateFormat dateFormat = new SimpleDateFormat("HH:mm");
			Date date = new Date();	
			
			currentTime = dateFormat.format(date);
			custom.scheduledEvent(currentTime);
		}
	}
	
	public TimeKeeper(CustomUserModule c) {
		timers = new ArrayList<CustomTimer>();
		new java.util.Timer();
		custom = c;
		
		MinuteListener m = new MinuteListener();
		scheduleTimer = new CustomTimer(60 * SECOND, m);
		scheduleTimer.start();
	}
	
	public void scheduleAction(int interval, String action) {
		TimerListener t = new TimerListener();
		CustomTimer timer = new CustomTimer(interval * SECOND, t);
		timer.setName(action);
		timer.start();
		timers.add(timer);
	}
	
	public void stopTimers() {
		for (int i = 0; (i <= timers.size() - 1); i++) {
			timers.get(i).stop();
		}
	}
	
	/**
	 * this method allows us to retrieve yesterday's close 
	 * @author lkjaero, jhartless
	 */
	public String getYesterdaysClose() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMM");// allows us to create a new dateFormat object which will be of the year and month (excluding date)
		Date date = new Date();// date is created as a new date object
		String dateStringMinusDay = dateFormat.format(date);// sets up a variable by formatting our date object by having dateFormat format it for us
		
		DateFormat dateFormatTwo = new SimpleDateFormat("dd");// allows us to create a new dateFormat object which will be of the date only
		Date dateTwo = new Date();// date is created as a new date object
		String dayString = dateFormatTwo.format(dateTwo);// sets up a variable by formatting our date object by having dateFormattwo format it for us
		char firstDayDigit = dayString.charAt(0);// first day is the tens digit for our date selection
		char lastDayDigit = dayString.charAt(1);// last day is the ones digit for our date selection
		if (lastDayDigit == '0') {// if our current last day is of 0
			lastDayDigit = '9';// set it to be the day before == 9
			firstDayDigit--;// decrement first day digit
		}
		else {
			lastDayDigit--;// otherwise automatically decrement
		}
		
		char[] dayArray = {firstDayDigit, lastDayDigit};// create a char array with first and last day as the elements in our arrray
		dayString = new String(dayArray);// set dayString to be our array
		
		String dateString = dateStringMinusDay + dayString + " 15:00:00 CST";// sets up YYYYMM + DD + 3:00 for the previous day
		
		return dateString;// return value for us
	}

	public String getCurrentTime() {
		return currentTime;
	}
	
}
