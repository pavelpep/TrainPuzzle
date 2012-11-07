package com.trainpuzzle.model.level;


public class Campaign implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String campaignName;
    private CampaignLevel[]	campaignLevels;
    
	
    public Campaign() {
    	campaignName = "Campaign 1";
    	campaignLevels = new CampaignLevel[3];
    	campaignLevels[0] = new CampaignLevel(false,false,1);
    	campaignLevels[1] = new CampaignLevel(true,false,2);
    	campaignLevels[2] = new CampaignLevel(true,false,3);
    	
	}
    public Campaign(String name) {
    	campaignName = name;
    	campaignLevels = new CampaignLevel[2];
    	campaignLevels[0] = new CampaignLevel(false,false,1);
    	campaignLevels[1] = new CampaignLevel(true,false,2);
    	
	}
	
	public void completeLevel(int levelNumber) {
		campaignLevels[levelNumber - 1].isCompleted = true;
		try
		{
			campaignLevels[levelNumber].isLocked = false;
		}
		finally{}
	}
	
	
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String name) {
		this.campaignName = name;
	}
	public CampaignLevel[] getCampaignLevels() {
		return this.campaignLevels;
	}
	
	
}
