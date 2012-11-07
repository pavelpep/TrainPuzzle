/* Class Name: WindowManager.java
 * Date Created: September 21, 2012
 * Date Edited: September 21, 2012, 23:30
 * Last Edited by: Joey Au-Yeung
 */

package com.trainpuzzle.ui.windows;

import java.util.Stack;

import com.trainpuzzle.controller.GameController;

/* This is the Window Manager for the Train Track Puzzle game. It manages windows,
 * modules and global data used by this application.
 * Only ONE Window Manager can exist at any time during the program.
 */

public class WindowManager {
	// Fields
	private static WindowManager manager;
	
	private Stack<Window> windowList; 
	
	// Constructors
	private WindowManager() {
		windowList = new Stack<Window>();
	}
	
	// Methods
	public static synchronized WindowManager getManager(GameController gameController) {
		if (manager == null) {
			manager = new WindowManager();
		}
		return manager;
	}
	
	public void setActiveWindow(Window window) {
		if(!windowList.isEmpty()) {
			windowList.peek().setVisible(false);
		}
		window.setVisible(true);
		windowList.add(window);
	}
	
	public Window getActiveWindow() {
			return windowList.peek();
	}
	
	public void showPreviousWindow() {
		windowList.peek().dispose();
		windowList.pop();
		windowList.peek().setVisible(true);
	}
}