package com.trainpuzzle.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import com.thoughtworks.xstream.*;

import com.trainpuzzle.model.level.Campaign;


public class CampaignManager {

	private Campaign[] campaignList;
	private transient Campaign selectedCampaign;
	
	public CampaignManager(){
		this.campaignList = new Campaign[2];
        this.campaignList[0] = new Campaign();
        this.campaignList[1] = new Campaign("Campaign 2");
        
	}
	
	public Campaign[] getCampaignList(){
		return this.campaignList;
	}	
	
	public Campaign getCampaign(){
		return this.selectedCampaign;
	}
	
	public void selectCampaign(int campaignNumber){
		this.selectedCampaign = this.campaignList[campaignNumber-1];
	}
	
	  
	public void saveCampaign(Campaign campaign) {
		File file = new File("campaign.xml"); 
		try {
		// Create the necessary output streams to save the level.
			PrintStream out = new PrintStream(file);
			XStream xstream = new XStream();
			xstream.toXML(campaign, out);
			System.out.println("wrote to file: " + file.getAbsoluteFile());
		}
		// Print out exceptions. We should really display them in a dialog...
		catch (IOException e) { 
			System.out.println(e); 
		}
	}
	  
	public Campaign loadCampaign(File file) {
		Campaign loadedCampaign = new Campaign();
	    try {
	    	
	    	XStream xstream = new XStream();
	        loadedCampaign = (Campaign)xstream.fromXML(file);
	        System.out.println("loaded from file: " + file.getAbsoluteFile());    
	    }
	    // Print out exceptions. We should really display them in a dialog...
	    catch (Exception e) { System.out.println(e); }
	    
	    return loadedCampaign;
	}
}