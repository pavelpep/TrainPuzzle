package com.trainpuzzle.ui.windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.trainpuzzle.controller.Application;
import com.trainpuzzle.controller.CampaignManager;
import com.trainpuzzle.controller.GameController;


public class MainMenu extends Window implements ActionListener {
	GameController gameController;
	CampaignManager campaignManager;
	
	// Window elements
	private JLabel menuLabel = new JLabel();
	private JButton continueButton = new JButton();
	private JButton profilesButton = new JButton(); 
	private JButton creditsButton = new JButton();
	private JButton exitButton = new JButton();
	
	// Constructor
	public MainMenu() {		
		setLayout(new GridBagLayout());
		setSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);		
	}
	
	public MainMenu(GameController gameController) {	
		this.gameController = gameController;
		setLayout(new GridBagLayout());
		setSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);		
	}
	
	public void create() {	    
		
		// Game Title
		addComponent(this, this.menuLabel, Font.CENTER_BASELINE, 28, Color.BLACK, 0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 0, 10), true);
		this.menuLabel.setText("Train Track Puzzle Game");
		
		// Continue Button
		addComponent(this, this.continueButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.ORANGE, 0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(30, 0, 10, 0), true);
		this.continueButton.setText("Continue Campaign");
	
		/// Campaigns Button
		addComponent(this, this.profilesButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.ORANGE, 0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 10, 0), true);
		this.profilesButton.setText("Add/Change Campaign");
			
		// Credits Button
		addComponent(this, this.creditsButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.ORANGE, 0, 3, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 10, 0), false);
		this.creditsButton.setText("Credits");
		
		// Exit Button
		addComponent(this, this.exitButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.LIGHT_GRAY, 0, 4, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(40, 0, 20, 0), true);
		this.exitButton.setText("Exit");

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
			WindowManager.getManager().setActiveWindow(new LevelSelect(gameController)); 
			WindowManager.getManager().updateWindows();
		} else if (action == "campaigns") {
			WindowManager.getManager().setActiveWindow(new CampaignsMenu(campaignManager)); 
			WindowManager.getManager().updateWindows();
		} else if (action == "exit") {
			// Exit program
			System.exit(0);
		}
	}
	
}