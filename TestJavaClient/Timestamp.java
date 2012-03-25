package TestJavaClient;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Timestamp implements Comparable, Serializable {
	private static final long serialVersionUID = 4770641853221522355L;
	String date;
	String time;
	
	/**
	 * Timestamp is a class that will contain the date and time of a transaction.
	 * 
	 * the format is yyyymmdd  hh:mm:ss
	 */
	public Timestamp() {
		DateFormat df = new SimpleDateFormat("yyyyMMdd  HH:mm:ss");
		Date d = new Date();
		String temp[] = df.format(d).split("  ");
		date = temp[0];
		time = temp[1];
	}
	
	/**
	 * Compares the date of one Timestamp object (this)
	 * to the date of another Timestamp object (other)
	 * and returns -1 if this is less than other, 1 if
	 * this is greater than other, and 0 if they are equal
	 * 
	 * @param t
	 * @return
	 */
	public int compareDates(Timestamp t) {
		int year = Integer.parseInt(date.substring(0,3));
		int month = Integer.parseInt(date.substring(4,5));
		int day = Integer.parseInt(date.substring(6,7));
		
		int oYear = Integer.parseInt(date.substring(0,3));
		int oMonth = Integer.parseInt(date.substring(4,5));
		int oDay = Integer.parseInt(date.substring(6,7));
		
		if(year < oYear) {
			return -1;
		}
		if(year > oYear) {
			return 1;
		}
		if(month < oMonth) {
			return -1;
		}
		if(month > oMonth) {
			return 1;
		}
		if(day < oDay) {
			return -1;
		}
		if(day > oDay) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * compares the time of one Timestamp object (this) and
	 * another Timestamp object (other). If this time is greater,
	 * returns a positive integer (1). If this time is less, 
	 * returns a negative integer (-1). If the time is the same, 
	 * returns 0.
	 * 
	 * @param t, the other Timestamp
	 * @return
	 */
	public int compareTimes(Timestamp t) {
		System.out.println(time);
		System.out.println(time);
		
		String timeParts[] = time.split(":");
		int hours = Integer.parseInt(timeParts[0]);
		int minutes = Integer.parseInt(timeParts[1]);
		int seconds = Integer.parseInt(timeParts[2]);
		
		String otherTP[] = t.getTime().split(":");
		int oHours = Integer.parseInt(otherTP[0]);
		int oMinutes = Integer.parseInt(otherTP[1]);
		int oSeconds = Integer.parseInt(otherTP[2]);
		
		if(hours < oHours) {
			return -1;
		}
		if(hours > oHours) {
			return 1;
		}
		if(minutes < oMinutes) {
			return -1;
		}
		if(minutes > oMinutes) {
			return 1;
		}
		if(seconds < oSeconds) {
			return -1;
		}
		if(seconds > oSeconds) {
			return 1;
		}
		return 0;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getTime() {
		return time;
	}
	
	/**
	 * returns how many hours apart this is from the other timestamp.
	 * 
	 * @param t
	 * @return
	 */
	public int hoursApart(Timestamp t) {
		int hours = 0;
		int oHours = 0;
		try {
			hours = Integer.parseInt(time.split(":")[0]);
			oHours = Integer.parseInt(t.getTime().split(":")[0]);
		} catch(Exception e) {
			System.out.println("Times are causing exceptions in hoursApart in the Timestamp class.");
		}
		
		return(hours - oHours);
	}
	
	/**
	 * returns how many hours apart 
	 * @param t
	 * @return
	 */
	public int minutesApart(Timestamp t) {
		int minutes = 0;
		int oMinutes = 0;
		try {
			minutes = Integer.parseInt(time.split(":")[1]);
			oMinutes = Integer.parseInt(t.getTime().split(":")[1]);
		} catch(Exception e) {
			System.out.println("Times are causing exceptions in minutesApart in the Timestamp class.");
		}
		
		return (minutes - oMinutes + (hoursApart(t) * 60));
	}
	
	public int secondsApart(Timestamp t) {
		int seconds = 0;
		int oSeconds = 0;
		try {
			seconds = Integer.parseInt(time.split(":")[2]);
			oSeconds = Integer.parseInt(t.getTime().split(":")[2]);
		} catch(Exception e) {
			System.out.println("Times are causing exceptions in secondsApart in the Timestamp class.");
		}
		
		return (seconds - oSeconds + (minutesApart(t) * 60));
	}
	
	/**
	 * sets the date to the given date after checking the format and
	 * believability of the numbers. (e.g. date must be within acceptable 
	 * range for that month)
	 * 
	 * @param newDate, String, formatted yyyymmdd
	 * @throws TimeFormatException
	 */
	public void setDate(String newDate) {
		String dateParts[] = new String[3];
		
		dateParts[0] = newDate.substring(0,4);
		dateParts[1] = newDate.substring(4,6);
		dateParts[2] = newDate.substring(6,8);
		
		try {
			
			if(dateParts.length != 3) {
				throw new TimeFormatException("TimeFormatException incorrect date format");
			}
			
			int year = Integer.parseInt(dateParts[0]);
			int month = Integer.parseInt(dateParts[1]);
			int day = Integer.parseInt(dateParts[2]);
			
			if(month < 1 || month > 12) {
				throw new TimeFormatException("TimeFormatException month out of bounds");
			}
			
			switch(month)
			{
			case 1:		//January
				if(day < 1 || day > 31) {
					throw new TimeFormatException("TimeFormatException day out of bounds");
				}
				break;
			case 2:		//February
				if(day < 1 || ((day > 29 && year%4 == 0) || (day > 28 && year%4 != 0))) {
					throw new TimeFormatException("TimeFormatException day out of bounds");
				}
				break;
			case 3:		//March
				if(day < 1 || day > 31) {
					throw new TimeFormatException("TimeFormatException day out of bounds");
				}
				break;
			case 4:		//April
				if(day < 1 || day > 30) {
					throw new TimeFormatException("TimeFormatException day out of bounds");
				}
				break;
			case 5:		//May
				if(day < 1 || day > 31) {
					throw new TimeFormatException("TimeFormatException day out of bounds");
				}
				break;
			case 6:		//June
				if(day < 1 || day > 30) {
					throw new TimeFormatException("TimeFormatException day out of bounds");
				}
				break;
			case 7:		//July
				if(day < 1 || day > 31) {
					throw new TimeFormatException("TimeFormatException day out of bounds");
				}
				break;
			case 8:		//August
				if(day < 1 || day > 31) {
					throw new TimeFormatException("TimeFormatException day out of bounds");
				}
				break;
			case 9:		//September
				if(day < 1 || day > 30) {
					throw new TimeFormatException("TimeFormatException day out of bounds");
				}
				break;
			case 10:	//October
				if(day < 1 || day > 31) {
					throw new TimeFormatException("TimeFormatException day out of bounds");
				}
				break;
			case 11:	//November
				if(day < 1 || day > 30) {
					throw new TimeFormatException("TimeFormatException day out of bounds");
				}
				break;
			case 12:	//December
				if(day < 1 || day > 31) {
					throw new TimeFormatException("TimeFormatException day out of bounds");
				}
				break;
			default:
				throw new TimeFormatException("TimeFormatException month out of bounds");
			}

			date = newDate;
			
		}
		catch(Exception e) {
			
		}
	}
	
	/**
	 * setTime sets the time to the given time after
	 * checking that the format was correct
	 */
	public void setTime(String newTime) {
		String timeParts[] = newTime.split(":");
		
		try {
			
			if(timeParts.length != 3) {
				throw new TimeFormatException("TimeFormatException incorrect time format");
			}
			
			int hours = Integer.parseInt(timeParts[0]);
			int minutes = Integer.parseInt(timeParts[1]);
			int seconds = Integer.parseInt(timeParts[2]);
			
			if(hours > 23 || hours < 0) {
				throw new TimeFormatException("TimeFormatException hours out of bounds");
			}
			
			if(minutes > 59 || minutes < 0) {
				throw new TimeFormatException("TimeFormatException minutes out of bounds");
			}
			
			if(seconds > 59 || seconds < 0) {
				throw new TimeFormatException("TimeFormatException seconds out of bounds");
			}
			
			time = newTime;
			System.out.println(time);
		}
		catch(Exception e) {
			
		}
	}
	
	public String toString() {
		return date + "  " + time;
	}
	
	/**
	 * compareTo takes a Timestamp object and compares its date and
	 * time to another Timestamp object's date and time.
	 * 
	 * @param arg0, the other Timestamp object. Must be a Timestamp.
	 */
	@Override
	public int compareTo(Object arg0) {
		Timestamp otherStamp = (Timestamp)arg0;
		
		if(compareDates(otherStamp) == -1) {
			return -1;
		}
		if(compareDates(otherStamp) == 1) {
			return 1;
		}
		if(compareTimes(otherStamp) == -1) {
			return -1;
		}
		if(compareTimes(otherStamp) == 1) {
			return 1;
		}
		return 0;
	}
	
	private class TimeFormatException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6432349077759830757L;
		
		public TimeFormatException(String problem) {
			System.out.println(problem);
		}
		
	}
}
