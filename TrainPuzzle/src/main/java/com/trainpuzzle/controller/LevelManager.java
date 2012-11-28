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
		
	public void selectLevel(int levelNumber) throws LevelLockedException {
		campaign.selectLevel(levelNumber);
		campaignLevel = campaign.getCurrentLevel();
		if(campaignLevel.isLocked) {
			throw new LevelLockedException("Level " + levelNumber + " is locked.");
		}else{
			loadLevel(levelNumber);
		}
	}
	
	public void selectNextLevel() {
		int nextLevel = campaign.getCurrentLevelNumber() + 1;
		try {
			selectLevel(nextLevel);
		} catch (LevelLockedException e) {
			e.printStackTrace();
		}
	}
	
	private void loadLevel(int levelNumber) {

		// if level has been saved, load the saved level
		if(campaignLevel.hasUserSave) {
			Level level = FileManager.loadLevel(campaign.getName(), ((Integer)levelNumber).toString());
			levelLoaded = level;	
		}// Otherwise, load the master level
		else {
			LevelFactory levelFactory = new LevelFactory();
			Level level = levelFactory.createLevel(levelNumber);
			levelLoaded = level;	
		}
	}


	public boolean thereIsANextLevel() {
		return campaign.thereIsANextLevel();
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