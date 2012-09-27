package com.trainpuzzle.ui.windows;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*;

// Level selection for the campaign
class LevelSelect extends Window implements ActionListener {
	// Layout Manager
	private GridBagConstraints c;
	
	// Window elements
	private JLabel titleLabel;
	private JButton levelButton;
	private JButton backButton;
	
	// Constructor
	public LevelSelect() {
		titleLabel = null;
		levelButton = null;
		backButton = null;
		
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		setSize(new Dimension(640,480));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	
	}
	
	public void Create() {	    
		// Game title
		titleLabel = new JLabel("Level Select");
		titleLabel.setFont(new Font("Arial", Font.CENTER_BASELINE, 28));
		titleLabel.setForeground(Color.black);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(10, 10, 0, 10);
		this.add(titleLabel, c);
		
		// Continue Button
		levelButton = new JButton("Enter Test Level");
		levelButton.setFont(new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 20));
		levelButton.setBackground(Color.GREEN);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 0, 10, 0);
		levelButton.setEnabled(true);
		this.add(levelButton, c);
		
		backButton = new JButton("Back");
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(50, 0, 0, 0);
		this.add(backButton, c);		
		
				// ActionListeners for window elements
		backButton.setActionCommand("back");
		backButton.addActionListener(this);
		
		levelButton.setActionCommand("levelSelected");
		levelButton.addActionListener(this);
		
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "back") {
			WindowManager.getManager().setActiveWindow(new MainMenu()); 
			WindowManager.getManager().updateWindows();
		}
		if (e.getActionCommand() == "levelSelected") {
			WindowManager.getManager().setActiveWindow(new LoadedLevel()); 
			WindowManager.getManager().updateWindows();
		}
		
	}
}