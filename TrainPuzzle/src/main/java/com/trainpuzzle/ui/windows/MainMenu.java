/* Class Name: MainMenu.java
 * Date Created: September 21, 2012
 * Date Edited: September 21, 2012, 23:30
 * Last Edited by: Joey Au-Yeung
 */
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
	private JLabel title;
	private JButton campaign;
	private JButton levelSelect; // do we need this??
	private JButton credits;
	private JButton exit;
	
	// Constructor
	public MainMenu() {
		title = null;
		campaign = null;
		levelSelect = null;
		credits = null;
		exit = null;
		
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		setSize(new Dimension(640,480));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	
	}
	
	public void Create() {	    
		// Game title
		title = new JLabel("Train Track Puzzle Game");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10, 10, 0, 10);
		this.add(title, c);
		
		// Campaign Button
		campaign = new JButton("Campaign");
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 0, 10, 0);
		this.add(campaign, c);
		
		// Level Select Button
		levelSelect = new JButton("Level Select");
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10, 0, 10, 0);
		this.add(levelSelect, c);
		
		// Credits
		credits = new JButton("Credits");
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10, 0, 10, 0);
		this.add(credits, c);
		
		// Exit Button
		exit = new JButton("Exit");
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(40, 0, 20, 0);
		this.add(exit, c);		
		
		//this.pack();
		this.setVisible(true);
		
		// ActionListener for window elements
		campaign.setActionCommand("campaign");
		campaign.addActionListener(this);
		
		exit.setActionCommand("exit");
		exit.addActionListener(this);
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		// Loads the campaign menu	
		if (e.getActionCommand() == "campaign") {
				WindowManager.getManager().setActiveWindow(new Campaign()); 
				WindowManager.getManager().updateWindows();
			}
		// Exit Program
		if (e.getActionCommand() == "exit") {
			System.exit(0);
		}
			
	}
	
}