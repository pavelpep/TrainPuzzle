package com.trainpuzzle.controller;

import java.util.ArrayList;
import java.util.List;

import com.trainpuzzle.infrastructure.FileManager;
import com.trainpuzzle.model.level.Campaign;

public class CampaignManager {

	private List<Campaign> campaigns = new ArrayList<Campaign>();
	private Campaign selectedCampaign;
	
	public CampaignManager() {
        campaigns.add(FileManager.loadCampaign("Campaign1"));
        campaigns.add(FileManager.loadCampaign("Campaign2"));
	}
	
	public List<Campaign> getCampaigns() {
		return campaigns;
	}	
	
	public Campaign getCampaign() {
		return selectedCampaign;
	}
	
	public void selectCampaign(int campaignNumber) {
		selectedCampaign = this.campaigns.get(campaignNumber-1);
	}
	
	public void saveCampaign() {
		FileManager.saveCampaign(selectedCampaign, selectedCampaign.getName());
	}
	
	public void saveCampaign(int campaignNumber) {
		Campaign campaignToSave = campaigns.get(campaignNumber);
		FileManager.saveCampaign(campaignToSave, campaignToSave.getName());
	}
	
	public void resetCampaign(int campaignNumber) {
		campaigns.get(campaignNumber).reset();
		saveCampaign(campaignNumber);
	}
}