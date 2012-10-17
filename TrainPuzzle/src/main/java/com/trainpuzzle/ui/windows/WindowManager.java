/* Class Name: WindowManager.java
 * Date Created: September 21, 2012
 * Date Edited: September 21, 2012, 23:30
 * Last Edited by: Joey Au-Yeung
 */

package com.trainpuzzle.ui.windows;

import java.awt.*;
import javax.swing.*;

/* This is the Window Manager for the Train Track Puzzle game. It manages windows,
 * modules and global data used by this application.
 * Only ONE Window Manager can exist at any time during the program.
 */

public class WindowManager {
	// Fields
	private static WindowManager manager;
	
	private Window activeWindow;
	private Window previousWindow;
	private boolean isWindowChanged;
	
	// Constructors
	private WindowManager() {
		activeWindow = null;
		previousWindow = null;
		isWindowChanged = false;
	}
	
	// Methods
	public static synchronized WindowManager getManager() {
		if (manager == null) {
			manager = new WindowManager();
		}
		return manager;
	}
	
	public Window getActiveWindow() {
		return activeWindow;
	}
	
	public Window setActiveWindow(Window window) {
		if (activeWindow != window) {
			activeWindow = window;
			isWindowChanged = true;
		}
		return activeWindow;
	}
	
	public Window getPreviousWindow() {
		return previousWindow;
	}
	
	public Window setPreviousWindow(Window window) {
		previousWindow = window;
		return previousWindow;
	}
	
	public boolean getIsWindowChanged() {
		return isWindowChanged;
	}
	
	public void setIsWindowChanged(boolean changed) {
		isWindowChanged = changed;
	}
	
	public boolean updateWindows() {
		if (isWindowChanged) {
			if (previousWindow != null) {
				previousWindow.dispose();
			}
			previousWindow = activeWindow;
			activeWindow.create();
			
			isWindowChanged = false;
		}
		return (!isWindowChanged);
	}
}