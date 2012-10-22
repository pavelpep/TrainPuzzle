package com.trainpuzzle.controller;

import com.trainpuzzle.model.level.*;


public class CampaignManager {
	private Level levelLoaded;
	private LevelGenerator levelGenerator = new LevelGenerator();
	
	public CampaignManager(){

	}
	
	public Level loadLevel(int levelNumber) {
		if(levelNumber == 1){
			this.levelLoaded = levelGenerator.createLevelOne();
		}else if(levelNumber == 2){
			this.levelLoaded = levelGenerator.createLevelTwo();
		}else{
			//if some weird level number is given, return level 1 just in case 
			this.levelLoaded = levelGenerator.createLevelOne();
		}
		return this.levelLoaded;
	}
}


