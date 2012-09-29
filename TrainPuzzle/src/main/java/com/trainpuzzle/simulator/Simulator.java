package com.trainpuzzle.simulator;
import com.trainpuzzle.model.map.*;
/**
 * 
 * @author huachuandeng
 *
 */
public class Simulator {
	private Map map;
	private Train trian;
	Simulator(Level level){
		
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
	
	private void Stop()
	{
		
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Train getTrian() {
		return trian;
	}

	public void setTrian(Train trian) {
		this.trian = trian;
	}
}