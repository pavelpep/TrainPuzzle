/* Class Name: StartGame.java
 * Date Created: September 21, 2012
 * Date Edited: September 21, 2012, 23:30
 * Last Edited by: Joey Au-Yeung
 */

package trainpuzzle.ui.windows;

// Main Method to start the game... 
public class StartGame {
	public static void main(String[]args) {
		// Loads Main Menu upon start
		WindowManager.getManager().setActiveWindow(new MainMenu());
		WindowManager.getManager().updateWindows();
	}
}


