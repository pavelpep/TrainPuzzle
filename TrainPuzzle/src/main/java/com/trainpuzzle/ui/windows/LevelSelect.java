package com.trainpuzzle.ui.windows;

import com.trainpuzzle.controller.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*;

// Level selection for the campaign
class LevelSelect extends Window implements ActionListener {
	
	// Window elements
	private JLabel titleLabel = new JLabel();
	private JButton levelButton = new JButton();
	private JButton backButton = new JButton();
	
	private Application app;
	int levelSelected = 0;
	
	// Constructor
	public LevelSelect() {
		gbConstraints = new GridBagConstraints();
		setLayout(new GridBagLayout());
		setSize(new Dimension(640,480));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	
	}
	
	public void Create() {	    
		
		// Level select title
		initializeComponent(this.titleLabel, Font.CENTER_BASELINE, 28, Color.BLACK, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 0, 10), true);
		this.titleLabel.setText("Level Select");
		this.add(this.titleLabel, gbConstraints);
		
		// Level 1 Button
		initializeComponent(this.levelButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.GREEN, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(30, 0, 10, 0), true);
		this.levelButton.setText("Level 1");
		this.add(this.levelButton, gbConstraints);

		// Back button
		initializeComponent(this.backButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.LIGHT_GRAY, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(50, 0, 10, 0), true);
		this.backButton.setText("Back");
		this.add(this.backButton, gbConstraints);
	
		// ActionListeners for window elements
		backButton.setActionCommand("back");
		backButton.addActionListener(this);
		
		levelButton.setActionCommand("LEVEL_ONE");
		levelButton.addActionListener(this);
		
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		
		if (action == "back") {
				WindowManager.getManager().setActiveWindow(new MainMenu()); 
				WindowManager.getManager().updateWindows();
		} else if (action == "LEVEL_ONE") {
			levelSelected = 1;
			
			LoadedLevel loadedLevel = new LoadedLevel();
			app = new Application(levelSelected, loadedLevel);
			
			loadedLevel.setApplication(app);	
			WindowManager.getManager().setActiveWindow(loadedLevel); 
			WindowManager.getManager().updateWindows();	
		}		
	}
}