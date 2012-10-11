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
import com.trainpuzzle.model.map.CompassHeading;
import com.trainpuzzle.model.map.Train;
import com.trainpuzzle.model.map.Location;

import java.util.*;

// Level selection for the campaign
public class LoadedLevel extends Window implements ActionListener {
	// Layout Manager
	private GridBagConstraints c;
	
	// Window elements
	private JLabel titleLabel = new JLabel();
	private JButton runButton = new JButton();
	private JPanel mapPanel = new JPanel();
	private JPanel sidePanel = new JPanel();
	private JLabel grassTile = new JLabel();
	private JLabel trackTile = new JLabel();
	private Logger logger = Logger.getLogger(LoadedLevel.class);
	
	private int previousTrainRow;
	private int previousTrainColumn;
	
	int numberOfRows = 15;
	int numberOfColumns = 20;
	
	private JLayeredPane mapTile;
	private JLayeredPane[][] mapTiles;
	
	private Application app;
	
	Border loweredbevel, loweredetched;
	TitledBorder mapTitle, sidePanelTitle;
	
	// Constructor
	public LoadedLevel() {
		loweredbevel = BorderFactory.createLoweredBevelBorder();
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		
		previousTrainRow = 0;
		previousTrainColumn = 0;
		
		c = new GridBagConstraints();
		setBackground(Color.LIGHT_GRAY);
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
		initializeComponent(this.titleLabel, Font.CENTER_BASELINE, 28, Color.BLACK, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 0, 10), true);
		this.titleLabel.setText("Level 1");
		this.add(this.titleLabel, gbConstraints);
		
		// Map Panel
		Level testLevel = new Level(1);
		
		mapTiles = new JLayeredPane[numberOfRows][numberOfColumns];
		
		mapPanel = new JPanel();	
		mapPanel.setLayout(new GridLayout(numberOfRows, numberOfColumns));
		
        for(int row = 0; row < numberOfRows; row++){
            for(int column = 0; column < numberOfColumns; column++){
            	
            	mapTile = new JLayeredPane();
            	mapTile.setPreferredSize(new Dimension(40, 40));
            	
            	if(testLevel.getMap().getTile(row, column).getLandscapeType().equals("grass")) {
            		grassTile=new JLabel(new ImageIcon("src/main/resources/images/grass.png")); 
            	}
            	
            	if(testLevel.getMap().getTile(row, column).getLandscapeType().equals("water")) {
            		grassTile=new JLabel(new ImageIcon("src/main/resources/images/water.png"));
            	}
            	
            	grassTile.setBounds(0,0,40,40);
            	
            	mapTile.add(grassTile, new Integer(0));
            	
            	if(testLevel.getMap().getTile(row, column).hasTrack()){
            		trackTile=new JLabel(new ImageIcon("src/main/resources/images/track.png"));
                	trackTile.setBounds(0,0,40,40);
                	mapTile.add(trackTile, new Integer(1));
            	}
            	
            	mapPanel.add(mapTile);
            	mapTiles[row][column] = mapTile;
            }
        }
        
        initializeComponent(this.mapPanel, Font.CENTER_BASELINE, 0, Color.LIGHT_GRAY, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), true);
        this.add(this.mapPanel, gbConstraints);
		
		//testSimulation(testLevel);
		
		// Track Panel 
        sidePanel.setPreferredSize(new Dimension(250, 600));
        sidePanelTitle = BorderFactory.createTitledBorder(loweredetched, "Action");
        sidePanelTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		sidePanel.setBorder(sidePanelTitle);		
		
		runButton = new JButton("Simulate");
		sidePanel.add(runButton);
		
		runButton.setActionCommand("run");
		runButton.addActionListener(this);
		
		initializeComponent(this.sidePanel, Font.CENTER_BASELINE, 0, Color.LIGHT_GRAY, 200, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), true);
		this.add(sidePanel, gbConstraints);
		
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
		testTrain.setCompassHeading(CompassHeading.EAST);
		for(int row = 0; row < numberOfRows; row++) {
			for(int column = 0; column < numberOfColumns; column++) {
				if(levelToSimulate.getMap().getTile(row,column).hasTrack()) {
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