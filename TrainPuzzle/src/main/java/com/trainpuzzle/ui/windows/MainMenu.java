package com.trainpuzzle.ui.windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.trainpuzzle.controller.Application;
import com.trainpuzzle.controller.CampaignManager;
import com.trainpuzzle.controller.GameController;


public class MainMenu extends Window implements ActionListener {
	GameController gameController;
	CampaignManager campaignManager;
	
	public MainMenu(GameController gameController) {	
		this.gameController = gameController;
		setSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		create();
	}
	
	public void create() {
		JPanel mainMenuPanel = new JPanel();
		mainMenuPanel.setLayout(new BoxLayout(mainMenuPanel, BoxLayout.Y_AXIS));
		mainMenuPanel.setBorder(new EmptyBorder(200, 10, 10, 10) );
		this.add(mainMenuPanel);
		
		JLabel menuLabel = new JLabel("Train Track Puzzle Game");
		initializeComponent(menuLabel, 28);
		mainMenuPanel.add(menuLabel);


		JButton continueButton = new JButton();
		initializeComponent(continueButton, 20);
		continueButton.setBackground(Color.ORANGE);
		continueButton.setText("Continue Campaign");
		continueButton.setActionCommand("continue");
		continueButton.addActionListener(this);
		mainMenuPanel.add(continueButton);
	

		JButton campaignsButton = new JButton("Add/Change Campaign"); 
		initializeComponent(campaignsButton, 20);
		campaignsButton.setBackground(Color.ORANGE);
		campaignsButton.setActionCommand("campaigns");
		campaignsButton.addActionListener(this);
		mainMenuPanel.add(campaignsButton);
		

		JButton creditsButton = new JButton("Credits");
		initializeComponent(creditsButton, 20);
		creditsButton.setBackground(Color.ORANGE);
		mainMenuPanel.add(creditsButton);
		

		JButton exitButton = new JButton("Exit");
		initializeComponent(exitButton, 20);
		exitButton.setBackground(Color.ORANGE);
		exitButton.setActionCommand("exit");
		exitButton.addActionListener(this);
		mainMenuPanel.add(exitButton);
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