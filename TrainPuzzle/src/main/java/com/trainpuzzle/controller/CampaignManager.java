package com.trainpuzzle.controller;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
			//if some weird level number is given, return level 1 just in case 
			 load();
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
	        GZIPOutputStream gzos = new GZIPOutputStream(fos);     
								// Compressed
	        ObjectOutputStream out = new ObjectOutputStream(gzos); 
								// Save objects
	        out.writeObject(levelLoaded);      	// Write the entire Level 
	        out.flush();                 		// Always flush the output.
	        out.close();                 		// And close the stream.
	        System.out.println("done writing to file");
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
	  
	  public void load() {
		  
		Level loadedLevel = new Level(3);
	    // Create a file dialog to query the user for a filename.
	    FileDialog f = new FileDialog(new Frame(), "Load Level", FileDialog.LOAD);
	    f.show();                         // Display the dialog and block.
	    String filename = f.getFile();    // Get the user's response
	    if (filename != null) {           // If user didn't click "Cancel".
	      try {
	        // Create necessary input streams
	        FileInputStream fis = new FileInputStream(filename); // Read from file
	        GZIPInputStream gzis = new GZIPInputStream(fis);     // Uncompress
	        ObjectInputStream in = new ObjectInputStream(gzis);  // Read objects
	        // Read in an object.  It should be a vector of scribbles
	        loadedLevel = (Level)in.readObject();
	        in.close();                    // Close the stream.
	        this.levelLoaded = loadedLevel;
	    
	      }
	      // Print out exceptions.  We should really display them in a dialog...
	      catch (Exception e) { System.out.println(e); }
	    }
	    
	    
	  }
}


