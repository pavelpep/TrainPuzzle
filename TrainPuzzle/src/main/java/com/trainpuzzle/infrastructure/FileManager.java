package com.trainpuzzle.infrastructure;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import com.thoughtworks.xstream.XStream;
import com.trainpuzzle.model.level.Campaign;
import com.trainpuzzle.model.level.Level;

public class FileManager {
	
	//Saving and Loading Levels
	public static void saveLevel(Level level, String filename) {
		saveObject(level,filename);
	}

	public static void saveLevel(Level level, String campaignName, String levelNumber){
		String filename = "Campaigns/" + campaignName + "/Saves/" + levelNumber + ".xml";
		saveLevel(level, filename);
	}
	
	public static Level loadLevel(String filename) {
		return (Level)loadObject(filename);
	}
	
	public static Level loadLevel(String campaignName, String levelNumber) {
		String filename = "Campaigns/" + campaignName + "/Saves/" + levelNumber + ".xml";
		return loadLevel(filename);
	}
	
	//Saving and Loading Campaigns
	public static void saveCampaign(Campaign campaign, String campaignName) {
		String filename = "Campaigns/" + campaignName + ".xml";
		saveObject(campaign, filename);
	}
	
	public static Campaign loadCampaign(String campaignName) {
		String filename = "Campaigns/" + campaignName +  ".xml";
		return (Campaign)loadObject(filename);
	}
	
    public static void saveObject(Object object, String filename) {
		File file = new File(filename); 
		try {
			PrintStream out = new PrintStream(file);
			XStream xstream = new XStream();
			xstream.toXML(object, out);
			System.out.println("wrote to file: " + file.getAbsoluteFile());
		}
		// TODO: Print out exceptions. Should display them in a dialog...
		catch (IOException e) { 
			System.out.println(e); 
		}
	}
    
  	public static Object loadObject(String filename) {
  		File file = new File(filename);
  		Object objectLoaded = new Object();
  		try {
	    	
	    	XStream xstream = new XStream();
	    	objectLoaded = xstream.fromXML(file);
	        System.out.println("loaded from file: " + file.getAbsoluteFile());    
	    }
		// TODO: Print out exceptions. Should display them in a dialog...
	    catch (Exception e) { System.out.println(e); }
  		return objectLoaded;
	}
}