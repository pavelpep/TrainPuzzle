package com.trainpuzzle.ui.windows;

import com.trainpuzzle.controller.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.*;

// Level selection for the campaign
public class LevelSelect extends Window implements ActionListener {
	private GameController gameController;
	
	
	int levelSelected = 0;
	
	// Constructor
	public LevelSelect() {
		gbConstraints = new GridBagConstraints();
		setLayout(new GridBagLayout());
		setSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	
	}
	
	public LevelSelect(GameController gameController) {
		this.gameController = gameController;
		gbConstraints = new GridBagConstraints();
		setLayout(new GridBagLayout());
		setSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	
	}
	
	public void create() {	    
		
		// Level select title
		JLabel titleLabel = new JLabel();
		addComponent(this, titleLabel, Font.CENTER_BASELINE, 28, Color.BLACK, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 0, 10), true);
		titleLabel.setText("Level Select");
		this.add(titleLabel, gbConstraints);
		
		// Level 1 Button
	    JButton levelOneButton = new JButton();
		addComponent(this, levelOneButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.GREEN, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(30, 0, 10, 0), true);
		levelOneButton.setText("Level 1");
		levelOneButton.setActionCommand("LEVEL_ONE");
		levelOneButton.addActionListener(this);
		
		// Level 2 Button
		JButton levelTwoButton = new JButton();
		addComponent(this, levelTwoButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.GREEN, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(30, 0, 10, 0), true);
		levelTwoButton.setText("Level 2");
		levelTwoButton.setActionCommand("LEVEL_TWO");
		levelTwoButton.addActionListener(this);
		
		// Level 3 Button
		JButton levelThreeButton = new JButton();
		addComponent(this, levelThreeButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.GREEN, 0, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(30, 0, 10, 0), true);
		levelThreeButton.setText("Level 3");
		levelThreeButton.setActionCommand("LEVEL_THREE");
		levelThreeButton.addActionListener(this);
		
		// Load Button
		JButton loadButton = new JButton();
		addComponent(this, loadButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.GREEN, 0, 4, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(30, 0, 10, 0), true);
		loadButton.setText("Load Level");
		loadButton.setActionCommand("LOAD");
		loadButton.addActionListener(this);
		
		// Back button
		JButton backButton = new JButton();
		addComponent(this, backButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.LIGHT_GRAY, 0, 5, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(50, 0, 10, 0), true);
		backButton.setText("Back");
		backButton.setActionCommand("back");
		backButton.addActionListener(this);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		if (action == "back") {
				WindowManager.getManager().setActiveWindow(new MainMenu(gameController)); 
				WindowManager.getManager().updateWindows();
		} else if (action == "LEVEL_ONE") {
			levelSelected = 1;
					
			gameController.startGame(levelSelected);
			LoadedLevelScreen loadedLevelScreen = new LoadedLevelScreen(gameController);
			WindowManager.getManager().setActiveWindow(loadedLevelScreen); 
			WindowManager.getManager().updateWindows();	
		}		
		 else if (action == "LEVEL_TWO") {
			levelSelected = 2;
					
			gameController.startGame(levelSelected);
			LoadedLevelScreen loadedLevelScreen = new LoadedLevelScreen(gameController);
			WindowManager.getManager().setActiveWindow(loadedLevelScreen); 
			WindowManager.getManager().updateWindows();	
		}
		 else if (action == "LEVEL_THREE") {
			levelSelected = 3;
					
			gameController.startGame(levelSelected);
			LoadedLevelScreen loadedLevelScreen = new LoadedLevelScreen(gameController);
			WindowManager.getManager().setActiveWindow(loadedLevelScreen); 
			WindowManager.getManager().updateWindows();	
		}		
		 else if (action == "LOAD") {
			File levelFile = openFile();
			if(levelFile != null){	
				gameController.startGame(levelFile);
				LoadedLevelScreen loadedLevelScreen = new LoadedLevelScreen(gameController);
				WindowManager.getManager().setActiveWindow(loadedLevelScreen); 
				WindowManager.getManager().updateWindows();	
			}
		}	
		WindowManager.getManager().setPreviousWindow(this);
	}
	
	
	private File openFile(){
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "XML Encoded Level", "xml");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	return chooser.getSelectedFile();
	    }
		return null;
	}
}