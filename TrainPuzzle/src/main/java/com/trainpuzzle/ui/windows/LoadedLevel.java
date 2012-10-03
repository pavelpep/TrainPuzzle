package com.trainpuzzle.ui.windows;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.map.Train;
import com.trainpuzzle.model.map.Location;

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
	private JLabel grassTile;
	private JLabel trackTile;
	
	private Location previousTrainLocation;
	
	int mapWidth = 20;
	int mapHeight = 15;
	
	private JLayeredPane[][] mapTiles;
	
	

	Border loweredbevel, loweredetched;
	TitledBorder mapTitle, toolbarTitle;
	
	// Constructor
	public LoadedLevel() {
		loweredbevel = BorderFactory.createLoweredBevelBorder();
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		titleLabel = null;
		mapPanel = null;
		toolbarPanel = null;
		
		previousTrainLocation = new Location(0,0);
		
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		setSize(new Dimension(1280,720));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	
	}
	
	public void redrawTrain(Train train) {
		
		int previousLatitude = previousTrainLocation.getLatitude();
		int previousLongitude = previousTrainLocation.getLongitude();
		mapTiles[previousLatitude][previousLongitude].remove(2);
		
		JLabel trainTile;
    	trainTile = new JLabel(new ImageIcon("src/main/resources/images/train.png"));
    	trainTile.setBounds(0,0,40,40);
        
		Location trainLocation = train.getLocation();
	
		int latitude = trainLocation.getLatitude();
		int longitude = trainLocation.getLongitude();
		previousTrainLocation = trainLocation;
		
		mapTiles[latitude][longitude].add(trainTile, new Integer(2));
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
		Level testLevel = new Level(1);
		
		mapTiles = new JLayeredPane[mapWidth][mapHeight];
		
		mapPanel = new JPanel();	
		mapPanel.setLayout(new GridLayout(mapHeight, mapWidth));
		
        for(int y=0; y < mapHeight; y++){
            for(int x=0; x < mapWidth; x++){
            	
            	JLayeredPane mapTile = new JLayeredPane();
            	mapTile.setPreferredSize(new Dimension(40, 40));
            	
            	if(testLevel.getMap().getTile(y,x).getLandscapeType() == "grass"){
            		grassTile=new JLabel(new ImageIcon("src/main/resources/images/grass.png")); 
            	}
            	
            	if(testLevel.getMap().getTile(y,x).getLandscapeType() == "water"){
            		grassTile=new JLabel(new ImageIcon("src/main/resources/images/water.png"));
            	}
            	
            	grassTile.setBounds(0,0,40,40);
            	
            	mapTile.add(grassTile, new Integer(0));
            	
            	if(testLevel.getMap().getTile(y,x).hasTrack()){
            		trackTile=new JLabel(new ImageIcon("src/main/resources/images/track.png"));
                	trackTile.setBounds(0,0,40,40);
                	mapTile.add(trackTile, new Integer(1));
            	}
            	
            	mapPanel.add(mapTile);
            	mapTiles[x][y] = mapTile;
            }
    }
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
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
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		
	}
}