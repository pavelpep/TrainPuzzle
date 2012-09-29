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
	Simulator(Level level){
		this.map = level.getMap();
		this.train= new Train();
		int[] startPoint= map.getStartPoint();
		this.train.setLocation(startPoint[0],startPoint[1]);
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
		
		return false;
	}
	public void setTrain(Train train){
		this.train = train;
	}
	public Train getTrain(){
		return this.train;
	}

}