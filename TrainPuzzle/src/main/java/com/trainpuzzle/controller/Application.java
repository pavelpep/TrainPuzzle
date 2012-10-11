package com.trainpuzzle.controller;

import org.apache.log4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.trainpuzzle.ui.windows.LoadedLevel;

import com.trainpuzzle.exception.TrainCrashException;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.map.Board;
import com.trainpuzzle.model.map.Track;
import com.trainpuzzle.model.map.Location;

/**
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 */
public class Application {
	private Logger logger = Logger.getLogger(Application.class);
	
	private CampaignManager levelLoader;
	private TrackPlacer trackBuilder;
	private Simulator simulator;
	
	private Level loadedLevel;
	private Level loadedLevelWithTrack;
	
	private LoadedLevel uiLoadedLevel;
	
	private boolean isTrainNotCrash;
	
	/* Public Interface */
	
	public Application(int levelNumber, LoadedLevel uiLoadedLevel) {
		levelLoader = new CampaignManager();
		loadedLevel = levelLoader.loadLevel(levelNumber);
		trackBuilder = new TrackPlacer(loadedLevel);
		this.uiLoadedLevel = uiLoadedLevel;
		simulator = new Simulator(loadedLevel);
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
	        	if(simulator.isVictoryConditionsSatisfied() || !isTrainNotCrash){
	        		((Timer)actionEvent.getSource()).stop();
	        	}
	         }
	    };
	    t = new Timer(200, actionListener);
	    t.start();	
	}
	
	public void placeTrack(Track track, int latitude, int longitude) {
		trackBuilder.placeTrack(track, latitude, longitude);
		loadedLevelWithTrack = trackBuilder.getLevelWithTrack();
	}
	
	/* Private Functions */
	
	private void move() {
		
    	try {
			simulator.proceedNextTile();
		} catch (TrainCrashException e) {
			e.printStackTrace();
			isTrainNotCrash = false;
		}
	}
	
	/* Getters and Setters */
	
	public Board getBlankMap() {
		return loadedLevel.getMap();
	}
	
	public Board getTrackMap() {
		return loadedLevelWithTrack.getMap();
	}
}
