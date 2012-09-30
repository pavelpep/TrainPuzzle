package com.trainpuzzle.ui.windows;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*;

// Level selection for the campaign
class LevelSelect extends Window implements ActionListener {
	// Layout Manager
	private GridBagConstraints gbConstraints;
	
	// Window elements
	private JLabel titleLabel = new JLabel();
	private JButton levelButton = new JButton();
	private JButton backButton = new JButton();
	
	// Constructor
	public LevelSelect() {
		gbConstraints = new GridBagConstraints();
		setLayout(new GridBagLayout());
		setSize(new Dimension(640,480));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	
	}
	
	
	//initializes a specific jComponent
	private void initializeComponent(JComponent jComponent, int fontLayout, int fontSize, Color bgColor, int gridX, int gridY, int gridWidth, int gridHeight, int anchor, int fill, Insets inset, boolean isEnabled){
		jComponent.setFont(new Font("Arial", fontLayout, fontSize));
		jComponent.setBackground(bgColor);
		this.gbConstraints.gridx = gridX;
		this.gbConstraints.gridy = gridY;
		this.gbConstraints.gridwidth = gridWidth;
		this.gbConstraints.gridheight = gridHeight;
		this.gbConstraints.anchor = anchor;
		this.gbConstraints.fill = fill;
		this.gbConstraints.insets = inset;
		jComponent.setEnabled(isEnabled);
	}
	
	
	public void Create() {	    
		
		//Level select title
		this.initializeComponent(this.titleLabel, Font.CENTER_BASELINE, 28, Color.BLACK, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 0, 10), true);
		this.titleLabel.setText("Level Select");
		this.add(this.titleLabel, this.gbConstraints);
		
		//Test level button
		this.initializeComponent(this.levelButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.GREEN, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(30, 0, 10, 0), true);
		this.levelButton.setText("Enter Test Level");
		this.add(this.levelButton, this.gbConstraints);

		//Back button
		this.initializeComponent(this.backButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.LIGHT_GRAY, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(50, 0, 10, 0), true);
		this.backButton.setText("Back");
		this.add(this.backButton, this.gbConstraints);
	
		// ActionListeners for window elements
		backButton.setActionCommand("back");
		backButton.addActionListener(this);
		
		levelButton.setActionCommand("levelSelected");
		levelButton.addActionListener(this);
		
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		switch(action){
			case("back"):
				WindowManager.getManager().setActiveWindow(new MainMenu()); 
				WindowManager.getManager().updateWindows();
			break;
			case("levelSelected"):
				WindowManager.getManager().setActiveWindow(new LoadedLevel()); 
				WindowManager.getManager().updateWindows();
			break;
		}
		
	}
}