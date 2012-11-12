package com.trainpuzzle.controller;

import java.util.ArrayList;
import java.util.List;

import com.trainpuzzle.infrastructure.FileManager;
import com.trainpuzzle.model.level.Campaign;


public class CampaignManager {

	private List<Campaign> campaigns = new ArrayList<Campaign>();
	private Campaign selectedCampaign;
	
	public CampaignManager(){
        campaigns.add( new Campaign());
        campaigns.add(FileManager.loadCampaign("Campaign2"));
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
	
	public void saveCampaign(){
		FileManager.saveCampaign(selectedCampaign, selectedCampaign.getName());
	}
	
	public void resetCampaign(int campaignNumber){
		campaigns.get(campaignNumber).reset();
		selectCampaign(campaignNumber + 1);
		saveCampaign();
	}
	
	  
}