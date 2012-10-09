package com.trainpuzzle.ui.windows;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import org.apache.log4j.Logger;

import com.trainpuzzle.controller.Application;
import com.trainpuzzle.controller.Simulator;
import com.trainpuzzle.exception.TrainCrashException;
import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.model.map.Train;
import com.trainpuzzle.model.map.Location;
import com.trainpuzzle.model.map.Track;

import java.util.*;

// Level selection for the campaign
public class LoadedLevel extends Window implements ActionListener {
	// Layout Manager
	private GridBagConstraints c;
	
	// Window elements
	private JLabel titleLabel;
	private JButton runButton;
	private JPanel mapPanel;
	private JPanel toolbarPanel;
	private JLabel grassTile;
	private JLabel trackTile;
	private Logger logger = Logger.getLogger(LoadedLevel.class);
	
	private int previousTrainRow;
	private int previousTrainColumn;
	
	int numberOfRows = 15;
	int numberOfColumns = 20;
	
	private JLayeredPane mapTile;
	private JLayeredPane[][] mapTiles;
	
	private Application app;
	
	Border loweredbevel, loweredetched;
	TitledBorder mapTitle, toolbarTitle;
	
	// Constructor
	public LoadedLevel() {
		loweredbevel = BorderFactory.createLoweredBevelBorder();
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		titleLabel = null;
		mapPanel = null;
		toolbarPanel = null;
		
		previousTrainRow = 0;
		previousTrainColumn = 0;
		
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		setSize(new Dimension(1280,720));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	
	}
	
	public void redrawTrain(Train train) {		
		JLabel trainTile;
    	trainTile = new JLabel(new ImageIcon("src/main/resources/images/train.png"));
    	trainTile.setBounds(0,0,40,40);
        
		Location trainLocation = train.getLocation();
		
		int row = trainLocation.getRow();
		int column = trainLocation.getColumn();
		
		mapTiles[row][column].add(trainTile, new Integer(2));
		
		try {
			mapTiles[previousTrainRow][previousTrainColumn].remove(mapTiles[previousTrainRow][previousTrainColumn].getComponentsInLayer(2)[0]);
			//logger.info("removing @ " + previousTrainLatitude + ", " + previousTrainLongitude);
        	
		} catch(Exception e){
			logger.error(e.getMessage(), e.fillInStackTrace());
		}
		
		previousTrainRow = trainLocation.getRow();
		previousTrainColumn= trainLocation.getColumn();
		mapPanel.repaint();
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
		
		mapTiles = new JLayeredPane[numberOfRows][numberOfColumns];
		
		mapPanel = new JPanel();	
		mapPanel.setLayout(new GridLayout(numberOfRows, numberOfColumns));
		
        for(int r = 0; r < numberOfRows; r++){
            for(int c = 0; c < numberOfColumns; c++){
            	
            	mapTile = new JLayeredPane();
            	mapTile.setPreferredSize(new Dimension(40, 40));
            	
            	if(testLevel.getMap().getTile(r,c).getLandscapeType().equals("grass")) {
            		grassTile=new JLabel(new ImageIcon("src/main/resources/images/grass.png")); 
            	}
            	
            	if(testLevel.getMap().getTile(r,c).getLandscapeType().equals("water")) {
            		grassTile=new JLabel(new ImageIcon("src/main/resources/images/water.png"));
            	}
            	
            	grassTile.setBounds(0,0,40,40);
            	
            	mapTile.add(grassTile, new Integer(0));
            	
            	if(testLevel.getMap().getTile(r,c).hasTrack()){
            		trackTile=new JLabel(new ImageIcon("src/main/resources/images/track.png"));
                	trackTile.setBounds(0,0,40,40);
                	mapTile.add(trackTile, new Integer(1));
            	}
            	
            	mapPanel.add(mapTile);
            	mapTiles[r][c] = mapTile;
            }
        }
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		this.add(mapPanel, c);
		
		//testSimulation(testLevel);
		
		// Track Panel
		toolbarPanel = new JPanel();
		toolbarPanel.setPreferredSize(new Dimension(250, 600));
		toolbarTitle = BorderFactory.createTitledBorder(loweredetched, "Action");
		toolbarTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		toolbarPanel.setBorder(toolbarTitle);		
		c.gridx = 200;
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0, 0, 0, 10);
		
		runButton = new JButton("Simulate");
		toolbarPanel.add(runButton);
		
		runButton.setActionCommand("run");
		runButton.addActionListener(this);
		
		this.add(toolbarPanel, c);
		this.setVisible(true);
	}
	
	/**
	 * Used for internal testing to see if a are simulating a level correctly. Avoid using Application and directly runs the simulator.
	 * 
	 * @param levelToSimulate
	 */
	private void testSimulation(Level levelToSimulate) {
		Simulator testSim = new Simulator(levelToSimulate);
		testSim.getTrain().setLocation(0, 4);
		
		Train testTrain = new Train();
		testTrain.setLocation(0, 4);
		testTrain.setCompassHeading(Track.CompassHeading.EAST);
		for(int r = 0; r < numberOfRows; r++) {
			for(int c = 0; c < numberOfColumns; c++) {
				if(levelToSimulate.getMap().getTile(r,c).hasTrack()) {
					redrawTrain(testSim.getTrain());
		        		try {
							testSim.proceedNextTile();
						} catch (TrainCrashException e) {
							logger.error(e.getMessage(), e.fillInStackTrace());
						}
		        		logger.info("NEW location @ " + testSim.getTrain().getLocation().getRow() + ", " + testSim.getTrain().getLocation().getColumn());
		        }
			}
		}
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == "run") {
			app.runSimulation();
		}	
	}
	
	public void setApplication(Application app) {
		this.app = app;
	}
}