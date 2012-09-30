package com.trainpuzzle.controller;
import com.trainpuzzle.model.map.*;
/**
 * 
 * @author huachuandeng
 *
 */
public class Simulator {
	private Map map;
	private Train train;
	Simulator(TrackBuilder trackBuilder){
		this.map = trackBuilder.getMap();
		this.train= new Train();
		int[] startPoint= map.getStartPoint();
		this.train.setLocation(startPoint[0],startPoint[1]);
		this.train.setHeading(Track.Heading.EAST);
	}
	/**
	 * A function to check whether the user win the game
	 * @return a boolean to tell whether the train finish all requirements 
	 */
	private boolean isVictory(){
		return true;
	}

	/**
	 *  to simulate the turn run on map 
	 */
	public void Run() 
	{
		
	}
	public boolean goNextTrack(){
		int[] location = train.getLocation();
		
		return false;
	}
	/*public void setTrain(Train train){
		this.train = train;
	}*/
	public Train getTrain(){
		return this.train;
	}

}