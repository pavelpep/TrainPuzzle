package com.trainpuzzle.controller;

import java.util.ArrayList;
import java.util.List;
import com.trainpuzzle.model.level.Campaign;


public class CampaignManager {

	private List<Campaign> campaigns = new ArrayList<Campaign>();
	private transient Campaign selectedCampaign;
	
	public CampaignManager(){
        campaigns.add( new Campaign());
        campaigns.add(new Campaign("Campaign2"));  
	}
	
	public List<Campaign> getCampaigns(){
		return campaigns;
	}	
	
	public Campaign getCampaign(){
		return selectedCampaign;
	}
	
	public void selectCampaign(int campaignNumber){
		selectedCampaign = this.campaigns.get(campaignNumber-1);
	}
	
	  
}