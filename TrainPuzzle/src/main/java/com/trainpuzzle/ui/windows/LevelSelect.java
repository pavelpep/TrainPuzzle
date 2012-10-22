package com.trainpuzzle.ui.windows;

import com.trainpuzzle.controller.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

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
		initializeComponent(titleLabel, Font.CENTER_BASELINE, 28, Color.BLACK, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 0, 10), true);
		titleLabel.setText("Level Select");
		this.add(titleLabel, gbConstraints);
		
		// Level 1 Button
	    JButton levelOneButton = new JButton();
		initializeComponent(levelOneButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.GREEN, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(30, 0, 10, 0), true);
		levelOneButton.setText("Level 1");
		this.add(levelOneButton, gbConstraints);
		levelOneButton.setActionCommand("LEVEL_ONE");
		levelOneButton.addActionListener(this);
		
		// Level 2 Button
		JButton levelTwoButton = new JButton();
		initializeComponent(levelTwoButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.GREEN, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(30, 0, 10, 0), true);
		levelTwoButton.setText("Level 2");
		this.add(levelTwoButton, gbConstraints);
		levelTwoButton.setActionCommand("LEVEL_TWO");
		levelTwoButton.addActionListener(this);
		
		// Back button
		JButton backButton = new JButton();
		initializeComponent(backButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.LIGHT_GRAY, 0, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(50, 0, 10, 0), true);
		backButton.setText("Back");
		this.add(backButton, gbConstraints);
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
	}
}