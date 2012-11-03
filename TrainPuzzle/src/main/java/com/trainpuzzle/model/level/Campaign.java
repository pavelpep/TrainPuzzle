package com.trainpuzzle.model.level;

public class Campaign implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private int firstLevel = 1;
	private int lastLevel = 10;
	private int currentLevel = 1;
	
	public Campaign(){
		
	}
	
	public void completeCurrentLevel(){
		if(currentLevel >= firstLevel && currentLevel <= lastLevel){
				currentLevel = currentLevel + 1;
		}
	}
	
	public int getCurrentLevel(){
		return this.currentLevel;
	}
	
	
}
