package com.trainpuzzle.controller;

import org.apache.log4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.Thread;

import javax.swing.Timer;

import com.trainpuzzle.ui.windows.LoadedLevel;

import com.trainpuzzle.exception.TrainCrashException;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.map.Map;
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
	
	private LevelLoader levelLoader;
	private TrackBuilder trackBuilder;
	private Simulator simulator;
	
	private Level loadedLevel;
	private Level loadedLevelWithTrack;
	
	private LoadedLevel uiLoadedLevel;
	
	/* Public Interface */
	
	public Application(int levelNumber, LoadedLevel uiLoadedLevel) {
		levelLoader = new LevelLoader();
		loadedLevel = levelLoader.loadLevel(levelNumber);
		trackBuilder = new TrackBuilder(loadedLevel);
		this.uiLoadedLevel = uiLoadedLevel;
		simulator = new Simulator(loadedLevel);
	}
	
	public void runSimulation() {
		move();
	}
	
	public void placeTrack(Track track, int latitude, int longitude) {
		trackBuilder.placeTrack(track, latitude, longitude);
		loadedLevelWithTrack = trackBuilder.getLevelWithTrack();
	}
	
	/* Private Functions */
	
	private void move() {
		Location endPoint = loadedLevel.getEndLocation();
		boolean isTrainNotCrash = true;
		
	    ActionListener actionListener = new ActionListener() {
	         public void actionPerformed(ActionEvent actionEvent) {
	        	try {
	    			uiLoadedLevel.redrawTrain(simulator.getTrain());
					simulator.proceedNextTile();
				} catch (TrainCrashException e) {
					e.printStackTrace();
				}
	         }
	    };
	       
	    Timer t = new Timer(200, actionListener);
	    t.start();		
	}
	
	/* Getters and Setters */
	
	public Map getBlankMap() {
		return loadedLevel.getMap();
	}
	
	public Map getTrackMap() {
		return loadedLevelWithTrack.getMap();
	}
}
