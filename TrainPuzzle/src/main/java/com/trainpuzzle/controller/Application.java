package com.trainpuzzle.controller;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.trainpuzzle.ui.windows.LevelSelect;
import com.trainpuzzle.ui.windows.LoadedLevel;
import com.trainpuzzle.ui.windows.MainMenu;
import com.trainpuzzle.ui.windows.WindowManager;

import com.trainpuzzle.exception.TrainCrashException;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.map.Board;
import com.trainpuzzle.model.map.Track;
import com.trainpuzzle.model.map.Location;


public class Application {
	private Logger logger = Logger.getLogger(Application.class);
	
	private CampaignManager campaignManager = new CampaignManager();
	private TrackPlacer trackBuilder;
	private Simulator simulator;
	
	private Level loadedLevel;
	private Level loadedLevelWithTrack;
	
	private LoadedLevel uiLoadedLevel;
	
	private boolean isTrainNotCrash;
	
	
	public static void main(String[]args) {
		BasicConfigurator.configure(); //loads log4j.xml configuration file
		
		Application application = new Application();
		
		application.createMainMenu();
	}

	
	public Application() {
	}
	
	public Application(int levelNumber, LoadedLevel uiLoadedLevel) {
		loadedLevel = campaignManager.loadLevel(levelNumber);
		trackBuilder = new TrackPlacer(loadedLevel);
		this.uiLoadedLevel = uiLoadedLevel;
		simulator = new Simulator(loadedLevel);
	}
	
	public void createMainMenu() {
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
		WindowManager.getManager().setActiveWindow(new LevelSelect(campaignManager));
		//WindowManager.getManager().setActiveWindow(new MainMenu(campaignManager));
		WindowManager.getManager().updateWindows();
	}
	
	public void runSimulation() {
		Location endPoint = loadedLevel.getEndLocation();
		isTrainNotCrash = true;
	    ActionListener actionListener;
	    Timer t;
	    
	    uiLoadedLevel.redrawTrain(simulator.getTrain());
	    
	    actionListener = new ActionListener() {
	         public void actionPerformed(ActionEvent actionEvent) {
	        	move();
	 			uiLoadedLevel.redrawTrain(simulator.getTrain());
	        	if(simulator.isVictoryConditionsSatisfied() || !isTrainNotCrash) {
	        		((Timer)actionEvent.getSource()).stop();
	        	}
	         }
	    };
	    t = new Timer(200, actionListener);
	    t.start();	
	}
	
	public void placeTrack(Track track, int row, int column) {
		try {
			trackBuilder.placeTrack(track, row, column);
			loadedLevelWithTrack = trackBuilder.getLevelWithTrack();
		} catch (Exception e) {
			logger.error(e.getMessage(), e.fillInStackTrace());
		}
	}
	
	public void removeTrack(int row, int column) {
		try {
			trackBuilder.removeTrack(row, column);
			loadedLevelWithTrack = trackBuilder.getLevelWithTrack();
		} catch (Exception e) {
			logger.error(e.getMessage(), e.fillInStackTrace());
		}
	}
	
	
	private void move() {
    	try {
			simulator.proceedNextTile();
		} catch (TrainCrashException e) {
			e.printStackTrace();
			isTrainNotCrash = false;
		}
	}
	
	/* Getters and Setters */
	
	public CampaignManager getCampaignManager() {
		return campaignManager;
	}
	
	public Board getBlankMap() {
		return loadedLevel.getMap();
	}
	
	public Board getTrackMap() {
		return loadedLevelWithTrack.getMap();
	}
	

}
