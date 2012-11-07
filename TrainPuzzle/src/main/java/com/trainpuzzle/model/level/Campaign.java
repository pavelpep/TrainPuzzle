package com.trainpuzzle.model.level;

import com.trainpuzzle.factory.LevelFactory;

public class Campaign implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String campaignName;
    CampaignLevel[]	campaignLevels;
    private Level currentLevel;
	
    public Campaign() {
    	campaignName = "Campaign 1";
    	campaignLevels = new CampaignLevel[3];
    	campaignLevels[0] = new CampaignLevel(false,false,1);
    	campaignLevels[1] = new CampaignLevel(true,false,2);
    	campaignLevels[2] = new CampaignLevel(true,false,3);
    	
	}
	
	public void completeLevel(int levelNumber) {
		campaignLevels[levelNumber - 1].isCompleted = true;	
	}
	
	public void loadLevel(int levelNumber) {
		
		Level levelLoaded;
		LevelFactory levelFactory = new LevelFactory();
		
		switch(levelNumber) {
		case 1: levelLoaded = levelFactory.createLevelOne();
				break;
		case 2: levelLoaded = levelFactory.createLevelTwo();
				break;
		case 3: levelLoaded = levelFactory.createLevelThree();
				break;
		default:levelLoaded = levelFactory.createLevelOne();
				break;
		}
		currentLevel= levelLoaded;
	}

	public void loadNextLevel(){
		loadLevel(getCurrentLevelNumber() + 1);
	}
	
	public String getCampaignName() {
		return campaignName;
	}
	
	public CampaignLevel[] getCampaignLevels() {
		return this.campaignLevels;
	}
	
	public Level getCurrentLevel() {
		return currentLevel;
	}
	
	public int getCurrentLevelNumber() {
		return this.currentLevel.getLevelNumber();
	}


}
