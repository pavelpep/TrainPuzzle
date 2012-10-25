package com.trainpuzzle.controller;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
		}else{
			//if some weird level number is given, then load level from file 
			 this.levelLoaded = load();
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
	  public void saveLevel() {
	    // Create a file dialog to query the user for a filename.
	    FileDialog f = new FileDialog(new Frame(), "Save Level", FileDialog.SAVE);
	    f.show();                        // Display the dialog and block.
	    String filename = f.getFile();   // Get the user's response
	    if (filename != null) {          // If user didn't click "Cancel".
	      try {
	        // Create the necessary output streams to save the scribble.
	        FileOutputStream fos = new FileOutputStream(filename); 
								// Save to file

	        ObjectOutputStream out = new ObjectOutputStream(fos); 
								// Save objects
	        XStream xstream = new XStream();
	        String xml = xstream.toXML(levelLoaded);
	        
	        out.writeObject(xml);      	// Write the entire Level 
	        out.flush();                 		// Always flush the output.
	        out.close();                 		// And close the stream.
	        System.out.println("wrote to file: " + filename);
	      }
	      // Print out exceptions.  We should really display them in a dialog...
	      catch (IOException e) { System.out.println(e); }
	    }
	  }

	  /**
	   * Prompt for a filename, and load a scribble from that file.
	   * Read compressed, serialized data with a FileInputStream.
	   * Uncompress that data with a GZIPInputStream.
	   * Deserialize the vector of lines with a ObjectInputStream.
	   * Replace current data with new data, and redraw everything.
	   */
	  
	  public Level load() {
		  
		Level loadedLevel = new Level(3);
	    // Create a file dialog to query the user for a filename.
	    FileDialog f = new FileDialog(new Frame(), "Load Level", FileDialog.LOAD);
	    f.show();                         // Display the dialog and block.
	    String filename = f.getFile();    // Get the user's response
	    if (filename != null) {           // If user didn't click "Cancel".
	      try {
	        // Create necessary input streams
	        FileInputStream fis = new FileInputStream(filename); // Read from file
	        ObjectInputStream in = new ObjectInputStream(fis);  // Read objects
	        // Read in an object.  It should be a vector of scribbles
	        XStream xstream = new XStream();
	        String xml = (String)in.readObject(); 
	        
	        loadedLevel = (Level)xstream.fromXML(xml);
	        in.close();                    // Close the stream.
	        System.out.println("loaded from file: " + filename);
	        
	        
	      }
	      // Print out exceptions.  We should really display them in a dialog...
	      catch (Exception e) { System.out.println(e); }
	    }
	    
	    return loadedLevel;
	  }
}


