package com.trainpuzzle.controller;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.trainpuzzle.ui.windows.LevelSelect;
import com.trainpuzzle.ui.windows.LoadedLevelScreen;
import com.trainpuzzle.ui.windows.MainMenu;
import com.trainpuzzle.ui.windows.WindowManager;

import com.trainpuzzle.exception.TrainCrashException;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.map.Board;
import com.trainpuzzle.model.map.Track;
import com.trainpuzzle.model.map.Location;


public class Application {
	private Logger logger = Logger.getLogger(Application.class);
	
	
	
	public static void main(String[]args) {
		BasicConfigurator.configure(); //loads log4j.xml configuration file
		
		Application thisApplication = new Application();
		GameController gameController = new GameController();
		
		thisApplication.createMainMenu(gameController);
	}
	
	public Application() {

	}
	
	public void createMainMenu(GameController gameController) {
		/*
		assert campaignManager != null : "campaignManager not set";
		
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
		*/
		
		
		LevelSelect levelSelect = new LevelSelect(gameController);
		WindowManager.getManager().setActiveWindow(levelSelect);
		
		
		//MainMenu mainMenu = new MainMenu(this, campaignManager);
		//WindowManager.getManager().setActiveWindow(mainMenu);
		WindowManager.getManager().updateWindows();
	}
	
}
