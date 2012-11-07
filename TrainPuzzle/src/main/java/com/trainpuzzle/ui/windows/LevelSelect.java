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
		levelSelectPanel.add(titleLabel);
		
		// Level 1 Button
	    JButton levelOneButton = initializeButton("Level 1","LEVEL_ONE");
	    initializeComponent(levelOneButton, 20);
	    levelOneButton.setBackground(Color.GREEN);
		levelSelectPanel.add(levelOneButton);
		
		// Level 2 Button
		JButton levelTwoButton = initializeButton("Level 2","LEVEL_TWO");
		initializeComponent(levelTwoButton, 20);
		levelTwoButton.setBackground(Color.GREEN);
		levelSelectPanel.add(levelTwoButton);
		
		// Level 3 Button
		JButton levelThreeButton = initializeButton("Level 3","LEVEL_THREE");
		initializeComponent(levelThreeButton, 20);
		levelThreeButton.setBackground(Color.GREEN);
		levelSelectPanel.add(levelThreeButton);
		
		// Load Button
		JButton loadButton = initializeButton("Load Level","LOAD");
		initializeComponent(loadButton, 20);
		loadButton.setBackground(Color.GREEN);
		levelSelectPanel.add(loadButton);
		
		// Back button
		JButton backButton = initializeButton("Back","back");
		initializeComponent(backButton, 20);
		backButton.setBackground(Color.LIGHT_GRAY);
		levelSelectPanel.add(backButton);

		this.setVisible(true);
	}
	
	private JButton initializeButton(String label, String actionCommand) {
		JButton button = new JButton(label);
		button.setActionCommand(actionCommand);
		button.addActionListener(this);
		return button;
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