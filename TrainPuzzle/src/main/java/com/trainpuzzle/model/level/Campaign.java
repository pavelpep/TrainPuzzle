package com.trainpuzzle.model.level;

import java.util.ArrayList;
import java.util.List;


public class Campaign implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String campaignName;
	private List<CampaignLevel> campaignLevels = new ArrayList<CampaignLevel>();
    
	
    public Campaign() {
    	campaignName = "Campaign 1";
    	campaignLevels.add(new CampaignLevel(false,false,1));
    	campaignLevels.add(new CampaignLevel(false,false,2));
    	campaignLevels.add(new CampaignLevel(false,false,3));
    	
	}
    public Campaign(String name) {
    	campaignName = name;
    	campaignLevels.add(new CampaignLevel(false,false,1));
    	campaignLevels.add(new CampaignLevel(true,false,2));
    	campaignLevels.add(new CampaignLevel(true,false,3));
    	
	}
	
	public void completeLevel(int levelNumber) {
		campaignLevels.get(levelNumber - 1).isCompleted = true;
		try
		{
			campaignLevels.get(levelNumber).isLocked = false;
		}
		finally{}
	}
	
	
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String name) {
		this.campaignName = name;
	}
	public List<CampaignLevel> getCampaignLevels() {
		return campaignLevels;
	}
	
	
}
