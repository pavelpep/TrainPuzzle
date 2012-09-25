/* Class Name: StartGame.java
 * Date Created: September 21, 2012
 * Date Edited: September 21, 2012, 23:30
 * Last Edited by: Joey Au-Yeung
 */

package trainpuzzle.ui.windows;

import javax.swing.UIManager;
import javax.swing.UIManager.*;

// Main Method to start the game... 
public class StartGame {
	public static void main(String[]args) {
		// Loads Main Menu upon start
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}		
		WindowManager.getManager().setActiveWindow(new MainMenu());
		WindowManager.getManager().updateWindows();
	}
}


