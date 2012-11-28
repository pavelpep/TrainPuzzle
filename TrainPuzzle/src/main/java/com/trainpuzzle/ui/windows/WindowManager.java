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
		windowList.add(window);
		window.setVisible(true);
	}
	
	public Window getActiveWindow() {
		return windowList.peek();
	}
	
	public void showPreviousWindow() {
		windowList.pop().dispose();
		if(!windowList.isEmpty()) {
			windowList.peek().setVisible(true);
			windowList.peek().revalidate();
		}
	}
	
	public boolean hasPreviousWindow() {
		return windowList.size() > 1;
	}
	
	public void exit() {
		System.exit(0);
	}
}