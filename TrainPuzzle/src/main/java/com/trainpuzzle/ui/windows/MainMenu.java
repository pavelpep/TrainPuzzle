package com.trainpuzzle.ui.windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

// The main menu for the Train Track Puzzle game
class MainMenu extends Window implements ActionListener {
	// Layout Manager
	private GridBagConstraints c;
	
	// Window elements
	private JLabel menuLabel;
	private JButton continueButton;
	private JButton profilesButton; 
	private JButton creditsButton;
	private JButton exitButton;
	
	// Constructor
	public MainMenu() {
		menuLabel = null;
		continueButton = null;
		profilesButton = null;
		creditsButton = null;
		exitButton = null;
		
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		setSize(new Dimension(640,480));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	
	}
	
	public void Create() {	    
		// Game title
		menuLabel = new JLabel("Train Track Puzzle Game");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10, 10, 0, 10);
		this.add(menuLabel, c);
		
		// Continue Button
		continueButton = new JButton("Continue Campaign");
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 0, 10, 0);
		continueButton.setEnabled(false);
		this.add(continueButton, c);
		
		// Profile Button
		profilesButton = new JButton("Add/Change User");
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10, 0, 10, 0);
		this.add(profilesButton, c);
		
		// Credits Button
		creditsButton = new JButton("Credits");
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10, 0, 10, 0);
		creditsButton.setEnabled(false);
		this.add(creditsButton, c);
		
		// Exit Button
		exitButton = new JButton("Exit");
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(40, 0, 20, 0);
		this.add(exitButton, c);		
		
		//this.pack();
		this.setVisible(true);
		
		// ActionListener for window elements
		continueButton.setActionCommand("continue");
		continueButton.addActionListener(this);
		
		exitButton.setActionCommand("exit");
		exitButton.addActionListener(this);
		
		profilesButton.setActionCommand("profiles");
		profilesButton.addActionListener(this);
				
	}
	
	public void actionPerformed(ActionEvent e) {
		// Loads the level select menu
		if (e.getActionCommand() == "continue") {
				WindowManager.getManager().setActiveWindow(new LevelSelect()); 
				WindowManager.getManager().updateWindows();
			}
		// Exit Program
		if (e.getActionCommand() == "exit") {
			System.exit(0);
		}
		if (e.getActionCommand() == "profiles") {
			WindowManager.getManager().setActiveWindow(new Profiles()); 
			WindowManager.getManager().updateWindows();
		}
			
	}
	
}