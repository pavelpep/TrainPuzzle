package com.trainpuzzle.controller;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.thoughtworks.xstream.*;

import com.trainpuzzle.model.level.*;


public class CampaignManager {
	private Level levelLoaded;
	private LevelGenerator levelGenerator = new LevelGenerator();
	
	public CampaignManager(){

	}
	
	public Level loadLevel(int levelNumber) {
		if(levelNumber == 1){
			this.levelLoaded = levelGenerator.createLevelOne();
		}else if(levelNumber == 2){
			this.levelLoaded = levelGenerator.createLevelTwo();
		}else if(levelNumber == 3){
			this.levelLoaded = levelGenerator.createLevelThree();
		}else{
			 //just in case
			 this.levelLoaded = levelGenerator.createLevelOne();
		}
		return this.levelLoaded;
	}
	
	  /**
	   * Prompt the user for a filename, and save the scribble in that file.
	   * Serialize the vector of lines with an ObjectOutputStream.
	   * Compress the serialized objects with a GZIPOutputStream.
	   * Write the compressed, serialized data to a file with a FileOutputStream.
	   * Don't forget to flush and close the stream.
	   */
	  public void saveLevel(File file) {	  
	      try {
	        // Create the necessary output streams to save the level.
	        PrintStream out = new PrintStream(file);
	        XStream xstream = new XStream();
	        xstream.toXML(levelLoaded, out);
	        
	        System.out.println("wrote to file: " + file.getAbsoluteFile());
	      }
	      // Print out exceptions.  We should really display them in a dialog...
	      catch (IOException e) { System.out.println(e); }
	    
	  }
 
	  public Level loadLevel(File file) {
		  
		Level loadedLevel = new Level(3);
		
	      try {
	        XStream xstream = new XStream();
	        loadedLevel = (Level)xstream.fromXML(file);
	        this.levelLoaded = loadedLevel;
	        System.out.println("loaded from file: " + file.getAbsoluteFile());    
	        
	      }
	      // Print out exceptions.  We should really display them in a dialog...
	      catch (Exception e) { System.out.println(e); }
	     
	      return this.levelLoaded;
	  }
}


