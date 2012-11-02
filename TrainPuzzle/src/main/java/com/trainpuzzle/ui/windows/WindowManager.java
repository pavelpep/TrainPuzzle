/* Class Name: WindowManager.java
 * Date Created: September 21, 2012
 * Date Edited: September 21, 2012, 23:30
 * Last Edited by: Joey Au-Yeung
 */

package com.trainpuzzle.ui.windows;

import java.awt.*;
import java.util.LinkedList;

import javax.swing.*;

import com.trainpuzzle.controller.GameController;

/* This is the Window Manager for the Train Track Puzzle game. It manages windows,
 * modules and global data used by this application.
 * Only ONE Window Manager can exist at any time during the program.
 */

public class WindowManager {
	// Fields
	private static WindowManager manager;
	
	private GameController gameController;
	private Window activeWindow;
	private Window previousWindow;
	private LinkedList<Window> windowList; 
	private boolean isWindowChanged;
	
	// Constructors
	private WindowManager(GameController gameController) {
		this.gameController = gameController;
		windowList = new LinkedList<Window>();
		activeWindow = null;
		previousWindow = null;
		isWindowChanged = false;
	}
	
	// Methods
	public static synchronized WindowManager getManager(GameController gameController) {
		if (manager == null) {
			manager = new WindowManager(gameController);
		}
		return manager;
	}
	
	public void setActiveWindow(Window window) {
		if(!windowList.isEmpty()) {
			windowList.getLast().setVisible(false);
		}
		window.setVisible(true);
		windowList.add(window);
		System.out.println(windowList.size());
	}
	
	public void showPreviousWindow() {
		windowList.getLast().dispose();
		windowList.removeLast();
		windowList.getLast().setVisible(true);
	}
	
	/*
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
	*/
}