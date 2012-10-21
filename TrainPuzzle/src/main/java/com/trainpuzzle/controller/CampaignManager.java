package com.trainpuzzle.controller;

import com.trainpuzzle.model.level.*;


public class CampaignManager {
	private Level levelLoaded;
	private LevelGenerator levelGenerator = new LevelGenerator();
	
	public CampaignManager(){

	}
	
	public Level loadLevel(int levelNumber) {
		this.levelLoaded = levelGenerator.createLevelOne();
		return this.levelLoaded;
	}
}


