package com.trainpuzzle.ui.windows;

import javax.swing.UIManager;
import javax.swing.UIManager.*;
import org.apache.log4j.BasicConfigurator;

// Main Method to start the game... 
public class StartGame {
	public static void main(String[]args) {
		//loads log4j.xml configuration file
		BasicConfigurator.configure();
		
		// Loads Main Menu upon start
//		try {
//		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//		        if ("Nimbus".equals(info.getName())) {
//		            UIManager.setLookAndFeel(info.getClassName());
//		            break;
//		        }
//		    }
//		} catch (Exception e) {
//		    // Switch to default view
//		}		
		WindowManager.getManager().setActiveWindow(new MainMenu());
		WindowManager.getManager().updateWindows();
	}
}


