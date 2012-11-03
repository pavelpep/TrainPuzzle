package com.trainpuzzle.model.level;

public class Campaign {

	private int firstLevel = 1;
	private int lastLevel = 10;
	private int currentLevel = 1;
	
	public Campaign(){
		
	}
	
	public void completeCurrentLevel(){
		currentLevel = currentLevel + 1;
	}
	
	public int getCurrentLevel(){
		return this.currentLevel;
	}
	
	
}
