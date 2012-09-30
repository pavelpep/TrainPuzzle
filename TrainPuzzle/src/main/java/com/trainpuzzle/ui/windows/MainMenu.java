package com.trainpuzzle.ui.windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// The main menu for the Train Track Puzzle game
class MainMenu extends Window implements ActionListener {
	// Layout Manager
	private GridBagConstraints gbConstraints = new GridBagConstraints();
	
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

	//initializes a specific jComponent
	private void initializeComponent(JComponent jComponent, int fontLayout, int fontSize, Color bgColor, int gridX, int gridY, int gridWidth, int anchor, int fill, Insets inset, boolean isEnabled){
		jComponent.setFont(new Font("Arial", fontLayout, fontSize));
		jComponent.setBackground(bgColor);
		this.gbConstraints.gridx = gridX;
		this.gbConstraints.gridy = gridY;
		this.gbConstraints.gridwidth = gridWidth;
		this.gbConstraints.anchor = anchor;
		this.gbConstraints.fill = fill;
		this.gbConstraints.insets = inset;
		jComponent.setEnabled(isEnabled);
	}
	
	public void Create() {	    
		
		this.initializeComponent(this.menuLabel, Font.CENTER_BASELINE, 28, Color.BLACK, 0, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 0, 10), true);
		this.menuLabel.setText("Train Track Puzzle Game");
		this.add(this.menuLabel, this.gbConstraints);
		
		this.initializeComponent(this.continueButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.ORANGE, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(30, 0, 10, 0), true);
		this.continueButton.setText("Continue Campaign");
		this.add(this.continueButton, this.gbConstraints);
	
		this.initializeComponent(this.profilesButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.ORANGE, 0, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 10, 0), true);
		this.profilesButton.setText("Add/Change User");
		this.add(this.profilesButton, this.gbConstraints);
		
		this.initializeComponent(this.creditsButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.ORANGE, 0, 3, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 10, 0), true);
		this.creditsButton.setText("Credits");
		this.add(this.creditsButton, this.gbConstraints);
		
		this.initializeComponent(this.exitButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.LIGHT_GRAY, 0, 4, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(40, 0, 20, 0), true);
		this.exitButton.setText("Exit");
		this.add(this.exitButton, this.gbConstraints);

		//this.pack();
		this.setVisible(true);
		
		// ActionListener for window elements
		this.continueButton.setActionCommand("continue");
		this.continueButton.addActionListener(this);
		
		this.profilesButton.setActionCommand("profiles");
		this.profilesButton.addActionListener(this);
				
		this.exitButton.setActionCommand("exit");
		this.exitButton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		// Loads the level select menu
		String action = e.getActionCommand();
		//if (e.getActionCommand() == "continue") {
		if (action.equals("continue")) {
			WindowManager.getManager().setActiveWindow(new LevelSelect()); 
			WindowManager.getManager().updateWindows();
		}
		if (action.equals("profiles")) {
			WindowManager.getManager().setActiveWindow(new Profiles()); 
			WindowManager.getManager().updateWindows();
		}			
		// Exit Program
		if (action.equals("exit")) {
			System.exit(0);
		}
	}
	
}