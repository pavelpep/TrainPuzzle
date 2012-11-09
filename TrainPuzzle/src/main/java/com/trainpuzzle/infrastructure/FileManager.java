package com.trainpuzzle.infrastructure;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import com.thoughtworks.xstream.XStream;
import com.trainpuzzle.model.level.Level;

public class FileManager {

	public static void saveLevel(Level level, String filename) {
		saveObject(level,filename);
	}
	public static Level loadLevel(String filename) {
		return (Level)loadObject(filename);
	}
	
	public static void saveObject(Object object, String filename) {
		File file = new File(filename); 
		try {
			PrintStream out = new PrintStream(file);
			XStream xstream = new XStream();
			xstream.toXML(object, out);
			System.out.println("wrote to file: " + file.getAbsoluteFile());
		}
		// Print out exceptions. We should really display them in a dialog...
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
	    // Print out exceptions. We should really display them in a dialog...
	    catch (Exception e) { System.out.println(e); }
  		return objectLoaded;
	}
	
	
}
