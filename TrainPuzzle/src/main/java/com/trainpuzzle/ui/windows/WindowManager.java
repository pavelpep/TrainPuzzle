/* Class Name: WindowManager.java
 * Date Created: September 21, 2012
 * Date Edited: September 21, 2012, 23:30
 * Last Edited by: Joey Au-Yeung
 */

package com.trainpuzzle.ui.windows;

import java.util.Stack;


public class WindowManager {
	private static WindowManager manager;
	private Stack<Window> windowList; 

	private WindowManager() {
		windowList = new Stack<Window>();
	}

	public static synchronized WindowManager getManager() {
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
		windowList.pop().dispose();
		if(!windowList.isEmpty()) {
			windowList.peek().setVisible(true);
		}
	}
	
	public boolean hasPreviousWindow() {
		return windowList.size() > 1;
	}
	
	public void exit() {
		System.exit(0);
	}
}