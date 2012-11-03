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
	private JButton campaignsButton = new JButton(); 
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
		create();
	}
	
	public void create() {	    
		
		// Game Title
		addComponent(this, this.menuLabel, Font.CENTER_BASELINE, 28, Color.BLACK, 0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 0, 10), true);
		this.menuLabel.setText("Train Track Puzzle Game");
		
		// Continue Button
		addComponent(this, this.continueButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.ORANGE, 0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(30, 0, 10, 0), true);
		this.continueButton.setText("Continue Campaign");
		this.continueButton.setActionCommand("continue");
		this.continueButton.addActionListener(this);
	
		/// Campaigns Button
		addComponent(this, this.campaignsButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.ORANGE, 0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 10, 0), true);
		this.campaignsButton.setText("Add/Change Campaign");
		this.campaignsButton.setActionCommand("campaigns");
		this.campaignsButton.addActionListener(this);
		
		// Credits Button
		addComponent(this, this.creditsButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.ORANGE, 0, 3, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 10, 0), false);
		this.creditsButton.setText("Credits");
		
		// Exit Button
		addComponent(this, this.exitButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.LIGHT_GRAY, 0, 4, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(40, 0, 20, 0), true);
		this.exitButton.setText("Exit");
		this.exitButton.setActionCommand("exit");
		this.exitButton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		
		if (action == "continue") {
			WindowManager.getManager(gameController).setActiveWindow(new LevelSelect(gameController)); 
		} else if (action == "campaigns") {
			WindowManager.getManager(gameController).setActiveWindow(new Campaigns(gameController)); 
		} else if (action == "exit") {
			// Exit program
			System.exit(0);
		}
	}
	
}