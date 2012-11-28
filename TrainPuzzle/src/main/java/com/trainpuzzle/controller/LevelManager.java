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
		
	public LevelManager(Campaign campaign) {
        this.campaign = campaign;   
	}
		
	public void selectLevel(int levelNumber){
		try {
			campaign.selectLevel(levelNumber);
		} catch (LevelLockedException e) {
			e.printStackTrace();
		}
		
	    loadLevel(campaign.getCurrentLevelNumber());

	}
	
	public void selectNextLevel() {
		try {
			campaign.selectNextLevel();
		} catch (LevelLockedException e) {
			e.printStackTrace();
		}
		
		loadLevel(campaign.getCurrentLevelNumber());
	}
	
	private void loadLevel(int levelNumber) {
		campaignLevel = campaign.getLevel(levelNumber);
		
		// if level has been saved, load the saved level
		if(campaignLevel.hasUserSave) {
			Level level = FileManager.loadLevelSave(campaign.getName(), ((Integer)levelNumber).toString());
			levelLoaded = level;	
		}else if(campaign.hasMasterFiles){// Otherwise, load the level from master file
			Level level = FileManager.loadLevelMaster(campaign.getName(), ((Integer)levelNumber).toString());
			levelLoaded = level;	
		}// Otherwise, load the hardcoded level
		else {
			LevelFactory levelFactory = new LevelFactory();
			Level level = levelFactory.createLevel(levelNumber);
			levelLoaded = level;	
		}
	}


	public boolean thereIsANextLevel() {
		return campaign.thereIsANextLevel();
	}
	public boolean nextLevelIsUnlocked() {
		return campaign.nextLevelIsUnlocked();
	}
	
	public void levelCompleted() {
		campaign.completeLevel(campaign.getCurrentLevelNumber());
	}
	
	public Level getLevel() {
		return levelLoaded;
	}
	
	public int getCurrentLevelNumber() {
		return campaign.getCurrentLevelNumber();
	}
	
	public List<CampaignLevel> getLevels() {
		return campaign.getCampaignLevels();
	}
	
	public void saveCurrentLevel() {
		int levelNumber = campaign.getCurrentLevelNumber();
		String filename = "Campaigns/" + campaign.getName() + "/Saves/" + levelNumber + ".xml";
		FileManager.saveLevel(levelLoaded, filename);
		campaignLevel.hasUserSave = true;
	}
}