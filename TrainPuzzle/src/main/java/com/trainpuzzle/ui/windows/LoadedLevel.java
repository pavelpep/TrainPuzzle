package com.trainpuzzle.ui.windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import java.util.*;

// Level selection for the campaign
class LoadedLevel extends Window implements ActionListener {
	// Layout Manager
	private GridBagConstraints c;
	
	// Window elements
	private JLabel titleLabel;
	private JButton backButton;
	private JPanel mapPanel;
	private JPanel toolbarPanel;
	
	Border loweredbevel, loweredetched;
	TitledBorder mapTitle, toolbarTitle;
	
	// Constructor
	public LoadedLevel() {
		loweredbevel = BorderFactory.createLoweredBevelBorder();
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		titleLabel = null;
		mapPanel = null;
		toolbarPanel = null;
		
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		setSize(new Dimension(1280,720));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	
	}
	
	public void Create() {	    
		// Game title
		titleLabel = new JLabel("Demo Simulation");
		titleLabel.setFont(new Font("Arial", Font.CENTER_BASELINE, 28));
		titleLabel.setForeground(Color.black);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(10, 10, 0, 10);
		this.add(titleLabel, c);
		
		// Map Panel
		mapPanel = new JPanel();		
		mapPanel.setPreferredSize(new Dimension(900, 600));
		mapTitle = BorderFactory.createTitledBorder(loweredbevel, "Map");
		mapTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		mapPanel.setBorder(mapTitle);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 60;
		c.gridheight = 40;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(10, 10, 10, 10);
		this.add(mapPanel, c);
		
		toolbarPanel = new JPanel();
		toolbarPanel.setPreferredSize(new Dimension(250, 600));
		toolbarTitle = BorderFactory.createTitledBorder(loweredetched, "Track Pieces");
		toolbarTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		toolbarPanel.setBorder(toolbarTitle);		
		c.gridx = 200;
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0, 0, 0, 10);
		this.add(toolbarPanel, c);
		
		//this.pack();
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
	}
}