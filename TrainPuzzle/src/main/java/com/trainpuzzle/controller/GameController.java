package com.trainpuzzle.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.trainpuzzle.exception.CannotPlaceTrackException;
import com.trainpuzzle.exception.CannotRemoveTrackException;
import com.trainpuzzle.exception.TrainCrashException;
import com.trainpuzzle.model.board.Board;
import com.trainpuzzle.model.board.Track;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.observe.Observable;
import com.trainpuzzle.observe.Observer;
import com.trainpuzzle.ui.windows.LoadedLevelScreen;

public class GameController implements Observable{
	private Logger logger = Logger.getLogger(Application.class);
	private Set<Observer> observerList = new HashSet<Observer>();
	
	private CampaignManager campaignManager;
	private TrackPlacer trackPlacer;
	private Simulator simulator;
	Timer timer;
	
	
	private Level level;
	private Level loadedLevelWithTrack;
	
	private boolean trainCrashed;
	
	public GameController(){
		
	}
	
	public void startGame(int levelNumber){
		campaignManager = new CampaignManager();
		level = campaignManager.loadLevel(levelNumber);
		trackPlacer = new TrackPlacer(level);
		simulator = new Simulator(level);
		
		System.out.println(simulator.getTrain().getHeading());
	}
	
	public Simulator getSimulator(){
		return simulator;
	}

	public void runSimulation() {
		trainCrashed = false;
	    ActionListener actionListener;
	    
	    
	    //uiLoadedLevel.redrawTrain(simulator.getTrain());
	    
	    actionListener = new ActionListener() {
	         public void actionPerformed(ActionEvent actionEvent) {
	        	move();
	 			//uiLoadedLevel.redrawTrain(simulator.getTrain());
	        	if(simulator.isVictoryConditionsSatisfied() || trainCrashed) {
	        		((Timer)actionEvent.getSource()).stop();
	        	}
	         }
	    };
	    timer = new Timer(200, actionListener);
	    timer.start();
	}
	
	public void resetSimulation() {
		timer.stop();
		trainCrashed = false;
	    simulator.reset();
	    
	}
	public void placeTrack(Track track, int row, int column) {
		try {
			trackPlacer.placeTrack(track, row, column);
			loadedLevelWithTrack = trackPlacer.getLevelWithTrack();
			
		} catch (CannotPlaceTrackException e) {
			logger.error(e.getMessage(), e.fillInStackTrace());
			for(Observer observer : observerList) {
				observer.notifyChange(e);
			}
		}
	}
	
	public void removeTrack(int row, int column) {
		try {
			trackPlacer.removeTrack(row, column);
			loadedLevelWithTrack = trackPlacer.getLevelWithTrack();
		} catch (CannotRemoveTrackException e) {
			logger.error(e.getMessage(), e.fillInStackTrace());
			for(Observer observer : observerList) {
				observer.notifyChange(e);
			}
		}
	}
	
	private void move() {
    	try {
			simulator.proceedNextTile();
		} catch (TrainCrashException e) {
			e.printStackTrace();
			trainCrashed = true;
			//JOptionPane.showMessageDialog(null, "The train has crashed!");
			for(Observer observer : observerList) {
				observer.notifyChange(e);
			}
			
		}
	}
	
	/* Getters and Setters */
	
	public CampaignManager getCampaignManager() {
		return campaignManager;
	}
	
	public Board getBlankMap() {
		return level.getBoard();
	}
	
	public Board getTrackMap() {
		return loadedLevelWithTrack.getBoard();
	}
	
	public Level getLevel() {
		return this.level;
	}
	
	public void register(Observer observer){
		observerList.add(observer);
	}
	
	public void notifyAllObservers(){
		for(Observer observer : observerList) {
			observer.notifyChange(this);
		}
	}
	
}
