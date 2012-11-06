package com.trainpuzzle.controller;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import com.trainpuzzle.ui.windows.MainMenu;
import com.trainpuzzle.ui.windows.WindowManager;

public class Application {
	
	private Logger logger = Logger.getLogger(Application.class);
	
	public static void main(String[]args) {
		BasicConfigurator.configure(); // loads log4j.xml configuration file
		
		Application thisApplication = new Application();
		GameController gameController = new GameController();
		
		thisApplication.createMainMenu(gameController);
	}
	
	public void createMainMenu(GameController gameController) {
		// assert campaignManager != null : "campaignManager not set";
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    //TODO: Switch to default view
			logger.error(e.getMessage(), e.fillInStackTrace());
		}
		WindowManager.getManager(gameController).setActiveWindow(new MainMenu(gameController));

		// WindowManager.		
		// LevelSelect levelSelect = new LevelSelect(gameController);
		// WindowManager.getManager().setActiveWindow(levelSelect);
		// WindowManager.getManager().updateWindows();
		// MainMenu mainMenu = new MainMenu(this, campaignManager);
		// WindowManager.getManager().setActiveWindow(mainMenu);
	}	
}