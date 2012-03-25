package TestJavaClient;

import java.awt.Component;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Main.java
 * 
 * Starts up the automated trading system and handles critical errors.
 * 
 * @author gkoch
 *
 */

public class Main {

    // This method is called to start the application
    public static void main (String args[]) {
        @SuppressWarnings("unused")
		Controller controller = new Controller();
    }

    // Handles strings and errors, do not change.
    static public void inform( final Component parent, final String str) {
        if( SwingUtilities.isEventDispatchThread() ) {
        	showMsg( parent, str, JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            SwingUtilities.invokeLater( new Runnable() {
				public void run() {
					showMsg( parent, str, JOptionPane.INFORMATION_MESSAGE);
				}
			});
        }
    }
    
    // This function pops up a dlg box displaying a message based on what str is
    static private void showMsg( Component parent, String str, int type) {    	
        JOptionPane.showMessageDialog( parent, str, "IB Java Test Client", type);
    }
}