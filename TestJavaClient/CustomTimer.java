package TestJavaClient;

import java.awt.event.ActionListener;

import javax.swing.Timer;

public class CustomTimer extends Timer {

	private static final long serialVersionUID = -2053134062115711151L;
	String name;

	public CustomTimer(int delay, ActionListener listener) {
		super(delay, listener);
	}
	
	public String toString() {
		return name;
	}
	
	public void setName(String n) {
		name = n;
	}
}
