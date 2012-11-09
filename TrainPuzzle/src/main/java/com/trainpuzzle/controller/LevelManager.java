package com.trainpuzzle.controller;

import java.util.List;

import com.trainpuzzle.exception.LevelLockedException;
import com.trainpuzzle.factory.LevelFactory;
import com.trainpuzzle.infrastructure.FileManager;
import com.trainpuzzle.model.level.Campaign;
import com.trainpuzzle.model.level.CampaignLevel;
import com.trainpuzzle.model.level.Level;

public class LevelManager {

	private Campaign campaign;
	private CampaignLevel campaignLevel;
	private Level levelLoaded;
	
	
	public LevelManager(Campaign campaign){
        this.campaign = campaign;   
	}
		
	public void selectLevel(int levelNumber) throws LevelLockedException{
		campaign.setCurrentLevelNumber(levelNumber);
		campaignLevel = campaign.getCurrentLevel();
		//check if level is locked
		if(campaignLevel.isLocked){
			throw new LevelLockedException("Level " + levelNumber + " is locked.");
		}
		loadLevel(levelNumber);
	}
	
	private void loadLevel(int levelNumber) {

		//if level has been saved, load it's save
		if(campaignLevel.hasUserSave){
			String filename = "Campaigns/" + campaign.getCampaignName() + "/Saves/" + levelNumber + ".xml";
			Level level = FileManager.loadLevel(filename);
			levelLoaded = level;	
		}// otherwise load the master level
		else{
			LevelFactory levelFactory = new LevelFactory();
			Level level = levelFactory.createLevel(levelNumber);
			levelLoaded = level;	
		}
	}

	public void loadNextLevel() {
		int nextLevel = campaign.getCurrentLevelNumber() + 1;
	    loadLevel(nextLevel);
	}
	
	public void levelCompleted(){
		campaign.completeLevel(campaign.getCurrentLevelNumber());
	}
	public Level getLevel(){
		return levelLoaded;
	}
	
	public int getCurrentLevelNumber(){
		return campaign.getCurrentLevelNumber();
	}
	
	public List<CampaignLevel> getLevels(){
		return campaign.getCampaignLevels();
	}
	
	public void saveCurrentLevel(){
		int levelNumber = campaign.getCurrentLevelNumber();
		String filename = "Campaigns/" + campaign.getCampaignName() + "/Saves/" + levelNumber + ".xml";
		FileManager.saveLevel(levelLoaded, filename);
		campaignLevel.hasUserSave = true;
	}
	

}