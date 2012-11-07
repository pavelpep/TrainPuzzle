package com.trainpuzzle.ui.windows;

import com.trainpuzzle.controller.GameController;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import test.trainpuzzle.controller.*;

import java.util.*;

// Level selection for the campaign
public class LevelSelect extends Window implements ActionListener {
	private GameController gameController;
	
	
	int levelSelected = 0;
	
	public LevelSelect(GameController gameController) {
		this.gameController = gameController;
		setSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	
		create();
	}
	
	public void create() {	    
		JPanel levelSelectPanel = new JPanel();
		levelSelectPanel.setLayout(new BoxLayout(levelSelectPanel, BoxLayout.Y_AXIS));
		levelSelectPanel.setBorder(new EmptyBorder(200, 10, 10, 10) );
		this.add(levelSelectPanel);
		
		// Level select title
		JLabel titleLabel = new JLabel("Level Select");
		initializeComponent(titleLabel, 28);
		//addComponent(this, titleLabel, Font.CENTER_BASELINE, 28, Color.BLACK, 0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 0, 10), true);
		levelSelectPanel.add(titleLabel);
		
		// Level 1 Button
	    JButton levelOneButton = new JButton("Level 1");
	    initializeComponent(levelOneButton, 20);
	    levelOneButton.setBackground(Color.GREEN);
		levelOneButton.setActionCommand("LEVEL_ONE");
		levelOneButton.addActionListener(this);
		levelSelectPanel.add(levelOneButton);
		
		// Level 2 Button
		JButton levelTwoButton = new JButton("Level 2");
		initializeComponent(levelTwoButton, 20);
		levelTwoButton.setBackground(Color.GREEN);
		levelTwoButton.setActionCommand("LEVEL_TWO");
		levelTwoButton.addActionListener(this);
		levelSelectPanel.add(levelTwoButton);
		
		// Level 3 Button
		JButton levelThreeButton = new JButton("Level 3");
		initializeComponent(levelThreeButton, 20);
		levelThreeButton.setBackground(Color.GREEN);
		levelThreeButton.setActionCommand("LEVEL_THREE");
		levelThreeButton.addActionListener(this);
		levelSelectPanel.add(levelThreeButton);
		
		// Load Button
		JButton loadButton = new JButton("Load Level");
		initializeComponent(loadButton, 20);
		loadButton.setBackground(Color.GREEN);
		loadButton.setActionCommand("LOAD");
		loadButton.addActionListener(this);
		levelSelectPanel.add(loadButton);
		
		// Back button
		JButton backButton = new JButton("Back");
		initializeComponent(backButton, 20);
		backButton.setBackground(Color.LIGHT_GRAY);
		backButton.setActionCommand("back");
		backButton.addActionListener(this);
		levelSelectPanel.add(backButton);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		if (action == "back") {
				WindowManager.getManager(gameController).showPreviousWindow();
		} else if (action == "LEVEL_ONE") {
			levelSelected = 1;
			gameController.startGame(levelSelected);
			WindowManager.getManager(gameController).setActiveWindow(new LoadedLevelScreen(gameController));
		}		
		 else if (action == "LEVEL_TWO") {
			levelSelected = 2;
			gameController.startGame(levelSelected);
			WindowManager.getManager(gameController).setActiveWindow(new LoadedLevelScreen(gameController));
		}
		 else if (action == "LEVEL_THREE") {
			levelSelected = 3;
			gameController.startGame(levelSelected);
			WindowManager.getManager(gameController).setActiveWindow(new LoadedLevelScreen(gameController));
		}		
		 else if (action == "LOAD") {
			File levelFile = openFile();
			if(levelFile != null){	
				gameController.startGame(levelFile);
				LoadedLevelScreen loadedLevelScreen = new LoadedLevelScreen(gameController);
				WindowManager.getManager(gameController).setActiveWindow(loadedLevelScreen);
			}
		}	
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