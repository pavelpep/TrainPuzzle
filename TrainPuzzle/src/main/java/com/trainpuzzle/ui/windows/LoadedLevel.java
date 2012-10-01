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
	private JLayeredPane mapPanel;
	private JPanel toolbarPanel;
	private JLabel[][] grassTile;
	private JLabel[][] trackTile;
	int width = 5;
	int length = 5;
	
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
		titleLabel = new JLabel("Level 1");
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
		mapPanel = new JLayeredPane();	
		mapPanel.setLayout(new GridLayout(width, length));
		mapPanel.setPreferredSize(new Dimension(900, 600));
		mapTitle = BorderFactory.createTitledBorder(loweredbevel, "Map");
		mapTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		mapPanel.setBorder(mapTitle);
		
		grassTile = new JLabel[width][length];
		trackTile = new JLabel[width][length];
		
        for(int y=0; y<length; y++){
            for(int x=0; x<width; x++){
            	grassTile[x][y]=new JLabel(new ImageIcon("src/main/resources/images/grass.png"));    
            	trackTile[x][y]=new JLabel(new ImageIcon("src/main/resources/images/track.png"));
            	trackTile[x][y].setOpaque(true);
            	                    
                    mapPanel.add(grassTile[x][y]); //adds button to grid
                    mapPanel.add(trackTile[x][y]); //adds button to grid
            }
    }
		c.gridx = 0;
		c.gridy = 1;
/*		c.gridwidth = 60;
		c.gridheight = 40;*/
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		//c.insets = new Insets(10, 10, 10, 10);
		this.add(mapPanel, c);
		
		// Track Panel
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