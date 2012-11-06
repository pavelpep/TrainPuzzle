package com.trainpuzzle.model.level;

import com.trainpuzzle.factory.LevelFactory;

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
	
	public Level loadLevel(int levelNumber) {
		
		Level levelLoaded;
		LevelFactory levelFactory = new LevelFactory();
		
		if(levelNumber == 1){
			levelLoaded = levelFactory.createLevelOne();
		}else if(levelNumber == 2){
			levelLoaded = levelFactory.createLevelTwo();
		}else if(levelNumber == 3){
			levelLoaded = levelFactory.createLevelThree();
		}else{
			 //just in case
			levelLoaded = levelFactory.createLevelOne();
		}
		return levelLoaded;
	}
}
