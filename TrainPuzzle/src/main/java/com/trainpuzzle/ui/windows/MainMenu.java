package com.trainpuzzle.ui.windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// The main menu for the Train Track Puzzle game
class MainMenu extends Window implements ActionListener {
	
	// Window elements
	private JLabel menuLabel = new JLabel();
	private JButton continueButton = new JButton();
	private JButton profilesButton = new JButton(); 
	private JButton creditsButton = new JButton();
	private JButton exitButton = new JButton();
	
	// Constructor
	public MainMenu() {		
		setLayout(new GridBagLayout());
		setSize(new Dimension(640,480));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);		
	}

	
	public void Create() {	    
		
		// Game Title
		initializeComponent(this.menuLabel, Font.CENTER_BASELINE, 28, Color.BLACK, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 0, 10), true);
		this.menuLabel.setText("Train Track Puzzle Game");
		this.add(this.menuLabel, gbConstraints);
		
		// Continue Button
		initializeComponent(this.continueButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.ORANGE, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(30, 0, 10, 0), true);
		this.continueButton.setText("Continue Campaign");
		this.add(this.continueButton, gbConstraints);
	
		/// Campaigns Button
		initializeComponent(this.profilesButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.ORANGE, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 10, 0), true);
		this.profilesButton.setText("Add/Change User");
		this.add(this.profilesButton, gbConstraints);
			
		// Credits Button
		initializeComponent(this.creditsButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.ORANGE, 0, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 10, 0), false);
		this.creditsButton.setText("Credits");
		this.add(this.creditsButton, gbConstraints);
		
		// Exit Button
		initializeComponent(this.exitButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.LIGHT_GRAY, 0, 4, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(40, 0, 20, 0), true);
		this.exitButton.setText("Exit");
		this.add(this.exitButton, gbConstraints);

		//this.pack();
		this.setVisible(true);
		
		// ActionListener for window elements
		this.continueButton.setActionCommand("continue");
		this.continueButton.addActionListener(this);
		
		this.profilesButton.setActionCommand("campaigns");
		this.profilesButton.addActionListener(this);
				
		this.exitButton.setActionCommand("exit");
		this.exitButton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		
		if (action == "continue") {
			WindowManager.getManager().setActiveWindow(new LevelSelect()); 
			WindowManager.getManager().updateWindows();
		} else if (action == "campaigns") {
			WindowManager.getManager().setActiveWindow(new CampaignsMenu()); 
			WindowManager.getManager().updateWindows();
		} else if (action == "exit") {
			// Exit program
			System.exit(0);
		}
	}
	
}